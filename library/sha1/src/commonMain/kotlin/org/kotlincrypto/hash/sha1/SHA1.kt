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

import org.kotlincrypto.core.Counter
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
        blockSize = 64,
        digestLength = 20,
    ) {
        this.x = IntArray(80)
        this.state = intArrayOf(1732584193, -271733879, -1732584194, 271733878, -1009589776)
        this.count = Counter.Bit32(incrementBy = blockSize())
    }

    private constructor(other: SHA1): super(other) {
        this.x = other.x.copyOf()
        this.state = other.state.copyOf()
        this.count = other.count.copy()
    }

    public override fun copy(): SHA1 = SHA1(other = this)

    protected override fun compressProtected(input: ByteArray, offset: Int) {
        val x = x

        var j = offset
        for (i in 0..<16) {
            x[i] =
                ((input[j++].toInt() and 0xff) shl 24) or
                ((input[j++].toInt() and 0xff) shl 16) or
                ((input[j++].toInt() and 0xff) shl  8) or
                ((input[j++].toInt() and 0xff)       )
        }

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
        var bytesLo = count.lo
        var bytesHi = count.hi

        // Add in unprocessed bytes from buffer
        // that have yet to be counted as input.
        val lt0 = bytesLo < 0
        bytesLo += bufPos
        if (lt0 && bytesLo >= 0) bytesHi++

        // Convert to bits
        val bitsLo = bytesLo shl 3
        val bitsHi = (bytesHi shl 3) or (bytesLo ushr 29)

        buf[bufPos] = 0x80.toByte()

        if (bufPos + 1 > 56) {
            compressProtected(buf, 0)
            buf.fill(0, 0, 56)
        }

        buf[56] = (bitsHi ushr 24).toByte()
        buf[57] = (bitsHi ushr 16).toByte()
        buf[58] = (bitsHi ushr  8).toByte()
        buf[59] = (bitsHi        ).toByte()
        buf[60] = (bitsLo ushr 24).toByte()
        buf[61] = (bitsLo ushr 16).toByte()
        buf[62] = (bitsLo ushr  8).toByte()
        buf[63] = (bitsLo        ).toByte()

        compressProtected(buf, 0)

        val state = state
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

    protected override fun resetProtected() {
        val state = state
        x.fill(0)
        state[0] = 1732584193
        state[1] = -271733879
        state[2] = -1732584194
        state[3] = 271733878
        state[4] = -1009589776
        count.reset()
    }
}
