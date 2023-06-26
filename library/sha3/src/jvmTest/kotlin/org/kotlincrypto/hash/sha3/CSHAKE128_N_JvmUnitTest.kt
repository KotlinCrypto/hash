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

import org.bouncycastle.crypto.digests.CSHAKEDigest
import org.junit.Test
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.TestBCDigest

class CSHAKE128_N_JvmUnitTest: CSHAKE128_N_UnitTest() {
    override val digest: Digest = TestBCDigest(CSHAKEDigest(128, N, null)) {
        throw AssertionError("Unable to copy CSHAKE digest")
    }

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
//        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
