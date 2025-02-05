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
package org.kotlincrypto.hash.md

import org.kotlincrypto.bitops.bits.Counter
import org.kotlincrypto.bitops.endian.Endian.Little.leIntAt
import org.kotlincrypto.bitops.endian.Endian.Little.lePackIntoUnsafe
import org.kotlincrypto.core.digest.Digest

/**
 * MD5 implementation
 * */
public class MD5: Digest {

    private val x: IntArray
    private val state: IntArray
    private val count: Counter.Bit32

    public constructor(): super(
        algorithm = "MD5",
        blockSize = BLOCK_SIZE,
        digestLength = 16,
    ) {
        this.x = IntArray(16)
        this.state = H.copyOf()
        this.count = Counter.Bit32(incrementBy = BLOCK_SIZE)
    }

    private constructor(other: MD5): super(other) {
        this.x = other.x.copyOf()
        this.state = other.state.copyOf()
        this.count = other.count.copy()
    }

    public override fun copy(): MD5 = MD5(other = this)

    protected override fun compressProtected(input: ByteArray, offset: Int) {
        val k = K
        val s = S

        val x = x
        val state = state

        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]

        for (i in 0..<16) {
            val xi = input.leIntAt(offset = (i * Int.SIZE_BYTES) + offset)
            x[i] = xi
            // val g = i + 0
            val f = ((b and c) or (b.inv() and d)) + a + k[i] + xi // x[g]
            a = d
            d = c
            c = b
            b += f.rotateLeft(s[i])
        }

        for (i in 16..<32) {
            val g = ((5 * i) + 1) % 16
            val f = ((d and b) or (d.inv() and c)) + a + k[i] + x[g]
            a = d
            d = c
            c = b
            b += f.rotateLeft(s[i])
        }

        for (i in 32..<48) {
            val g = ((3 * i) + 5) % 16
            val f = (b xor c xor d) + a + k[i] + x[g]
            a = d
            d = c
            c = b
            b += f.rotateLeft(s[i])
        }

        for (i in 48..<64) {
            val g = (7 * i) % 16
            val f = (c xor (b or d.inv())) + a + k[i] + x[g]
            a = d
            d = c
            c = b
            b += f.rotateLeft(s[i])
        }

        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d

        count.increment()
    }

    protected override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
        val digest = ByteArray(digestLength())
        digestIntoProtected(digest, 0, buf, bufPos)
        return digest
    }

    protected override fun digestIntoProtected(dest: ByteArray, destOffset: Int, buf: ByteArray, bufPos: Int) {
        val (bitsLo, bitsHi) = count.final(additional = bufPos).asBits()
        buf[bufPos] = 0x80.toByte()

        if (bufPos + 1 > 56) {
            compressProtected(buf, 0)
            buf.fill(0, 0, 56)
        }

        bitsLo.lePackIntoUnsafe(buf, destOffset = 56)
        bitsHi.lePackIntoUnsafe(buf, destOffset = 60)
        compressProtected(buf, 0)

        state.lePackIntoUnsafe(dest = dest, destOffset = destOffset)
    }

    protected override fun resetProtected() {
        x.fill(0)
        H.copyInto(state)
        count.reset()
    }

    private companion object {
        private const val BLOCK_SIZE = 64

        private val H = intArrayOf(1732584193, -271733879, -1732584194, 271733878)

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
             -680876936,  -389564586,   606105819, -1044525330,
             -176418897,  1200080426, -1473231341,   -45705983,
             1770035416, -1958414417,      -42063, -1990404162,
             1804603682,   -40341101, -1502002290,  1236535329,
            // round 2
             -165796510, -1069501632,   643717713,  -373897302,
             -701558691,    38016083,  -660478335,  -405537848,
              568446438, -1019803690,  -187363961,  1163531501,
            -1444681467,   -51403784,  1735328473, -1926607734,
            // round 3
                -378558, -2022574463,  1839030562,   -35309556,
            -1530992060,  1272893353,  -155497632, -1094730640,
              681279174,  -358537222,  -722521979,    76029189,
             -640364487, - 421815835,   530742520,  -995338651,
            // round 4
             -198630844,  1126891415, -1416354905,   -57434055,
             1700485571, -1894986606,    -1051523, -2054922799,
             1873313359,   -30611744, -1560198380,  1309151649,
             -145523070, -1120210379,   718787259,  -343485551,
        )
    }
}
