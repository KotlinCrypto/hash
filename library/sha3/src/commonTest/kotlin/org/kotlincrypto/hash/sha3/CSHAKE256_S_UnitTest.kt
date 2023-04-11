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
open class CSHAKE256_S_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(null, S)
    final override val expectedResetHash: String = "3d79db7f3aaef4585ef784a9765ded61b069986184806de469e73fd3aaa854aaabd507ed16f87c0cb54e4f3cfbd9da9241476220f47a04eb4da29f514df65627"
    final override val expectedMultiBlockHash: String = "7e15ee688a1d7f43a2ad239d6045bcb07ab3c8a7c4ca4b6a9b8d2cc98ed7864d8213c98fb1dfb6a67a375d5492d789b1b9fa0e78ba297999467161c9030fa70a"
    final override val expectedUpdateSmallHash: String = "bf7058871040867ebe9d2de8714273c4068d85704d959160914c24c4061654ff5f39759cc4af9480bf75b4d7e9e68031159aec1b1ebee2ef0ffc83c7970a9b0a"
    final override val expectedUpdateMediumHash: String = "2430b7216231190dc13970c1fb1d8b4d64c6f2e02e6b0392d6a491bebba8668b6d0de4500e949c20d6986f069e52a1cd04a694a00021fa11e42e92d493a16f78"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
