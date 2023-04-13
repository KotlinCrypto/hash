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
 * TupleHash256 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#5%20TupleHash
 *
 * @see [xOf]
 * */
public class TupleHash256: TupleDigest {

    /**
     * Creates a new [TupleHash256] [Digest] instance with a fixed output
     * [digestLength].
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * */
    public constructor(S: ByteArray?): this(S, xOfMode = false)

    private constructor(
        S: ByteArray?,
        xOfMode: Boolean,
    ): super(S, xOfMode, BIT_STRENGTH_256, DIGEST_LENGTH_BIT_256)

    private constructor(state: DigestState, digest: TupleHash256): super(state, digest)

    protected override fun copy(state: DigestState): Digest = TupleHash256(state, this)

    public companion object: SHAKEXofFactory<TupleHash256>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [TupleHash256]
         *
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * */
        @JvmStatic
        @JvmOverloads
        public fun xOf(S: ByteArray? = null): Xof<TupleHash256> = SHAKEXof(TupleHash256(S, xOfMode = true))
    }
}
