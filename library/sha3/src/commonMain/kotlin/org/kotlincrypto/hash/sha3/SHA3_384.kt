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

/**
 * SHA3-384 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 * */
public class SHA3_384: KeccakDigest {

    public constructor(): super(
        algorithm = "${SHA3}-384",
        blockSize = 104,
        digestLength = 48,
        dsByte = PAD_SHA3,
    )

    private constructor(other: SHA3_384): super(other)

    public override fun copy(): SHA3_384 = SHA3_384(this)
}
