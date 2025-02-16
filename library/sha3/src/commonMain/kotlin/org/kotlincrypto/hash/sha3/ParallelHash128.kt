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
 * ParallelHash128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#6%20ParallelHash
 *
 * @see [xOf]
 * */
public class ParallelHash128: ParallelDigest {

    /**
     * Creates a new [ParallelHash128] [Digest] instance with a default output
     * length of 32 bytes.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [B] The block size for the inner hash function in bytes
     * @throws [InvalidParameterException] If [B] is less than 1
     * */
    public constructor(
        S: ByteArray?,
        B: Int,
    ): this(S, B, DIGEST_LENGTH_BIT_128)

    /**
     * Creates a new [ParallelHash128] [Digest] instance with a non-default output
     * length.
     *
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [B] The block size for the inner hash function in bytes
     * @param [outputLength] The number of bytes returned when [digest] is invoked
     * @throws [InvalidParameterException] If [B] is less than 1, or [outputLength] is negative
     * */
    public constructor(
        S: ByteArray?,
        B: Int,
        outputLength: Int,
    ): this(S, B, outputLength, xOfMode = false)

    @Throws(InvalidParameterException::class)
    private constructor(
        S: ByteArray?,
        B: Int,
        outputLength: Int,
        xOfMode: Boolean,
    ): super(
        S = S,
        B = B,
        xOfMode = xOfMode,
        bitStrength = BIT_STRENGTH_128,
        digestLength = outputLength,
    )

    private constructor(other: ParallelHash128): super(other)

    public override fun copy(): ParallelHash128 = ParallelHash128(this)

    public companion object: SHAKEXofFactory<ParallelHash128>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [ParallelHash128]
         *
         * @param [B] The block size for the inner hash function in bytes
         * @throws [InvalidParameterException] If [B] is less than 1
         * */
        @JvmStatic
        public inline fun xOf(B: Int): Xof<ParallelHash128> = xOf(S = null, B = B)

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [ParallelHash128]
         *
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * @param [B] The block size for the inner hash function in bytes
         * @throws [InvalidParameterException] If [B] is less than 1
         * */
        @JvmStatic
        public fun xOf(S: ByteArray?, B: Int): Xof<ParallelHash128> {
            return SHAKEXof(ParallelHash128(S = S, B = B, outputLength = 0, xOfMode = true))
        }
    }
}
