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
import org.kotlincrypto.core.Xof
import org.kotlincrypto.core.internal.DigestState

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
        TUPLE_HASH.encodeToByteArray(),
        S,
        xOfMode,
        TUPLE_HASH + bitStrength,
        blockSizeFromBitStrength(bitStrength),
        digestLength,
    )

    protected constructor(state: DigestState, digest: TupleDigest): super(state, digest)

    protected final override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        @OptIn(InternalKotlinCryptoApi::class)
        val encL = Xof.Utils.rightEncode(digestLength() * 8L)

        val size = bufferOffset + encL.size
        val newBitLength = bitLength + (encL.size * 8)

        // encL will be at MOST 9 bytes, which is less than the
        // blockSize. This means that no more than 1 compression
        // would be needed to create som space in the buffer to
        // fit everything. So, we good.
        return if (size > buffer.lastIndex) {
            val i = buffer.size - bufferOffset
            encL.copyInto(buffer, bufferOffset, 0, i)
            compress(buffer, 0)
            encL.copyInto(buffer, 0, i, encL.size)
            super.digest(newBitLength, size - buffer.size, buffer)
        } else {
            encL.copyInto(buffer, bufferOffset)
            super.digest(newBitLength, size, buffer)
        }
    }

    protected final override fun updateDigest(input: Byte) {
        // Do encoding manually so unnecessary arrays aren't created
        super.updateDigest(1)
        super.updateDigest(8)
        super.updateDigest(input)
    }

    protected final override fun updateDigest(input: ByteArray, offset: Int, len: Int) {
        @OptIn(InternalKotlinCryptoApi::class)
        val enc = Xof.Utils.leftEncode(len * 8L)
        super.updateDigest(enc, 0, enc.size)
        super.updateDigest(input, offset, len)
    }

    private companion object {
        private const val TUPLE_HASH = "TupleHash"
    }
}
