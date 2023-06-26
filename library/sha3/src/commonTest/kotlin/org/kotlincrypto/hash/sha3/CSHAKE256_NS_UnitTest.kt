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

import org.kotlincrypto.core.digest.Digest
import kotlin.test.Test

@Suppress("ClassName")
open class CSHAKE256_NS_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(N, S)
    final override val expectedResetHash: String = "2a46f1df815e8cd2f645df371d97989fa31ff99c80731c1f6ec2d3e48b183193524742eb87c5007edd1549feaaddbff2623cd16f3b5f8506e438d6aad8476107"
    final override val expectedMultiBlockHash: String = "5077045db9148c7ee4f665c50e3aeb49d032e4058dbb5a6f0791c03cde3c44c108d30403ca2626294163ff81edeb7f674ed709505c4b25c345b84042a82b40e0"
    final override val expectedUpdateSmallHash: String = "69269d2e388a5b116b70ed4d8ce8eb5f964e8236d25e7e29bbf251f990fc99036ab170a1ba47eeca03581741b420167bc806634af4387753eaac0a77e6fa06e4"
    final override val expectedUpdateMediumHash: String = "2d555623df2198f3fd36f5f5a79190eb06b6ce38bfe10cc9f1ff86542a2511babe79fd1b0c0d0b6d9d9941a131a69a4ea90483e7dc592848e03a0703f112d717"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
