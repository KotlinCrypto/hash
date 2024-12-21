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
package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.internal.DigestState
import org.kotlincrypto.core.xof.Xof

/**
 * Core abstraction for:
 *  - ParallelHash128
 *  - ParallelHash256
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#6%20ParallelHash
 *
 * Also see support libraries utilized:
 *  - https://github.com/KotlinCrypto/endians
 *  - https://github.com/KotlinCrypto/sponges
 * */
public sealed class ParallelDigest: SHAKEDigest {

    private val inner: SHAKEDigest
    private val innerBuf: ByteArray
    private var innerBufOffs: Int
    private var processCount: Long

    protected constructor(
        S: ByteArray?,
        B: Int,
        xOfMode: Boolean,
        bitStrength: Int,
        digestLength: Int,
    ): super(
        PARALLEL_HASH.encodeToByteArray(),
        S,
        xOfMode,
        PARALLEL_HASH + bitStrength,
        blockSizeFromBitStrength(bitStrength),
        digestLength,
    ) {
        require(B > 0) { "B must be greater than 0" }

        this.inner = when (bitStrength) {
            BIT_STRENGTH_128 -> CSHAKE128(null, null)
            BIT_STRENGTH_256 -> CSHAKE256(null, null)
            else -> throw IllegalArgumentException("bitStrength must be $BIT_STRENGTH_128 or $BIT_STRENGTH_256")
        }
        this.innerBuf = ByteArray(B)
        this.innerBufOffs = 0
        this.processCount = 0L

        @OptIn(InternalKotlinCryptoApi::class)
        val encB = Xof.Utils.leftEncode(B.toLong())
        super.updateDigest(encB, 0, encB.size)
    }

    protected constructor(state: DigestState, digest: ParallelDigest): super(state, digest) {
        this.inner = digest.inner.copy() as SHAKEDigest
        this.innerBuf = digest.innerBuf.copyOf()
        this.innerBufOffs = digest.innerBufOffs
        this.processCount = digest.processCount
    }

    protected final override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        val buffered = if (innerBufOffs != 0) {
            // If there's any buffered bytes left,
            // process them to append them to the
            // buffer here as additional input.
            inner.update(innerBuf, 0, innerBufOffs)
            processCount++
            innerBufOffs = 0
            inner.digest()
        } else {
            ByteArray(0)
        }

        @OptIn(InternalKotlinCryptoApi::class)
        val final = buffered + Xof.Utils.rightEncode(processCount) + Xof.Utils.rightEncode(digestLength() * 8L)

        val size = bufferOffset + final.size
        val newBitLength = bitLength + (final.size * 8)

        // final will be at MOST (64 + 9 + 9) = 82 bytes, which is
        // less than outer digest's blockSize (136 or 168 depending
        // on bitStrength). This means that no more than 1 compression
        // would be needed to create some space in the buffer to fit
        // everything. So, we good.
        return if (size > buffer.lastIndex) {
            val i = buffer.size - bufferOffset
            final.copyInto(buffer, bufferOffset, 0, i)
            compress(buffer, 0)
            final.copyInto(buffer, 0, i, final.size)
            super.digest(newBitLength, size - buffer.size, buffer)
        } else {
            final.copyInto(buffer, bufferOffset)
            super.digest(newBitLength, size, buffer)
        }
    }

    protected final override fun updateDigest(input: Byte) {
        val offsBuf = innerBufOffs++
        innerBuf[offsBuf] = input
        if (offsBuf + 1 != innerBuf.size) return
        processBlock(innerBuf, 0)
        innerBufOffs = 0
    }

    protected final override fun updateDigest(input: ByteArray, offset: Int, len: Int) {
        val buf = innerBuf
        val blockSize = buf.size
        var offsInput = offset
        val limit = offsInput + len
        var offsBuf = innerBufOffs

        if (offsBuf > 0) {
            if (offsBuf + len < blockSize) {
                input.copyInto(buf, offsBuf, offsInput, limit)
                innerBufOffs = offsBuf + len
                return
            }

            val needed = blockSize - offsBuf
            input.copyInto(buf, offsBuf, offsInput, offsInput + needed)
            processBlock(buf, 0)
            offsBuf = 0
            offsInput += needed
        }

        while (offsInput < limit) {
            val offsNext = offsInput + blockSize

            if (offsNext > limit) {
                input.copyInto(buf, 0, offsInput, limit)
                offsBuf = limit - offsInput
                break
            }

            processBlock(input, offsInput)
            offsInput = offsNext
        }

        innerBufOffs = offsBuf
    }

    private fun processBlock(input: ByteArray, offset: Int) {
        inner.update(input, offset, innerBuf.size)
        super.updateDigest(inner.digest(), 0, inner.digestLength())
        processCount++
    }

    protected final override fun resetDigest() {
        super.resetDigest()
        this.innerBuf.fill(0)
        this.innerBufOffs = 0
        this.processCount = 0L

        // No need to reset inner as digest() is always called
        // when processing blocks which leaves it in a perpetually
        // reset state.
//        this.inner.reset()

        @OptIn(InternalKotlinCryptoApi::class)
        val encB = Xof.Utils.leftEncode(innerBuf.size.toLong())
        super.updateDigest(encB, 0, encB.size)
    }

    private companion object {
        private const val PARALLEL_HASH = "ParallelHash"
    }
}
