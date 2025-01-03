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
@file:Suppress("LocalVariableName", "SpellCheckingInspection")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.xof.Xof
import org.kotlincrypto.core.digest.Digest
import kotlin.jvm.JvmStatic

/**
 * CSHAKE256 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#3%20cSHAKE
 *
 * @see [xOf]
 * */
public class CSHAKE256: SHAKEDigest {

    /**
     * Creates a new [CSHAKE256] [Digest] instance with a default output length
     * of 64 bytes.
     *
     * @param [N] A function-name bit string, used by NIST to define functions
     *   based on CSHAKE. Usage should be avoided when not required.
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * */
    public constructor(
        N: ByteArray?,
        S: ByteArray?,
    ): this(N, S, DIGEST_LENGTH)

    /**
     * Creates a new [CSHAKE256] [Digest] instance with a non-default output length.
     *
     * @param [N] A function-name bit string, used by NIST to define functions
     *   based on CSHAKE. Usage should be avoided when not required.
     * @param [S] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [S] is set to an
     *   empty or null value. (e.g. "My Customization".encodeToByteArray())
     * @param [outputLength] The number of bytes returned when [digest] is invoked
     * @throws [IllegalArgumentException] If [outputLength] is negative
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(
        N: ByteArray?,
        S: ByteArray?,
        outputLength: Int,
    ): this(N, S, outputLength, xOfMode = false)

    private constructor(
        N: ByteArray?,
        S: ByteArray?,
        outputLength: Int,
        xOfMode: Boolean,
    ): super(
        N = N,
        S = S,
        xOfMode = xOfMode,
        algorithm = CSHAKE + BIT_STRENGTH_256,
        blockSize = BLOCK_SIZE,
        digestLength = outputLength,
    )

    private constructor(other: CSHAKE256): super(other)

    public override fun copy(): CSHAKE256 = CSHAKE256(other = this)

    public companion object: SHAKEXofFactory<CSHAKE256>() {

        /**
         * The block size for this algorithm
         * */
        public const val BLOCK_SIZE: Int = BLOCK_SIZE_BIT_256

        /**
         * The default number of bytes output when [digest] is invoked
         * */
        public const val DIGEST_LENGTH: Int = DIGEST_LENGTH_BIT_256

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [CSHAKE256]
         *
         * @param [N] A function-name bit string, used by NIST to define functions
         *   based on CSHAKE. Usage should be avoided when not required.
         * @param [S] A user selected customization bit string to define a variant
         *   of the function. When no customization is desired, [S] is set to an
         *   empty or null value. (e.g. "My Customization".encodeToByteArray())
         * */
        @JvmStatic
        public fun xOf(N: ByteArray?, S: ByteArray?): Xof<CSHAKE256> {
            return SHAKEXof(CSHAKE256(N, S, outputLength = 0, xOfMode = true))
        }
    }
}
