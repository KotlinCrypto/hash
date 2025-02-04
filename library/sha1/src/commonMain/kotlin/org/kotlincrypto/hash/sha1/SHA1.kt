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
package org.kotlincrypto.hash.sha1

import org.kotlincrypto.bitops.bits.Counter
import org.kotlincrypto.bitops.endian.Endian.Big.bePackIntoUnsafe
import org.kotlincrypto.core.digest.Digest

/**
 * SHA-1 implementation
 * */
public class SHA1: Digest {

    private val x: IntArray
    private val state: IntArray
    private val count: Counter.Bit32

    public constructor(): super(
        algorithm = "SHA-1",
        blockSize = BLOCK_SIZE,
        digestLength = 20,
    ) {
        this.x = IntArray(80)
        this.state = H.copyOf()
        this.count = Counter.Bit32(incrementBy = BLOCK_SIZE)
    }

    private constructor(other: SHA1): super(other) {
        this.x = other.x.copyOf()
        this.state = other.state.copyOf()
        this.count = other.count.copy()
    }

    public override fun copy(): SHA1 = SHA1(other = this)

    protected override fun compressProtected(input: ByteArray, offset: Int) {
        val x = x

        input.bePackIntoUnsafe(x, destOffset = 0, sourceIndexStart = offset, sourceIndexEnd = offset + BLOCK_SIZE)

        for (i in 16..<80) {
            x[i] = (x[i - 3] xor x[i - 8] xor x[i - 14] xor x[i - 16]).rotateLeft(1)
        }

        val state = state
        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]

        for (i in 0..<20) {
            val f = d xor (b and (c xor d))
            val k = 1518500249
            val a2 = (a.rotateLeft(5)) + f + e + k + x[i]
            e = d
            d = c
            c = b.rotateLeft(30)
            b = a
            a = a2
        }

        for (i in 20..<40) {
            val f = b xor c xor d
            val k = 1859775393
            val a2 = (a.rotateLeft(5)) + f + e + k + x[i]
            e = d
            d = c
            c = b.rotateLeft(30)
            b = a
            a = a2
        }

        for (i in 40..<60) {
            val f = (b and c) or (b and d) or (c and d)
            val k = -1894007588
            val a2 = (a.rotateLeft(5)) + f + e + k + x[i]
            e = d
            d = c
            c = b.rotateLeft(30)
            b = a
            a = a2
        }

        for (i in 60..<80) {
            val f = b xor c xor d
            val k = -899497514
            val a2 = (a.rotateLeft(5)) + f + e + k + x[i]
            e = d
            d = c
            c = b.rotateLeft(30)
            b = a
            a = a2
        }

        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d
        state[4] += e

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

        bitsHi.bePackIntoUnsafe(buf, destOffset = 56)
        bitsLo.bePackIntoUnsafe(buf, destOffset = 60)
        compressProtected(buf, 0)

        state.bePackIntoUnsafe(dest = dest, destOffset = destOffset)
    }

    protected override fun resetProtected() {
        x.fill(0)
        H.copyInto(state)
        count.reset()
    }

    private companion object {
        private const val BLOCK_SIZE = 64

        private val H = intArrayOf(1732584193, -271733879, -1732584194, 271733878, -1009589776)
    }
}
