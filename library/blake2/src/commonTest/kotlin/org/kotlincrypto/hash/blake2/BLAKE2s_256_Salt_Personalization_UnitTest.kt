/*
 * Copyright (c) 2025 KotlinCrypto
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
package org.kotlincrypto.hash.blake2

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

@Suppress("ClassName")
@OptIn(InternalKotlinCryptoApi::class)
open class BLAKE2s_256_Salt_Personalization_UnitTest: DigestUnitTest() {
    protected val salt = ByteArray(8) { i -> i.toByte() }
    protected val personalization = ByteArray(8) { i -> (i + 10).toByte() }

    override val digest: Digest = BLAKE2s(bitStrength = 256, keyLength = 0, salt = salt, personalization = personalization)
    final override val expectedResetHash: String = "8163e58dce27d734a7a35932c02ad3f8b29b4a92b0f4e64919647e040a9c9739"
    final override val expectedMultiBlockHash: String = "28789ef836a953713f5a8a28a5e84ffa88f072fade6f25e028fbe22305537b8c"
    final override val expectedUpdateSmallHash: String = "101b5bc3bf4cc5226abbe26d2521607798f1b93e9f09812db6bd0fbbcf20e8a0"
    final override val expectedUpdateMediumHash: String = "be1697b30fe33e56a056bd87b7fc028cdee12977961e43376c4b9c951ddc8c32"

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
