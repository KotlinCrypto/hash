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

@Suppress("ClassName")
open class SHA3_256UnitTest: DigestUnitTest() {
    override val digest: Digest = SHA3_256()
    final override val expectedResetHash: String = "a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a"
    final override val expectedMultiBlockHash: String = "28b4fcc975d3c40f259b34fe2f362b83358e0b7679e81f31444183b58a561005"
    final override val expectedUpdateSmallHash: String = "b41380a98cec55fee0785b76c6dbe5500a6f7b9d4ede7de182acb5d0dc1e7e59"
    final override val expectedUpdateMediumHash: String = "aecf3132dd50201f8ea6959ebed69cff09ae5cb2e1fed97bb7cd4ff8ae4e73eb"

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
    final override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

    @Test
    final override fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        super.givenDigest_whenDigested_thenLengthMatchesOutput()
    }

}
