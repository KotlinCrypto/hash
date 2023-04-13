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

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.Xof
import org.kotlincrypto.core.internal.DigestState
import kotlin.jvm.JvmStatic

/**
 * ParallelHash128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#6%20ParallelHash
 *
 * @see [xOf]
 * */
public class ParallelHash128: ParallelDigest {

    /**
     * Creates a new [ParallelHash128] [Digest] instance with a fixed output
     * [digestLength].
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [B] The block size for the inner hash function in bytes
     * @throws [IllegalArgumentException] If [B] is less than 1
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(S: ByteArray?, B: Int): this(S, B, xOfMode = false)

    private constructor(
        S: ByteArray?,
        B: Int,
        xOfMode: Boolean,
    ): super(S, B, xOfMode, BIT_STRENGTH_128, DIGEST_LENGTH_BIT_128)

    private constructor(state: DigestState, digest: ParallelHash128): super(state, digest)

    protected override fun copy(state: DigestState): Digest = ParallelHash128(state, this)

    public companion object: SHAKEXofFactory<ParallelHash128>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [ParallelHash128]
         *
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * @param [B] The block size for the inner hash function in bytes
         * @throws [IllegalArgumentException] If [B] is less than 1
         * */
        @JvmStatic
        @Throws(IllegalArgumentException::class)
        public fun xOf(S: ByteArray?, B: Int): Xof<ParallelHash128> = SHAKEXof(ParallelHash128(S, B, xOfMode = true))
    }
}
