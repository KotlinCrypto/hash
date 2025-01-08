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

    public constructor(): super(
        d = 512,
        t = null,
        h0 =  7640891576956012808L,
        h1 = -4942790177534073029L,
        h2 =  4354685564936845355L,
        h3 = -6534734903238641935L,
        h4 =  5840696475078001361L,
        h5 = -7276294671716946913L,
        h6 =  2270897969802886507L,
        h7 =  6620516959819538809L,
    )

    private constructor(other: SHA512): super(other)

    public override fun copy(): SHA512 = SHA512(other = this)
}
