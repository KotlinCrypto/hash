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
@file:Suppress("ClassName")

package org.kotlincrypto.hash.sha3

/**
 * SHA3-224 implementation
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 * */
public class SHA3_224: KeccakDigest {

    public constructor(): super(
        algorithm = "${SHA3}-224",
        blockSize = 144,
        digestLength = 28,
        dsByte = PAD_SHA3,
    )

    private constructor(other: SHA3_224): super(other)

    public override fun copy(): SHA3_224 = SHA3_224(this)
}
