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
open class CSHAKE256_N_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(N, null)
    final override val expectedResetHash: String = "bee8b14df0e3c77030458609ed34cea99206ced74a5fe6bf9d3e72852ec7ca5647cef12bcb27dd9cd3fcd604fd7f1daff8ab6091d48e9f5c101d3e99dfc57e17"
    final override val expectedMultiBlockHash: String = "717a67d51fc606bc0caf03568a2be81d22b9179f4aafee1a9dbc14dc8d49910283eb64f072162fe6ce1a043ff5013a99a3334cb77bf078690950692af9bd94dc"
    final override val expectedUpdateSmallHash: String = "44c2e60abb32ba2fe774bc634bf9dc5fafc86382b90f3181d52a412a6d07ec6da9eddf23b27d232cba7832e8b81c6be65590a3b3c332b41c350629815acc2ee9"
    final override val expectedUpdateMediumHash: String = "9d1ad68945f0b9808308cb35bb043e231dfda40b0ca8f3214d43c34b007bc1777ee4517f178db034a60743d317c6316880e608f3362d586184f3babef64c0eee"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
