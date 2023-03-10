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

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.internal.DigestState

/**
 * SHA-384 implementation
 * */
public class SHA384: Bit64Digest {

    public constructor(): super(
        d = 384,
        t = null,
        h0 = -3766243637369397544L,
        h1 = 7105036623409894663L,
        h2 = -7973340178411365097L,
        h3 = 1526699215303891257L,
        h4 = 7436329637833083697L,
        h5 = -8163818279084223215L,
        h6 = -2662702644619276377L,
        h7 = 5167115440072839076L,
    )

    private constructor(state: DigestState, sha384: SHA384): super(state, sha384)

    protected override fun copy(state: DigestState): Digest = SHA384(state, this)

    protected override fun out(a: Long, b: Long, c: Long, d: Long, e: Long, f: Long, g: Long, h: Long): ByteArray {
        return byteArrayOf(
            (a shr 56).toByte(),
            (a shr 48).toByte(),
            (a shr 40).toByte(),
            (a shr 32).toByte(),
            (a shr 24).toByte(),
            (a shr 16).toByte(),
            (a shr  8).toByte(),
            (a       ).toByte(),
            (b shr 56).toByte(),
            (b shr 48).toByte(),
            (b shr 40).toByte(),
            (b shr 32).toByte(),
            (b shr 24).toByte(),
            (b shr 16).toByte(),
            (b shr  8).toByte(),
            (b       ).toByte(),
            (c shr 56).toByte(),
            (c shr 48).toByte(),
            (c shr 40).toByte(),
            (c shr 32).toByte(),
            (c shr 24).toByte(),
            (c shr 16).toByte(),
            (c shr  8).toByte(),
            (c       ).toByte(),
            (d shr 56).toByte(),
            (d shr 48).toByte(),
            (d shr 40).toByte(),
            (d shr 32).toByte(),
            (d shr 24).toByte(),
            (d shr 16).toByte(),
            (d shr  8).toByte(),
            (d       ).toByte(),
            (e shr 56).toByte(),
            (e shr 48).toByte(),
            (e shr 40).toByte(),
            (e shr 32).toByte(),
            (e shr 24).toByte(),
            (e shr 16).toByte(),
            (e shr  8).toByte(),
            (e       ).toByte(),
            (f shr 56).toByte(),
            (f shr 48).toByte(),
            (f shr 40).toByte(),
            (f shr 32).toByte(),
            (f shr 24).toByte(),
            (f shr 16).toByte(),
            (f shr  8).toByte(),
            (f       ).toByte()
        )
    }
}
