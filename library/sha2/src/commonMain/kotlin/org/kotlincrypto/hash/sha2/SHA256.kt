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

/**
 * SHA-256 implementation
 * */
public class SHA256: Bit32Digest {

    public constructor(): super(bitStrength = 256, h = H)

    private constructor(other: SHA256): super(other)

    public override fun copy(): SHA256 = SHA256(this)

    private companion object {
        private val H = intArrayOf(
            1779033703, -1150833019, 1013904242, -1521486534,
            1359893119, -1694144372,  528734635,  1541459225,
        )
    }
}
