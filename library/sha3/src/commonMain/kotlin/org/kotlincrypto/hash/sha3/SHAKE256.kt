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
import org.kotlincrypto.core.digest.internal.DigestState
import org.kotlincrypto.core.xof.Xof
import kotlin.jvm.JvmStatic

/**
 * SHAKE256 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * @see [xOf]
 * */
public class SHAKE256: SHAKEDigest {

    /**
     * Creates a new [SHAKE256] [Digest] instance with a default output
     * length of 64 bytes.
     * */
    public constructor(): this(DIGEST_LENGTH_BIT_256)

    /**
     * Creates a new [SHAKE256] [Digest] instance with a non-default output
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
        xOfMode: Boolean
    ): super(null, null, xOfMode, SHAKE + BIT_STRENGTH_256, BLOCK_SIZE_BIT_256, outputLength)

    private constructor(state: DigestState, digest: SHAKE256): super(state, digest)

    protected override fun copy(state: DigestState): Digest = SHAKE256(state, this)

    public companion object: SHAKEXofFactory<SHAKE256>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [SHAKE256]
         * */
        @JvmStatic
        public fun xOf(): Xof<SHAKE256> = SHAKEXof(SHAKE256(0, xOfMode = true))
    }
}
