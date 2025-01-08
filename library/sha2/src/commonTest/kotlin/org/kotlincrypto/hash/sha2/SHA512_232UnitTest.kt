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
package org.kotlincrypto.hash.sha2

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

/**
 * To ensure that when [Digest.digestLength] has an odd remainder,
 * [Digest.digestProtected] implementation functions as expected.
 * */
@Suppress("ClassName")
open class SHA512_232UnitTest: DigestUnitTest() {
    override val digest: Digest = SHA512t(224 + 8)
    final override val expectedResetHash: String = "7d29897f80908a38b7a89ca840d0e20bddb902fa475d2fed38a855fedc"
    final override val expectedMultiBlockHash: String = "1197503f9305085eea0e48a95aec0462eedb2f8f7357593422a0a80c14"
    final override val expectedUpdateSmallHash: String = "e725d5c36d640a65d06ad7dfb17593fe583ff2222beb5ba12571021789"
    final override val expectedUpdateMediumHash: String = "fbc900df3a3e3765a8a407307d8018be756108859f0fb6985e113b05f9"

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
