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
@file:Suppress("ClassName")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState

/**
 * Keccak-512 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 * */
public class Keccak512: KeccakDigest {

    public constructor(): super("${KECCAK}-512", 72, 64, PAD_KECCAK)

    private constructor(state: DigestState, digest: Keccak512): super(state, digest)

    protected override fun copy(state: DigestState): Digest = Keccak512(state, this)
}
