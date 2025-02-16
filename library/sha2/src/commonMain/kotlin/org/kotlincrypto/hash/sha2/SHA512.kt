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
package org.kotlincrypto.hash.sha2

/**
 * SHA-512 implementation
 * */
public class SHA512: Bit64Digest {

    public constructor(): super(bitStrength = 512, t = null, h = H)

    private constructor(other: SHA512): super(other)

    public override fun copy(): SHA512 = SHA512(this)

    private companion object {
        private val H = longArrayOf(
            7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L,
            5840696475078001361L, -7276294671716946913L, 2270897969802886507L,  6620516959819538809L,
        )
    }
}
