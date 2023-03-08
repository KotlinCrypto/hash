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
@file:Suppress("UnnecessaryOptInAnnotation")

package org.kotlincrypto.hash

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.internal.DigestState

/**
 * SHA-256 implementation
 * */
public class Sha256: Sha2IntDigest {

    @OptIn(InternalKotlinCryptoApi::class)
    public constructor(): super(
        d = 256,
        h0 = 1779033703,
        h1 = -1150833019,
        h2 = 1013904242,
        h3 = -1521486534,
        h4 = 1359893119,
        h5 = -1694144372,
        h6 = 528734635,
        h7 = 1541459225,
    )

    @OptIn(InternalKotlinCryptoApi::class)
    private constructor(state: DigestState, sha256: Sha256): super(state, sha256)

    protected override fun copy(state: DigestState): Digest = Sha256(state, this)

    protected override fun out(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int, g: Int, h: Int): ByteArray {
        return byteArrayOf(
            (a shr 24).toByte(),
            (a shr 16).toByte(),
            (a shr  8).toByte(),
            (a       ).toByte(),
            (b shr 24).toByte(),
            (b shr 16).toByte(),
            (b shr  8).toByte(),
            (b       ).toByte(),
            (c shr 24).toByte(),
            (c shr 16).toByte(),
            (c shr  8).toByte(),
            (c       ).toByte(),
            (d shr 24).toByte(),
            (d shr 16).toByte(),
            (d shr  8).toByte(),
            (d       ).toByte(),
            (e shr 24).toByte(),
            (e shr 16).toByte(),
            (e shr  8).toByte(),
            (e       ).toByte(),
            (f shr 24).toByte(),
            (f shr 16).toByte(),
            (f shr  8).toByte(),
            (f       ).toByte(),
            (g shr 24).toByte(),
            (g shr 16).toByte(),
            (g shr  8).toByte(),
            (g       ).toByte(),
            (h shr 24).toByte(),
            (h shr 16).toByte(),
            (h shr  8).toByte(),
            (h       ).toByte()
        )
    }
}
