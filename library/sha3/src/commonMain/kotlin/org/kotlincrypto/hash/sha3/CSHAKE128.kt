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
import org.kotlincrypto.core.internal.DigestState

/**
 * CSHAKE128 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#3%20cSHAKE
 * */
public class CSHAKE128: SHAKEDigest {

    /**
     * Primary constructor for creating a new [CSHAKE128] instance
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
    ): super(N, S, "${CSHAKE}128", 168, 32)

    private constructor(state: DigestState, digest: CSHAKE128): super(state, digest)

    protected override fun copy(state: DigestState): Digest = CSHAKE128(state, this)
}
