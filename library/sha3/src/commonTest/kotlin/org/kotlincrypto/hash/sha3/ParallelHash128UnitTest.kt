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

import kotlin.test.Test
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest

open class ParallelHash128UnitTest: DigestUnitTest() {
    protected val B = 100
    override val digest: Digest = ParallelHash128(null, B)
    final override val expectedResetHash: String = "4860c15ced13a8e56e95a6c7ea14e55be8ad8db61373f1b2372687dfd3c9c38f"
    final override val expectedMultiBlockHash: String = "8608090cc85438b6d29346d608815d87c2bf77073f001eab06bb6f8ecce16983"
    final override val expectedUpdateSmallHash: String = "f931e465e69bb9895cf655eb5227a55f8775eb0ca3b04edc7c9edfb577552f56"
    final override val expectedUpdateMediumHash: String = "57d11d6d370bd6ca17ad773f590e82d06cb2f4feb36dda629263ce723ac5d97e"

    @Test
    final override fun givenDigest_whenReset_thenDigestDigestReturnsExpected() {
        super.givenDigest_whenReset_thenDigestDigestReturnsExpected()
    }

    @Test
    final override fun givenDigest_whenMultiBlockDigest_thenDigestDigestReturnsExpected() {
        super.givenDigest_whenMultiBlockDigest_thenDigestDigestReturnsExpected()
    }

    @Test
    final override fun givenDigest_whenUpdatedSmall_thenDigestDigestReturnsExpected() {
        super.givenDigest_whenUpdatedSmall_thenDigestDigestReturnsExpected()
    }

    @Test
    final override fun givenDigest_whenUpdatedMedium_thenDigestDigestReturnsExpected() {
        super.givenDigest_whenUpdatedMedium_thenDigestDigestReturnsExpected()
    }

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

    @Test
    final override fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        super.givenDigest_whenDigested_thenLengthMatchesOutput()
    }

}
