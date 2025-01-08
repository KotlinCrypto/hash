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
 * SHA-384 implementation
 * */
public class SHA384: Bit64Digest {

    public constructor(): super(
        d = 384,
        t = null,
        h0 = -3766243637369397544L,
        h1 =  7105036623409894663L,
        h2 = -7973340178411365097L,
        h3 =  1526699215303891257L,
        h4 =  7436329637833083697L,
        h5 = -8163818279084223215L,
        h6 = -2662702644619276377L,
        h7 =  5167115440072839076L,
    )

    private constructor(other: SHA384): super(other)

    public override fun copy(): SHA384 = SHA384(other = this)
}
