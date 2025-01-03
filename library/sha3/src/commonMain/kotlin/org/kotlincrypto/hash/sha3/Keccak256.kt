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

/**
 * Keccak-256 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 * */
public class Keccak256: KeccakDigest {

    public constructor(): super(
        algorithm = "${KECCAK}-256",
        blockSize = 136,
        digestLength = 32,
        dsByte = PAD_KECCAK,
    )

    private constructor(other: Keccak256): super(other)

    public override fun copy(): Keccak256 = Keccak256(other = this)
}
