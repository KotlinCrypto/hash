/*
 * Copyright (c) 2025 Matthew Nelson
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
package org.kotlincrypto.hash.blake2

import org.bouncycastle.crypto.digests.Blake2bDigest
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.TestBCDigest

@Suppress("ClassName")
class BLAKE2b_512_Salt_Personalization_JvmUnitTest: BLAKE2b_512_Salt_Personalization_UnitTest() {
    override val digest: Digest = TestBCDigest(
        digest = Blake2bDigest(
            /* key =             */ null,
            /* digestLength =    */ 512 / Byte.SIZE_BITS,
            /* salt =            */ salt,
            /* personalization = */ personalization,
        ),
        copy = { Blake2bDigest(this) },
    )
}
