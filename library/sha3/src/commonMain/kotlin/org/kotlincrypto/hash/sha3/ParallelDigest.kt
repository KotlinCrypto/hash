/*
 * Copyright (c) 2023 KotlinCrypto
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
@file:Suppress("LocalVariableName", "NOTHING_TO_INLINE", "RedundantVisibilityModifier")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.xof.Xof
import org.kotlincrypto.error.InvalidParameterException
import org.kotlincrypto.error.requireParam

/**
 * Core abstraction for:
 *  - [ParallelHash128]
 *  - [ParallelHash256]
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#6%20ParallelHash
 * */
public sealed class ParallelDigest: SHAKEDigest {

    private val inner: SHAKEDigest
    private val innerBlockSize: Int
    private var innerPos: Int
    private var countLo: Int
    private var countHi: Int

    @Throws(InvalidParameterException::class)
    protected constructor(
        S: ByteArray?,
        B: Int,
        xOfMode: Boolean,
        bitStrength: Int,
        digestLength: Int,
    ): super(
        N = PARALLEL_HASH.encodeToByteArray(),
        S = S,
        xOfMode = xOfMode,
        algorithm = PARALLEL_HASH + bitStrength,
        blockSize = blockSizeFromBitStrength(bitStrength),
        digestLength = digestLength,
    ) {
        requireParam(B > 0) { "B must be greater than 0" }

        this.inner = when (bitStrength) {
            BIT_STRENGTH_128 -> CSHAKE128(N = null, S = null)
            BIT_STRENGTH_256 -> CSHAKE256(N = null, S = null)
            else -> throw IllegalArgumentException("bitStrength must be $BIT_STRENGTH_128 or $BIT_STRENGTH_256")
        }
        this.innerBlockSize = B
        this.innerPos = 0
        this.countLo = 0
        this.countHi = 0

        @OptIn(InternalKotlinCryptoApi::class)
        val encB = Xof.Utils.leftEncode(B)
        super.updateProtected(encB, 0, encB.size)
    }

    protected constructor(other: ParallelDigest): super(other) {
        this.inner = other.inner.copy()
        this.innerBlockSize = other.innerBlockSize
        this.innerPos = other.innerPos
        this.countLo = other.countLo
        this.countHi = other.countHi
    }

    public abstract override fun copy(): ParallelDigest

    protected final override fun finalizeAndExtractTo(
        dest: ByteArray,
        destOffset: Int,
        buf: ByteArray,
        bufPos: Int,
        len: Int,
    ): ByteArray {
        val buffered = if (innerPos != 0) {
            // If there's any buffered bytes left,
            // process them and prefix to the
            // buffer here as additional input.
            innerPos = 0
            incrementCount()
            inner.digest()
        } else {
            ByteArray(0)
        }

        @OptIn(InternalKotlinCryptoApi::class)
        val final = buffered + Xof.Utils.rightEncode(lo = countLo, hi = countHi) + digestLength().rightEncodeBits()

        val needed = bufPos + final.size

        // final will be at MOST (64 + 9 + 9) = 82 bytes, which is
        // less than outer digest's blockSize (136 or 168 depending
        // on bitStrength). This means that no more than 1 compression
        // would be needed to create some space in the buffer to fit
        // everything.
        return if (needed > buf.lastIndex) {
            val i = buf.size - bufPos
            final.copyInto(buf, bufPos, 0, i)
            compressProtected(buf, 0)
            // + 1 is to not include index where dsByte will be placed
            buf.fill(0, final.size - i + 1)
            final.copyInto(buf, 0, i, final.size)
            super.finalizeAndExtractTo(dest, destOffset, buf, needed - buf.size, len)
        } else {
            final.copyInto(buf, bufPos)
            super.finalizeAndExtractTo(dest, destOffset, buf, needed, len)
        }
    }

    protected final override fun updateProtected(input: Byte) {
        inner.update(input)
        if (++innerPos != innerBlockSize) return
        processBlock()
        innerPos = 0
    }

    protected final override fun updateProtected(input: ByteArray, offset: Int, len: Int) {
        var inputPos = offset
        val inputLimit = inputPos + len
        var innerPos = innerPos

        if (innerPos > 0) {
            if (innerPos + len < innerBlockSize) {
                // Not enough input to process a block
                inner.update(input, offset, len)
                this.innerPos = innerPos + len
                return
            }

            val needed = innerBlockSize - innerPos
            inner.update(input, inputPos, needed)
            processBlock()
            innerPos = 0
            inputPos += needed
        }

        while (inputPos < inputLimit) {
            val nextPos = inputPos + innerBlockSize

            if (nextPos > inputLimit) {
                innerPos = inputLimit - inputPos
                inner.update(input, inputPos, innerPos)
                break
            }

            inner.update(input, inputPos, innerBlockSize)
            processBlock()
            inputPos = nextPos
        }

        this.innerPos = innerPos
    }

    private inline fun processBlock() {
        super.updateProtected(inner.digest(), 0, inner.digestLength())
        incrementCount()
    }

    protected final override fun resetProtected() {
        super.resetProtected()
        this.inner.reset()
        this.innerPos = 0
        this.countLo = 0
        this.countHi = 0

        @OptIn(InternalKotlinCryptoApi::class)
        val encBSize = Xof.Utils.leftEncode(innerBlockSize)
        super.updateProtected(encBSize, 0, encBSize.size)
    }

    private inline fun incrementCount() { if (++countLo == 0) countHi++ }

    private companion object {
        private const val PARALLEL_HASH = "ParallelHash"
    }
}
