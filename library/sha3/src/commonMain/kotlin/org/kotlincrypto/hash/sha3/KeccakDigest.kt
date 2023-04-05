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
@file:Suppress("UnnecessaryOptInAnnotation")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.internal.DigestState
import org.kotlincrypto.endians.LittleEndian
import org.kotlincrypto.endians.LittleEndian.Companion.toLittleEndian
import org.kotlincrypto.keccak.F1600
import org.kotlincrypto.keccak.KeccakP
import kotlin.experimental.xor

/**
 * Core abstraction for:
 *  - Keccak-224
 *  - Keccak-256
 *  - Keccak-384
 *  - Keccak-512
 *  - SHA3-224
 *  - SHA3-256
 *  - SHA3-384
 *  - SHA3-512
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * TODO: Add urls for endians and keccak repositories once they are moved
 *
 * @see [SHAKEDigest]
 * */
public sealed class KeccakDigest: Digest {

    // domain separation byte
    private val dsByte: Byte
    private val state: F1600

    @OptIn(InternalKotlinCryptoApi::class)
    protected constructor(
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
        dsByte: Byte,
    ): super(algorithm, blockSize, digestLength) {
        this.dsByte = dsByte
        this.state = F1600()
    }

    @OptIn(InternalKotlinCryptoApi::class)
    protected constructor(state: DigestState, digest: KeccakDigest): super(state) {
        this.dsByte = digest.dsByte
        this.state = digest.state.copy()
    }

    protected final override fun compress(input: ByteArray, offset: Int) {
        val A = state
        val b = input

        var i = 0
        var o = offset
        val limit = o + blockSize()

        // Max blockSize is 168 (SHAKE128), so at a maximum only
        // 21 out of 25 state values will ever be modified with
        // input for each compression/permutation.
        while (o < limit) {
            A.addData(i++, LittleEndian.bytesToLong(b[o++], b[o++], b[o++], b[o++], b[o++], b[o++], b[o++], b[o++]))
        }

        KeccakP(A)
    }

    protected final override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        buffer[bufferOffset] = dsByte
        buffer.fill(0, bufferOffset + 1)
        buffer[buffer.lastIndex] = buffer.last() xor 0x80.toByte()
        compress(buffer, 0)

        return extract(state, ByteArray(digestLength()), 0, digestLength(), 0L)
    }

    protected open fun extract(A: F1600, out: ByteArray, offset: Int, len: Int, bytesRead: Long): ByteArray {
        val spongeSize: Int = blockSize()

        // Bytes remaining in the sponge to be extracted
        // before a permutation is needed
        var spongeRemaining: Int = spongeSize - (bytesRead % spongeSize).toInt()

        var b = offset
        val limit = b + len

        fun writeData(data: LittleEndian): Int {
            var j = if (spongeRemaining < data.size) {
                // if there is 6 bytes remaining in the sponge
                // 8 - 6 = 2
                // j: 2, 3, 4, 5, 6, 7 = 6 bytes written
                //
                // if there is 0 bytes remaining in the sponge
                // 8 - 0 = 8
                // j > 8 so no bytes will be written
                data.size - spongeRemaining
            } else {
                // more than 8 bytes remain,
                // read in the entire data set
                0
            }

            var written = 0
            while (b < limit && j < data.size) {
                out[b++] = data[j++]
                written++
            }

            spongeRemaining -= written
            return written
        }

        var i = (spongeSize - spongeRemaining) / Long.SIZE_BYTES
        while (b < limit) {
            while (i < A.size) {
                if (writeData(A[i++].toLittleEndian()) == 0) break
            }

            if (spongeRemaining == 0) {
                KeccakP(A)
                i = 0
                spongeRemaining = spongeSize
            }
        }

        return out
    }

    protected override fun resetDigest() {
        state.reset()
    }

    protected companion object {
        internal const val KECCAK: String = "Keccak"
        internal const val SHA3: String = "SHA3"

        internal const val PAD_KECCAK: Byte = 0x01
        internal const val PAD_SHA3: Byte = 0x06
    }
}
