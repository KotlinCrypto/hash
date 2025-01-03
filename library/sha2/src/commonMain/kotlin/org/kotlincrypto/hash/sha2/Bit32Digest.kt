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

package org.kotlincrypto.hash.sha2

import org.kotlincrypto.core.Counter
import org.kotlincrypto.core.digest.Digest

/**
 * Core abstraction for:
 *  - SHA-224
 *  - SHA-256
 * */
public sealed class Bit32Digest: Digest {

    private val h0: Int
    private val h1: Int
    private val h2: Int
    private val h3: Int
    private val h4: Int
    private val h5: Int
    private val h6: Int
    private val h7: Int

    private val x: IntArray
    private val state: IntArray
    private val count: Counter.Bit32

    @Throws(IllegalArgumentException::class)
    protected constructor(
        d: Int,
        h0: Int,
        h1: Int,
        h2: Int,
        h3: Int,
        h4: Int,
        h5: Int,
        h6: Int,
        h7: Int,
    ): super(
        algorithm = "SHA-$d",
        blockSize = 64,
        digestLength = d / 8,
    ) {
        this.h0 = h0
        this.h1 = h1
        this.h2 = h2
        this.h3 = h3
        this.h4 = h4
        this.h5 = h5
        this.h6 = h6
        this.h7 = h7
        this.x = IntArray(64)
        this.state = intArrayOf(h0, h1, h2, h3, h4, h5, h6, h7)
        this.count = Counter.Bit32(incrementBy = blockSize())
    }

    protected constructor(other: Bit32Digest): super(other) {
        this.h0 = other.h0
        this.h1 = other.h1
        this.h2 = other.h2
        this.h3 = other.h3
        this.h4 = other.h4
        this.h5 = other.h5
        this.h6 = other.h6
        this.h7 = other.h7
        this.x = other.x.copyOf()
        this.state = other.state.copyOf()
        this.count = other.count.copy()
    }

    public abstract override fun copy(): Bit32Digest

    protected final override fun compressProtected(input: ByteArray, offset: Int) {
        val x = x

        var j = offset
        for (i in 0..<16) {
            x[i] =
                ((input[j++].toInt() and 0xff) shl 24) or
                ((input[j++].toInt() and 0xff) shl 16) or
                ((input[j++].toInt() and 0xff) shl  8) or
                ((input[j++].toInt() and 0xff)       )
        }

        for (i in 16..<64) {
            val x15 = x[i - 15]
            val s0 =
                ((x15 ushr  7) or (x15 shl 25)) xor
                ((x15 ushr 18) or (x15 shl 14)) xor
                ((x15 ushr  3))
            val x2 = x[i - 2]
            val s1 =
                ((x2 ushr 17) or (x2 shl 15)) xor
                ((x2 ushr 19) or (x2 shl 13)) xor
                ((x2 ushr 10))
            val x16 = x[i - 16]
            val x7 = x[i - 7]
            x[i] = x16 + s0 + x7 + s1
        }

        val k = K
        val state = state

        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]
        var f = state[5]
        var g = state[6]
        var h = state[7]

        for (i in 0..<64) {
            val s0 =
                ((a ushr  2) or (a shl 30)) xor
                ((a ushr 13) or (a shl 19)) xor
                ((a ushr 22) or (a shl 10))
            val s1 =
                ((e ushr  6) or (e shl 26)) xor
                ((e ushr 11) or (e shl 21)) xor
                ((e ushr 25) or (e shl  7))

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

        count.increment()
    }

    protected final override fun digestProtected(buffer: ByteArray, offset: Int): ByteArray {
        var bytesLo = count.lo
        var bytesHi = count.hi

        // Add in unprocessed bytes from buffer
        // that have yet to be counted as input.
        val lt0 = bytesLo < 0
        bytesLo += offset
        if (lt0 && bytesLo >= 0) bytesHi++

        // Convert to bits
        val bitsLo = bytesLo shl 3
        val bitsHi = (bytesHi shl 3) or (bytesLo ushr 29)

        buffer[offset] = 0x80.toByte()

        val size = offset + 1
        if (size > 56) {
            buffer.fill(0, size, 64)
            compressProtected(buffer, 0)
            buffer.fill(0, 0, size)
        } else {
            buffer.fill(0, size, 56)
        }

        buffer[56] = (bitsHi ushr 24).toByte()
        buffer[57] = (bitsHi ushr 16).toByte()
        buffer[58] = (bitsHi ushr  8).toByte()
        buffer[59] = (bitsHi        ).toByte()
        buffer[60] = (bitsLo ushr 24).toByte()
        buffer[61] = (bitsLo ushr 16).toByte()
        buffer[62] = (bitsLo ushr  8).toByte()
        buffer[63] = (bitsLo        ).toByte()

        compressProtected(buffer, 0)

        val state = state
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
        a: Int,
        b: Int,
        c: Int,
        d: Int,
        e: Int,
        f: Int,
        g: Int,
        h: Int,
    ): ByteArray

    protected final override fun resetProtected() {
        val state = state
        x.fill(0)
        state[0] = h0
        state[1] = h1
        state[2] = h2
        state[3] = h3
        state[4] = h4
        state[5] = h5
        state[6] = h6
        state[7] = h7
        count.reset()
    }

    private companion object {
        private val K = intArrayOf(
             1116352408,  1899447441, -1245643825,  -373957723,
              961987163,  1508970993, -1841331548, -1424204075,
             -670586216,   310598401,   607225278,  1426881987,
             1925078388, -2132889090, -1680079193, -1046744716,
             -459576895,  -272742522,   264347078,   604807628,
              770255983,  1249150122,  1555081692,  1996064986,
            -1740746414, -1473132947, -1341970488, -1084653625,
             -958395405,  -710438585,   113926993,   338241895,
              666307205,   773529912,  1294757372,  1396182291,
             1695183700,  1986661051, -2117940946, -1838011259,
            -1564481375, -1474664885, -1035236496,  -949202525,
             -778901479,  -694614492,  -200395387,   275423344,
              430227734,   506948616,   659060556,   883997877,
              958139571,  1322822218,  1537002063,  1747873779,
             1955562222,  2024104815, -2067236844, -1933114872,
            -1866530822, -1538233109, -1090935817,  -965641998,
        )
    }
}
