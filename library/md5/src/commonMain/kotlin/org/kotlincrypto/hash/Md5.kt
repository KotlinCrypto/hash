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

package org.kotlincrypto.hash

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.internal.DigestState

public class Md5: Digest {

    private val x: IntArray
    private val state: IntArray

    @OptIn(InternalKotlinCryptoApi::class)
    public constructor(): super("MD5", 64, 16) {
        x = IntArray(16)
        state = intArrayOf(
            1732584193,
            -271733879,
            -1732584194,
            271733878,
        )
    }

    @OptIn(InternalKotlinCryptoApi::class)
    private constructor(state: DigestState, md5: Md5): super(state) {
        x = md5.x.copyOf()
        this.state = md5.state.copyOf()
    }

    protected override fun copy(state: DigestState): Digest = Md5(state, this)

    protected override fun compress(buffer: ByteArray) {
        val k = K
        val s = S

        val x = x

        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]

        for (i in 0 until blockSize()) {
            when {
                i < 16 -> {
                    var bI = i * 4
                    x[i] =
                        ((buffer[bI++].toInt() and 0xff)       ) or
                        ((buffer[bI++].toInt() and 0xff) shl  8) or
                        ((buffer[bI++].toInt() and 0xff) shl 16) or
                        ((buffer[bI  ].toInt() and 0xff) shl 24)

                    val g = i + 0
                    val f = ((b and c) or (b.inv() and d)) + a + k[i] + x[g]
                    a = d
                    d = c
                    c = b
                    b += f rotateLeft s[i]
                }
                i < 32 -> {
                    val g = ((5 * i) + 1) % 16
                    val f = ((d and b) or (d.inv() and c)) + a + k[i] + x[g]
                    a = d
                    d = c
                    c = b
                    b += f rotateLeft s[i]
                }
                i < 48 -> {
                    val g = ((3 * i) + 5) % 16
                    val f = (b xor c xor d) + a + k[i] + x[g]
                    a = d
                    d = c
                    c = b
                    b += f rotateLeft s[i]
                }
                else -> {
                    val g = (7 * i) % 16
                    val f = (c xor (b or d.inv())) + a + k[i] + x[g]
                    a = d
                    d = c
                    c = b
                    b += f rotateLeft s[i]
                }
            }
        }

        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d
    }

    protected override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        buffer[bufferOffset] = 0x80.toByte()

        val size = bufferOffset + 1
        if (size > 56) {
            buffer.fill(0, size, blockSize())
            compress(buffer)
            buffer.fill(0, 0, size)
        } else {
            buffer.fill(0, size, 56)
        }

        buffer[56] = (bitLength        ).toByte()
        buffer[57] = (bitLength ushr  8).toByte()
        buffer[58] = (bitLength ushr 16).toByte()
        buffer[59] = (bitLength ushr 24).toByte()
        buffer[60] = (bitLength ushr 32).toByte()
        buffer[61] = (bitLength ushr 40).toByte()
        buffer[62] = (bitLength ushr 48).toByte()
        buffer[63] = (bitLength ushr 56).toByte()

        compress(buffer)

        val a = state[0]
        val b = state[1]
        val c = state[2]
        val d = state[3]

        return byteArrayOf(
            (a       ).toByte(),
            (a shr  8).toByte(),
            (a shr 16).toByte(),
            (a shr 24).toByte(),
            (b       ).toByte(),
            (b shr  8).toByte(),
            (b shr 16).toByte(),
            (b shr 24).toByte(),
            (c       ).toByte(),
            (c shr  8).toByte(),
            (c shr 16).toByte(),
            (c shr 24).toByte(),
            (d       ).toByte(),
            (d shr  8).toByte(),
            (d shr 16).toByte(),
            (d shr 24).toByte(),
        )
    }

    protected override fun resetDigest() {
        x.fill(0)
        state[0] = 1732584193
        state[1] = -271733879
        state[2] = -1732584194
        state[3] = 271733878
    }

    @Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
    private inline infix fun Int.rotateLeft(n: Int): Int = (this shl n) or (this ushr (32 - n))

    private companion object {
        private val S = intArrayOf(
            // round 1 left rotates
            7, 12, 17, 22,
            7, 12, 17, 22,
            7, 12, 17, 22,
            7, 12, 17, 22,
            // round 2 left rotates
            5, 9, 14, 20,
            5, 9, 14, 20,
            5, 9, 14, 20,
            5, 9, 14, 20,
            // round 3 left rotates
            4, 11, 16, 23,
            4, 11, 16, 23,
            4, 11, 16, 23,
            4, 11, 16, 23,
            // round 4 left rotates
            6, 10, 15, 21,
            6, 10, 15, 21,
            6, 10, 15, 21,
            6, 10, 15, 21,
        )

        private val K = intArrayOf(
            // round 1
            -680876936, -389564586, 606105819, -1044525330, -176418897, 1200080426, -1473231341, -45705983,
            1770035416, -1958414417, -42063, -1990404162, 1804603682, -40341101, -1502002290, 1236535329,
            // round 2
            -165796510, -1069501632, 643717713, -373897302, -701558691, 38016083, -660478335, -405537848,
            568446438, -1019803690, -187363961, 1163531501, -1444681467, -51403784, 1735328473, -1926607734,
            // round 3
            -378558, -2022574463, 1839030562, -35309556, -1530992060, 1272893353, -155497632, -1094730640,
            681279174, -358537222, -722521979, 76029189, -640364487, -421815835, 530742520, -995338651,
            // round 4
            -198630844, 1126891415, -1416354905, -57434055, 1700485571, -1894986606, -1051523, -2054922799,
            1873313359, -30611744, -1560198380, 1309151649, -145523070, -1120210379, 718787259, -343485551,
        )
    }
}
