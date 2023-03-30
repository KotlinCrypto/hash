/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
@file:Suppress("UnnecessaryOptInAnnotation")

package org.kotlincrypto.hash.sha2

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.internal.DigestState
import kotlin.jvm.JvmField

/**
 * Core abstraction for:
 *  - SHA-384
 *  - SHA-512
 *  - SHA-512/t
 * */
public sealed class Bit64Digest: Digest {

    // Initial values used to reset the Digest
    @JvmField protected var h0: Long
    @JvmField protected var h1: Long
    @JvmField protected var h2: Long
    @JvmField protected var h3: Long
    @JvmField protected var h4: Long
    @JvmField protected var h5: Long
    @JvmField protected var h6: Long
    @JvmField protected var h7: Long

    private val x: LongArray
    private val state: LongArray

    @OptIn(InternalKotlinCryptoApi::class)
    @Throws(IllegalArgumentException::class)
    protected constructor(
        d: Int,
        t: Int?,
        h0: Long,
        h1: Long,
        h2: Long,
        h3: Long,
        h4: Long,
        h5: Long,
        h6: Long,
        h7: Long,
    ): super("SHA-$d" + (t?.let { "/$it" } ?: ""), 128, (t ?: d) / 8) {
        if (t != null) {
            require(d == 512) { "t can only be expressed for SHA-512" }
            require(t % 8 == 0) { "t must be a factor of 8" }
            require(t < 512) { "t must be less than 512" }
            require(t != 384) { "t cannot be 384" }
        }

        this.h0 = h0
        this.h1 = h1
        this.h2 = h2
        this.h3 = h3
        this.h4 = h4
        this.h5 = h5
        this.h6 = h6
        this.h7 = h7
        this.x = LongArray(80)
        this.state = longArrayOf(h0, h1, h2, h3, h4, h5, h6, h7)
    }

    @OptIn(InternalKotlinCryptoApi::class)
    protected constructor(state: DigestState, digest: Bit64Digest): super(state) {
        this.h0 = digest.h0
        this.h1 = digest.h1
        this.h2 = digest.h2
        this.h3 = digest.h3
        this.h4 = digest.h4
        this.h5 = digest.h5
        this.h6 = digest.h6
        this.h7 = digest.h7
        this.x = digest.x.copyOf()
        this.state = digest.state.copyOf()
    }

    protected final override fun compress(input: ByteArray, offset: Int) {
        val x = x

        var j = offset
        for (i in 0 until 16) {
            x[i] =
                ((input[j++].toLong() and 0xff) shl 56) or
                ((input[j++].toLong() and 0xff) shl 48) or
                ((input[j++].toLong() and 0xff) shl 40) or
                ((input[j++].toLong() and 0xff) shl 32) or
                ((input[j++].toLong() and 0xff) shl 24) or
                ((input[j++].toLong() and 0xff) shl 16) or
                ((input[j++].toLong() and 0xff) shl  8) or
                ((input[j++].toLong() and 0xff)       )
        }

        for (i in 16 until 80) {
            val x15 = x[i - 15]
            val s0 = (x15 rotateRight 1) xor (x15 rotateRight 8) xor (x15 ushr 7)
            val x2 = x[i - 2]
            val s1 = (x2 rotateRight 19) xor (x2 rotateRight 61) xor (x2 ushr 6)
            val x16 = x[i - 16]
            val x7 = x[i - 7]
            x[i] = x16 + s0 + x7 + s1
        }

        val k = K

        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]
        var f = state[5]
        var g = state[6]
        var h = state[7]

        for (i in 0 until 80) {
            val s0 = (a rotateRight 28) xor (a rotateRight 34) xor (a rotateRight 39)
            val s1 = (e rotateRight 14) xor (e rotateRight 18) xor (e rotateRight 41)

            val ch = (e and f) xor (e.inv() and g)
            val maj = (a and b) xor (a and c) xor (b and c)

            val t1 = h + s1 + ch + k[i] + x[i]
            val t2 = s0 + maj

            h = g
            g = f
            f = e
            e = d + t1
            d = c
            c = b
            b = a
            a = t1 + t2
        }

        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d
        state[4] += e
        state[5] += f
        state[6] += g
        state[7] += h
    }

    protected final override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        buffer[bufferOffset] = 0x80.toByte()

        val size = bufferOffset + 1
        if (size > 112) {
            buffer.fill(0, size, 128)
            compress(buffer, 0)
            buffer.fill(0, 0, size)
        } else {
            buffer.fill(0, size, 120)
        }

        buffer[120] = (bitLength ushr 56).toByte()
        buffer[121] = (bitLength ushr 48).toByte()
        buffer[122] = (bitLength ushr 40).toByte()
        buffer[123] = (bitLength ushr 32).toByte()
        buffer[124] = (bitLength ushr 24).toByte()
        buffer[125] = (bitLength ushr 16).toByte()
        buffer[126] = (bitLength ushr  8).toByte()
        buffer[127] = (bitLength        ).toByte()

        compress(buffer, 0)

        return out(
            a = state[0],
            b = state[1],
            c = state[2],
            d = state[3],
            e = state[4],
            f = state[5],
            g = state[6],
            h = state[7],
        )
    }

    protected abstract fun out(
        a: Long,
        b: Long,
        c: Long,
        d: Long,
        e: Long,
        f: Long,
        g: Long,
        h: Long,
    ): ByteArray

    protected final override fun resetDigest() {
        x.fill(0)
        state[0] = h0
        state[1] = h1
        state[2] = h2
        state[3] = h3
        state[4] = h4
        state[5] = h5
        state[6] = h6
        state[7] = h7
    }

    @Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
    private inline infix fun Long.rotateRight(n: Int): Long = (this ushr n) or (this shl (64 - n))

    private companion object {
        private val K = longArrayOf(
            4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L,
            4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L,
            -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L,
            8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L,
            -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L,
            3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L,
            -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L,
            -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L,
            2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L,
            7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L,
            -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L,
            -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L,
            1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L,
            4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L,
            8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L,
            -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L,
            -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L,
            500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L,
            2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L,
            5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L,
        )
    }
}
