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
@file:Suppress("LocalVariableName", "RedundantVisibilityModifier")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.error.InvalidParameterException

/**
 * Core abstraction for:
 *  - [TupleHash128]
 *  - [TupleHash256]
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#5%20TupleHash
 * */
public sealed class TupleDigest: SHAKEDigest {

    @Throws(InvalidParameterException::class)
    protected constructor(
        S: ByteArray?,
        xOfMode: Boolean,
        bitStrength: Int,
        digestLength: Int,
    ): super(
        N = TUPLE_HASH.encodeToByteArray(),
        S = S,
        xOfMode = xOfMode,
        algorithm = TUPLE_HASH + bitStrength,
        blockSize = blockSizeFromBitStrength(bitStrength),
        digestLength = digestLength,
    )

    protected constructor(other: TupleDigest): super(other)

    public abstract override fun copy(): TupleDigest

    protected final override fun finalizeAndExtractTo(
        dest: ByteArray,
        destOffset: Int,
        buf: ByteArray,
        bufPos: Int,
        len: Int,
    ): ByteArray {
        val encLenBits = digestLength().rightEncodeBits()
        val needed = bufPos + encLenBits.size

        // encLenBits will be at MOST 9 bytes, which is less than
        // the blockSize. This means that no more than 1 compression
        // would be needed to create some space in the buffer to fit
        // everything.
        return if (needed > buf.lastIndex) {
            val i = buf.size - bufPos
            encLenBits.copyInto(buf, bufPos, 0, i)
            compressProtected(buf, 0)
            // + 1 is to not include index where dsByte will be placed
            buf.fill(0, encLenBits.size - i + 1)
            encLenBits.copyInto(buf, 0, i, encLenBits.size)
            super.finalizeAndExtractTo(dest, destOffset, buf, needed - buf.size, len)
        } else {
            encLenBits.copyInto(buf, bufPos)
            super.finalizeAndExtractTo(dest, destOffset, buf, needed, len)
        }
    }

    protected final override fun updateProtected(input: Byte) {
        // Do encoding manually so unnecessary arrays aren't created
        super.updateProtected(1)
        super.updateProtected(8)
        super.updateProtected(input)
    }

    protected final override fun updateProtected(input: ByteArray, offset: Int, len: Int) {
        val encLenBits = len.leftEncodeBits()
        super.updateProtected(encLenBits, 0, encLenBits.size)
        super.updateProtected(input, offset, len)
    }

    private companion object {
        private const val TUPLE_HASH = "TupleHash"
    }
}
