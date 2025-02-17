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
@file:Suppress("LocalVariableName", "KotlinRedundantDiagnosticSuppress", "NOTHING_TO_INLINE")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.xof.Xof
import org.kotlincrypto.error.InvalidParameterException
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
     * Creates a new [TupleHash256] [Digest] instance with a default output length of 64 bytes.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * */
    public constructor(S: ByteArray?): this(S, DIGEST_LENGTH_BIT_256)

    /**
     * Creates a new [TupleHash256] [Digest] instance with a non-default output length.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [outputLength] The number of bytes returned when [digest] is invoked
     *
     * @throws [InvalidParameterException] If [outputLength] is negative
     * */
    public constructor(
        S: ByteArray?,
        outputLength: Int,
    ): this(S, outputLength, xOfMode = false)

    @Throws(InvalidParameterException::class)
    private constructor(
        S: ByteArray?,
        outputLength: Int,
        xOfMode: Boolean,
    ): super(
        S = S,
        xOfMode = xOfMode,
        bitStrength = BIT_STRENGTH_256,
        digestLength = outputLength,
    )

    private constructor(other: TupleHash256): super(other)

    public override fun copy(): TupleHash256 = TupleHash256(this)

    public companion object: SHAKEXofFactory<TupleHash256>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [TupleHash256]
         * */
        @JvmStatic
        public inline fun xOf(): Xof<TupleHash256> = xOf(S = null)

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [TupleHash256]
         *
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * */
        @JvmStatic
        public fun xOf(S: ByteArray?): Xof<TupleHash256> {
            return SHAKEXof(TupleHash256(S = S, outputLength = 0, xOfMode = true))
        }
    }
}
