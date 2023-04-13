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
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * TupleHash128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#5%20TupleHash
 *
 * @see [xOf]
 * */
public class TupleHash128: TupleDigest {

    /**
     * Creates a new [TupleHash128] [Digest] instance with a default output
     * length of 32 bytes.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * */
    public constructor(S: ByteArray?): this(S, DIGEST_LENGTH_BIT_128)

    /**
     * Creates a new [TupleHash128] [Digest] instance with a non-default output
     * length.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [outputLength] The number of bytes returned when [digest] is invoked
     * @throws [IllegalArgumentException] If [outputLength] is negative
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(
        S: ByteArray?,
        outputLength: Int,
    ): this(S, outputLength, xOfMode = false)

    private constructor(
        S: ByteArray?,
        outputLength: Int,
        xOfMode: Boolean,
    ): super(S, xOfMode, BIT_STRENGTH_128, outputLength)

    private constructor(state: DigestState, digest: TupleHash128): super(state, digest)

    protected override fun copy(state: DigestState): Digest = TupleHash128(state, this)

    public companion object: SHAKEXofFactory<TupleHash128>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [TupleHash128]
         *
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * */
        @JvmStatic
        @JvmOverloads
        public fun xOf(S: ByteArray? = null): Xof<TupleHash128> = SHAKEXof(TupleHash128(S, 0, xOfMode = true))
    }
}
