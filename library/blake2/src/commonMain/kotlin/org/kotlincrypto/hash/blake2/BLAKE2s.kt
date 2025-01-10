/*
 * Copyright (c) 2025 Matthew Nelson
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
@file:Suppress("FunctionName")

package org.kotlincrypto.hash.blake2

import org.kotlincrypto.bitops.bits.Counter
import org.kotlincrypto.bitops.endian.Endian.Little.lePackIntoUnsafe
import org.kotlincrypto.hash.blake2.internal.*

/**
 * BLAKE2s implementation
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 *
 * @see [BLAKE2s_128]
 * @see [BLAKE2s_160]
 * @see [BLAKE2s_224]
 * @see [BLAKE2s_256]
 * */
public class BLAKE2s: BLAKE2Digest {

    private val v: IntArray
    private val h: IntArray
    private var m: Bit32Message?
    private val t: Counter.Bit32

    /**
     * Primary constructor for creating a new [BLAKE2s] instance
     *
     * @throws [IllegalArgumentException] when:
     *  - [bitStrength] is less than 8
     *  - [bitStrength] is greater than 256
     *  - [bitStrength] is not a factor of 8
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(bitStrength: Int): super(BLOCK_SIZE_S, bitStrength) {
        this.v = IntArray(16)
        this.h = IV.copyOf()
        this.h[0] = IV[0] xor (digestLength() or 16842752)
        this.m = null
        this.t = Counter.Bit32(incrementBy = blockSize())
    }

    private constructor(other: BLAKE2s): super(other) {
        this.v = other.v.copyOf()
        this.h = other.h.copyOf()
        this.m = other.m?.copy()
        this.t = other.t.copy()
    }

    public override fun copy(): BLAKE2s = BLAKE2s(this)

    protected override fun compressProtected(input: ByteArray, offset: Int) {
        val m = m

        if (m == null) {
            // The Digest abstraction will call compressProtected whenever 1 block of
            // input is available for processing. BLAKE2 requires a finalization byte
            // to be set for the last compression. Because of this and not knowing if
            // more input will be coming, compressions are always 1 block behind until
            // digestProtected is called which will polish things off.
            //
            // This scenario could arise if the Digest is updated with 1 full block of
            // bytes (compressProtected is called and that block would be processed
            // with no finalization flag), then digest gets called without adding more
            // input (so a bufPos of 0); the Digest abstraction's buffer would be empty,
            // but the last block of input had already been processed!
            this.m = Bit32Message(input, offset)
            return
        }

        t.increment()
        F(h = h, m = m, tLo = t.lo, tHi = t.hi, f = 0)

        // Populate m with this invocation's input to process next
        m.populate(input, offset)
    }

    protected override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
        var m = m

        if (m != null) {
            // Have a buffered block that needs to be processed

            if (bufPos == 0) {
                // No additional input, buffered block is final.
                t.increment()
            } else {
                // Process buffered block and update m with buf
                compressProtected(buf, 0)
            }
        } else {
            // compressProtected has not been called yet. Input
            // in the buffer is all that has been collected.
            m = Bit32Message(buf, 0)

            // Set global m so that reset call after return will
            // clear it out for us.
            this.m = m
        }

        val (tLo, tHi) = t.final(additional = bufPos)

        val h = h
        F(h = h, m = m, tLo = tLo, tHi = tHi, f = -1)

        val len = digestLength()
        val rem = len % Int.SIZE_BYTES
        val iHEnd = len / Int.SIZE_BYTES

        val out = h.lePackIntoUnsafe(
            dest = ByteArray(len),
            destOffset = 0,
            sourceIndexStart = 0,
            sourceIndexEnd = iHEnd
        )

        if (rem > 0) {
            h[iHEnd].lePackIntoUnsafe(
                dest = out,
                destOffset = len - rem,
                sourceIndexStart = 0,
                sourceIndexEnd = rem,
            )
        }

        return out
    }

    @Suppress("KotlinRedundantDiagnosticSuppress", "NOTHING_TO_INLINE")
    private inline fun F(h: IntArray, m: Bit32Message, tLo: Int, tHi: Int, f: Int) {
        val iv = IV
        val v = v

        // Initialize local work vector v
        h.copyInto(v)
        iv.copyInto(v, h.size, 0, 4)
        v[12] = tLo xor iv[4]
        v[13] = tHi xor iv[5]
        v[14] = f   xor iv[6]
        v[15] =         iv[7]

        // Cryptographic mixing
        val s = SIGMA

        for (i in 0..<ROUND_COUNT) {
            G(v = v, a = 0, b = 4, c =  8, d = 12, x = m[s[i][ 0]], y = m[s[i][ 1]])
            G(v = v, a = 1, b = 5, c =  9, d = 13, x = m[s[i][ 2]], y = m[s[i][ 3]])
            G(v = v, a = 2, b = 6, c = 10, d = 14, x = m[s[i][ 4]], y = m[s[i][ 5]])
            G(v = v, a = 3, b = 7, c = 11, d = 15, x = m[s[i][ 6]], y = m[s[i][ 7]])

            G(v = v, a = 0, b = 5, c = 10, d = 15, x = m[s[i][ 8]], y = m[s[i][ 9]])
            G(v = v, a = 1, b = 6, c = 11, d = 12, x = m[s[i][10]], y = m[s[i][11]])
            G(v = v, a = 2, b = 7, c =  8, d = 13, x = m[s[i][12]], y = m[s[i][13]])
            G(v = v, a = 3, b = 4, c =  9, d = 14, x = m[s[i][14]], y = m[s[i][15]])
        }

        // xor the two halves
        for (i in 0..<8) {
            h[i] = h[i] xor v[i] xor v[i + 8]
        }
    }

    @Suppress("KotlinRedundantDiagnosticSuppress", "NOTHING_TO_INLINE")
    private inline fun G(v: IntArray, a: Int, b: Int, c: Int, d: Int, x: Int, y: Int) {
        var va = v[a]
        var vb = v[b]
        var vc = v[c]
        var vd = v[d]

        va = (va  +  vb + x)
        vd = (vd xor va    ).rotateRight(R1)
        vc = (vc  +  vd    )
        vb = (vb xor vc    ).rotateRight(R2)
        va = (va  +  vb + y)
        vd = (vd xor va    ).rotateRight(R3)
        vc = (vc  +  vd    )
        vb = (vb xor vc    ).rotateRight(R4)

        v[a] = va
        v[b] = vb
        v[c] = vc
        v[d] = vd
    }

    protected override fun resetProtected() {
        v.fill(0)
        IV.copyInto(h)
        h[0] = IV[0] xor (digestLength() or 16842752)
        m?.fill()
        m = null
        t.reset()
    }

    private companion object {
        private const val ROUND_COUNT = 10

        private const val R1 = 16
        private const val R2 = 12
        private const val R3 = 8
        private const val R4 = 7

        private val IV = intArrayOf(
            1779033703, -1150833019, 1013904242, -1521486534,
            1359893119, -1694144372,  528734635,  1541459225,
        )
    }
}
