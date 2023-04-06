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

import org.kotlincrypto.core.Digest
import kotlin.test.Test

open class CSHAKE128_N_UnitTest: CSHAKE128UnitTest() {
    override val digest: Digest = CSHAKE128(N, null)
    final override val expectedResetHash: String = "63e5b592ba16aef96e22c02f995e04421df9ec14be1b5e82d4da9af10ff2a8c0"
    final override val expectedUpdateSmallHash: String = "d72243337762656b8d1d48f0ba502468590745e825f6fcfb5c031ff458c2a1e6"
    final override val expectedUpdateMediumHash: String = "8110a0f4e6886075dc1d11372a3760868ca6b719cace7a95b75a69583021637f"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
