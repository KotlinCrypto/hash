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

import org.kotlincrypto.bitops.bits.Counter
import org.kotlincrypto.bitops.endian.Endian.Big.bePackIntoUnsafe
import org.kotlincrypto.core.digest.Digest

/**
 * Core abstraction for:
 *  - [SHA224]
 *  - [SHA256]
 * */
public sealed class Bit32Digest: Digest {

    private val h: IntArray
    private val x: IntArray
    private val state: IntArray
    private val count: Counter.Bit32

    @Throws(IllegalArgumentException::class)
    protected constructor(bitStrength: Int, h: IntArray): super(
        algorithm = "SHA-$bitStrength",
        blockSize = BLOCK_SIZE,
        digestLength = bitStrength / Byte.SIZE_BITS,
    ) {
        this.h = h
        this.x = IntArray(BLOCK_SIZE)
        this.state = h.copyOf()
        this.count = Counter.Bit32(incrementBy = BLOCK_SIZE)
    }

    protected constructor(other: Bit32Digest): super(other) {
        this.h = other.h
        this.x = other.x.copyOf()
        this.state = other.state.copyOf()
        this.count = other.count.copy()
    }

    public abstract override fun copy(): Bit32Digest

    protected final override fun compressProtected(input: ByteArray, offset: Int) {
        val x = x

        input.bePackIntoUnsafe(x, destOffset = 0, sourceIndexStart = offset, sourceIndexEnd = offset + BLOCK_SIZE)

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

    protected final override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
        val digest = ByteArray(digestLength())
        digestIntoProtected(digest, 0, buf, bufPos)
        return digest
    }

    protected final override fun digestIntoProtected(dest: ByteArray, destOffset: Int, buf: ByteArray, bufPos: Int) {
        val (bitsLo, bitsHi) = count.final(additional = bufPos).asBits()
        buf[bufPos] = 0x80.toByte()

        if (bufPos + 1 > 56) {
            compressProtected(buf, 0)
            buf.fill(0, 0, 56)
        }

        bitsHi.bePackIntoUnsafe(buf, destOffset = 56)
        bitsLo.bePackIntoUnsafe(buf, destOffset = 60)
        compressProtected(buf, 0)

        state.bePackIntoUnsafe(
            dest = dest,
            destOffset = destOffset,
            sourceIndexEnd = digestLength() / Int.SIZE_BYTES,
        )
    }

    protected final override fun resetProtected() {
        x.fill(0)
        h.copyInto(state)
        count.reset()
    }

    private companion object {
        private const val BLOCK_SIZE = 64

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
