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

package org.kotlincrypto.hash.sha1

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState

public class SHA1: Digest {

    private val x: IntArray
    private val state: IntArray

    @OptIn(InternalKotlinCryptoApi::class)
    public constructor(): super("SHA-1", 64, 20) {
        this.x = IntArray(80)
        this.state = intArrayOf(1732584193, -271733879, -1732584194, 271733878, -1009589776)
    }

    @OptIn(InternalKotlinCryptoApi::class)
    private constructor(state: DigestState, digest: SHA1): super(state) {
        this.x = digest.x.copyOf()
        this.state = digest.state.copyOf()
    }

    protected override fun copy(state: DigestState): Digest = SHA1(state, this)

    protected override fun compress(input: ByteArray, offset: Int) {
        val x = x

        var j = offset
        for (i in 0 until 16) {
            x[i] =
                ((input[j++].toInt() and 0xff) shl 24) or
                ((input[j++].toInt() and 0xff) shl 16) or
                ((input[j++].toInt() and 0xff) shl  8) or
                ((input[j++].toInt() and 0xff)       )
        }

        for (i in 16 until 80) {
            x[i] = (x[i - 3] xor x[i - 8] xor x[i - 14] xor x[i - 16]) rotateLeft 1
        }

        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]

        for (i in 0 until 80) {
            val a2 = when {
                i < 20 -> {
                    val f = d xor (b and (c xor d))
                    val k = 1518500249
                    (a rotateLeft 5) + f + e + k + x[i]
                }
                i < 40 -> {
                    val f = b xor c xor d
                    val k = 1859775393
                    (a rotateLeft 5) + f + e + k + x[i]
                }
                i < 60 -> {
                    val f = (b and c) or (b and d) or (c and d)
                    val k = -1894007588
                    (a rotateLeft 5) + f + e + k + x[i]
                }
                else -> {
                    val f = b xor c xor d
                    val k = -899497514
                    (a rotateLeft 5) + f + e + k + x[i]
                }
            }

            e = d
            d = c
            c = b rotateLeft 30
            b = a
            a = a2
        }

        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d
        state[4] += e
    }

    protected override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        buffer[bufferOffset] = 0x80.toByte()

        val size = bufferOffset + 1
        if (size > 56) {
            buffer.fill(0, size, blockSize())
            compress(buffer, 0)
            buffer.fill(0, 0, size)
        } else {
            buffer.fill(0, size, 56)
        }

        buffer[56] = (bitLength ushr 56).toByte()
        buffer[57] = (bitLength ushr 48).toByte()
        buffer[58] = (bitLength ushr 40).toByte()
        buffer[59] = (bitLength ushr 32).toByte()
        buffer[60] = (bitLength ushr 24).toByte()
        buffer[61] = (bitLength ushr 16).toByte()
        buffer[62] = (bitLength ushr  8).toByte()
        buffer[63] = (bitLength        ).toByte()

        compress(buffer, 0)

        val a = state[0]
        val b = state[1]
        val c = state[2]
        val d = state[3]
        val e = state[4]

        return byteArrayOf(
            (a shr 24).toByte(),
            (a shr 16).toByte(),
            (a shr  8).toByte(),
            (a       ).toByte(),
            (b shr 24).toByte(),
            (b shr 16).toByte(),
            (b shr  8).toByte(),
            (b       ).toByte(),
            (c shr 24).toByte(),
            (c shr 16).toByte(),
            (c shr  8).toByte(),
            (c       ).toByte(),
            (d shr 24).toByte(),
            (d shr 16).toByte(),
            (d shr  8).toByte(),
            (d       ).toByte(),
            (e shr 24).toByte(),
            (e shr 16).toByte(),
            (e shr  8).toByte(),
            (e       ).toByte()
        )
    }

    protected override fun resetDigest() {
        x.fill(0)
        state[0] = 1732584193
        state[1] = -271733879
        state[2] = -1732584194
        state[3] = 271733878
        state[4] = -1009589776
    }

    @Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
    private inline infix fun Int.rotateLeft(n: Int): Int = (this shl n) or (this ushr (32 - n))
}
