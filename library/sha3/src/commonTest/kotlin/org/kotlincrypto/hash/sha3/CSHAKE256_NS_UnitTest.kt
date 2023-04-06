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
package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.Digest
import kotlin.test.Test

@Suppress("ClassName")
open class CSHAKE256_NS_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(N, S)
    final override val expectedResetHash: String = "827baa5be4bacc6136e063c7d520532d1627a3af6868daf59e49a7c0a28749086087b4cb2db1c775829d97e897bd77292c7fac07f76035a033df8ed992ee4679"
    final override val expectedUpdateSmallHash: String = "c3292bf661657b567ef39c356032deab239b8c7ab493f536ff8c2e6f109a19be1e3632cca9a796049296f1401edd276213ca736765ef17310b655b15bbcf741d"
    final override val expectedUpdateMediumHash: String = "65e926d03ca2a10d8c65d16fd61f6bc19563e41bd47905e38a33e435cfc45ca24f35d0f5d3ba921d4da99f099dd6215d3b9133691bf8814c76d4e989f5ec49b8"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
