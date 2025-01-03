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
@file:Suppress("LocalVariableName")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.xof.Xof

/**
 * Core abstraction for:
 *  - TupleHash128
 *  - TupleHash256
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#5%20TupleHash
 *
 * Also see support libraries utilized:
 *  - https://github.com/KotlinCrypto/endians
 *  - https://github.com/KotlinCrypto/sponges
 * */
public sealed class TupleDigest: SHAKEDigest {

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

    protected final override fun digestProtected(buffer: ByteArray, offset: Int): ByteArray {
        @OptIn(InternalKotlinCryptoApi::class)
        val encL = Xof.Utils.rightEncode(digestLength() * 8L)

        val size = offset + encL.size

        // encL will be at MOST 9 bytes, which is less than the
        // blockSize. This means that no more than 1 compression
        // would be needed to create som space in the buffer to
        // fit everything. So, we good.
        return if (size > buffer.lastIndex) {
            val i = buffer.size - offset
            encL.copyInto(buffer, offset, 0, i)
            compressProtected(buffer, 0)
            encL.copyInto(buffer, 0, i, encL.size)
            super.digestProtected(buffer, size - buffer.size)
        } else {
            encL.copyInto(buffer, offset)
            super.digestProtected(buffer, size)
        }
    }

    protected final override fun updateProtected(input: Byte) {
        // Do encoding manually so unnecessary arrays aren't created
        super.updateProtected(1)
        super.updateProtected(8)
        super.updateProtected(input)
    }

    protected final override fun updateProtected(input: ByteArray, offset: Int, len: Int) {
        // TODO: check size to see if can utilize Int
        val numBits = len * 8L
        @OptIn(InternalKotlinCryptoApi::class)
        val enc = Xof.Utils.leftEncode(numBits)

        super.updateProtected(enc, 0, enc.size)
        super.updateProtected(input, offset, len)
    }

    private companion object {
        private const val TUPLE_HASH = "TupleHash"
    }
}
