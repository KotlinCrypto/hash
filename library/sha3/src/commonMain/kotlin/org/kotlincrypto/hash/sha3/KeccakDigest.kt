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

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState
import org.kotlincrypto.endians.LittleEndian
import org.kotlincrypto.sponges.keccak.F1600
import org.kotlincrypto.sponges.keccak.keccakP
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
 * Also see support libraries utilized:
 *  - https://github.com/KotlinCrypto/endians
 *  - https://github.com/KotlinCrypto/sponges
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

        var APos = 0
        var inputPos = offset
        val inputLimit = inputPos + blockSize()

        // Max blockSize is 168 (SHAKE128), so at a maximum only
        // 21 out of 25 state values will ever be modified with
        // input for each permutation.
        while (inputPos < inputLimit) {
            A.addData(
                index = APos++,
                data = LittleEndian.bytesToLong(
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                    input[inputPos++],
                )
            )
        }

        A.keccakP()
    }

    protected override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        buffer[bufferOffset] = dsByte
        buffer.fill(0, bufferOffset + 1)
        val iLast = buffer.lastIndex
        buffer[iLast] = buffer[iLast] xor 0x80.toByte()
        compress(buffer, 0)

        val len = digestLength()
        return extract(state, ByteArray(len), 0, len, 0L)
    }

    protected open fun extract(A: F1600, out: ByteArray, offset: Int, len: Int, bytesRead: Long): ByteArray {
        var outPos = offset
        val outLimit = outPos + len

        val spongeSize = blockSize()
        val spongeLimit = (spongeSize / Long.SIZE_BYTES) + 1

        // Bytes available in the sponge for extraction before another permutation is needed
        var spongeRem = spongeSize - (bytesRead % spongeSize).toInt()
        var spongePos = (spongeSize - spongeRem) / Long.SIZE_BYTES

        while (outPos < outLimit) {
            while (spongePos < spongeLimit) {
                val lane = A[spongePos++]

                val laneOffset = when {
                    spongeRem < Long.SIZE_BYTES -> {
                        // If there is 6 bytes remaining in the sponge
                        // 8 - 6 = 2
                        // indices: 2, 3, 4, 5, 6, 7 = 6 bytes to read out
                        //
                        // If there is 0 bytes remaining in the sponge
                        // 8 - 0 = 8
                        // indices: 8 so no bytes will be written
                        Long.SIZE_BYTES - spongeRem
                    }
                    outPos == offset -> {
                        // Must check first block of the extraction
                        // if it is a partial read (e.g. prior extract
                        // call was for 10 bytes, leaving 6 remaining
                        // in this block of the sponge).
                        val priorRem = spongeRem % Long.SIZE_BYTES
                        if (priorRem == 0) {
                            // Full block. Copy all of it.
                            0
                        } else {
                            // Partial block. Copy remainder.
                            Long.SIZE_BYTES - priorRem
                        }
                    }
                    else -> 0
                }

                var i = 0
                while (outPos < outLimit && (laneOffset + i) < Long.SIZE_BYTES) {
                    val bits = (laneOffset + i++) * Long.SIZE_BYTES
                    out[outPos++] = (lane ushr bits).toByte()
                }

                if (i == 0) {
                    // Either sponge is exhausted, or out is full.
                    break
                }
                spongeRem -= i
            }

            if (spongeRem == 0) {
                // Do another permutation to refill the sponge
                A.keccakP()
                spongePos = 0
                spongeRem = spongeSize
            }
        }

        return out
    }

    protected override fun resetDigest() { state.reset() }

    protected companion object {
        internal const val KECCAK: String = "Keccak"
        internal const val SHA3: String = "SHA3"

        internal const val PAD_KECCAK: Byte = 0x01
        internal const val PAD_SHA3: Byte = 0x06
    }
}
