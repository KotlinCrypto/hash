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
 * @see [SHAKEDigest]
 * */
public sealed class KeccakDigest: Digest {

    // domain separation byte
    private val dsByte: Byte

    @OptIn(InternalKotlinCryptoApi::class)
    protected constructor(
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
        dsByte: Byte,
    ): super(algorithm, blockSize, digestLength) {
        this.dsByte = dsByte

        // TODO
    }

    @OptIn(InternalKotlinCryptoApi::class)
    protected constructor(state: DigestState, digest: KeccakDigest): super(state) {
        this.dsByte = digest.dsByte

        // TODO
    }

    protected final override fun compress(input: ByteArray, offset: Int) {
        TODO("Not yet implemented")
    }

    protected final override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }

    protected override fun resetDigest() {
        TODO("Not yet implemented")
    }

    @Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
    private inline infix fun Long.rotateLeft(n: Int): Long = (this shl n) or (this ushr -n)

    internal companion object {
        internal const val KECCAK: String = "Keccak"
        internal const val SHA3: String = "SHA3"

        internal const val PAD_KECCAK: Byte = 0x01
        internal const val PAD_SHA3: Byte = 0x06

        private val RC = longArrayOf(
            1L, 32898L, -9223372036854742902L, -9223372034707259392L,
            32907L, 2147483649L, -9223372034707259263L, -9223372036854743031L,
            138L, 136L, 2147516425L, 2147483658L,
            2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L,
            -9223372036854743038L, -9223372036854775680L, 32778L, -9223372034707292150L,
            -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L
        )
    }
}
