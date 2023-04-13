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
 * SHAKE128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * @see [xOf]
 * */
public class SHAKE128: SHAKEDigest {

    /**
     * Primary constructor for creating a new [SHAKE128] [Digest]
     * instance with a fixed output [digestLength].
     * */
    public constructor(): this(xOfMode = false)

    private constructor(
        xOfMode: Boolean
    ): super(null, null, xOfMode, SHAKE + BIT_STRENGTH_128, BLOCK_SIZE_BIT_128, DIGEST_LENGTH_BIT_128)

    private constructor(state: DigestState, digest: SHAKE128): super(state, digest)

    protected override fun copy(state: DigestState): Digest = SHAKE128(state, this)

    public companion object: SHAKEXofFactory<SHAKE128>() {

        /**
         * Produces a new [Xof] (Extendable-Output Function) for [SHAKE128]
         * */
        @JvmStatic
        public fun xOf(): Xof<SHAKE128> = SHAKEXof(SHAKE128(xOfMode = true))
    }
}
