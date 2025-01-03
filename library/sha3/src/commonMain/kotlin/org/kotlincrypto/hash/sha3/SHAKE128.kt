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

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.xof.Xof
import kotlin.jvm.JvmStatic

/**
 * SHAKE128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * @see [xOf]
 * */
public class SHAKE128: SHAKEDigest {

    /**
     * Creates a new [SHAKE128] [Digest] instance with a default output
     * length of 32 bytes.
     * */
    public constructor(): this(DIGEST_LENGTH_BIT_128)

    /**
     * Creates a new [SHAKE128] [Digest] instance with a non-default output
     * length.
     *
     * @param [outputLength] The number of bytes returned when [digest] is invoked
     * @throws [IllegalArgumentException] If [outputLength] is negative
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(
        outputLength: Int,
    ): this(outputLength, xOfMode = false)

    private constructor(
        outputLength: Int,
        xOfMode: Boolean,
    ): super(
        N = null,
        S = null,
        xOfMode = xOfMode,
        algorithm = SHAKE + BIT_STRENGTH_128,
        blockSize = BLOCK_SIZE_BIT_128,
        digestLength = outputLength,
    )

    private constructor(other: SHAKE128): super(other)

    public override fun copy(): SHAKE128 = SHAKE128(other = this)

    public companion object: SHAKEXofFactory<SHAKE128>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [SHAKE128]
         * */
        @JvmStatic
        public fun xOf(): Xof<SHAKE128> {
            return SHAKEXof(SHAKE128(outputLength = 0, xOfMode = true))
        }
    }
}
