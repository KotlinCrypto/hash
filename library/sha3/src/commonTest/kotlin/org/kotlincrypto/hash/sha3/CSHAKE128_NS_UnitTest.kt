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

open class CSHAKE128_NS_UnitTest: CSHAKE128UnitTest() {
    override val digest: Digest = CSHAKE128(N, S)
    final override val expectedResetHash: String = "133db34b6ede033a27bb910ed72c43fb5016d40e82fc817cd333d944cfdf6488"
    final override val expectedUpdateSmallHash: String = "764633426e5b58bb47cd5a75aa4bb6bc25fc477895fe56d21a58ea8a705376e8"
    final override val expectedUpdateMediumHash: String = "cd35947002326524745a3f56b1c080eaa6854669aaabfea8eca65fc84fc0c015"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
