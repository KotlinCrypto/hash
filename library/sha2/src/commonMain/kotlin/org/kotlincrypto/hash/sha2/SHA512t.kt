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
package org.kotlincrypto.hash.sha2

import org.kotlincrypto.error.InvalidParameterException

/**
 * SHA-512/t implementation
 * */
public class SHA512t: Bit64Digest {

    /**
     * Primary constructor for creating a new [SHA512t] instance
     *
     * @throws [InvalidParameterException] when:
     *  - [t] is less than 0
     *  - [t] is greater than or equal to 512
     *  - [t] is not a factor of 8
     *  - [t] is 384
     * */
    public constructor(t: Int): super(
        bitStrength = 512,
        t = t,
        h = longArrayOf(
             7640891576956012808L xor -6510615555426900571L,
            -4942790177534073029L xor -6510615555426900571L,
             4354685564936845355L xor -6510615555426900571L,
            -6534734903238641935L xor -6510615555426900571L,
             5840696475078001361L xor -6510615555426900571L,
            -7276294671716946913L xor -6510615555426900571L,
             2270897969802886507L xor -6510615555426900571L,
             6620516959819538809L xor -6510615555426900571L,
        )
    )

    private constructor(other: SHA512t): super(other)

    public override fun copy(): SHA512t = SHA512t(this)
}
