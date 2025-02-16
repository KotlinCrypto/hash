/*
 * Copyright (c) 2023 KotlinCrypto
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

@Suppress("ClassName")
open class CSHAKE256_N_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(N, null)
    final override val expectedResetHash: String = "bee8b14df0e3c77030458609ed34cea99206ced74a5fe6bf9d3e72852ec7ca5647cef12bcb27dd9cd3fcd604fd7f1daff8ab6091d48e9f5c101d3e99dfc57e17"
    final override val expectedMultiBlockHash: String = "b6a2c5010e7f1f21983bc8d3a76c73de56be42dad1372fd5d1f38eef05f6c687a400456f2e6090abe08130864fa9f087af6b8d72a3950af748e2426001dfa949"
    final override val expectedUpdateSmallHash: String = "44c2e60abb32ba2fe774bc634bf9dc5fafc86382b90f3181d52a412a6d07ec6da9eddf23b27d232cba7832e8b81c6be65590a3b3c332b41c350629815acc2ee9"
    final override val expectedUpdateMediumHash: String = "9d1ad68945f0b9808308cb35bb043e231dfda40b0ca8f3214d43c34b007bc1777ee4517f178db034a60743d317c6316880e608f3362d586184f3babef64c0eee"
}
