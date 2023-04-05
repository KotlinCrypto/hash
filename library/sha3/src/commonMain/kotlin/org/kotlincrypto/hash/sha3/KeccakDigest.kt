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
import org.kotlincrypto.keccak.State
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

        return out(state, digestLength())
    }

    // TODO: Refactor to account for XOFs
    protected open fun out(A: F1600, outLength: Int): ByteArray {
        val out = ByteArray(outLength)

        try {
            var o = 0
            for (i in 0 until A.size) {
                val data = A[i].toLittleEndian()
                out[o++] = data[0]
                out[o++] = data[1]
                out[o++] = data[2]
                out[o++] = data[3]
                out[o++] = data[4]
                out[o++] = data[5]
                out[o++] = data[6]
                out[o++] = data[7]
            }
        } catch (_: IndexOutOfBoundsException) {
            // ignore
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
