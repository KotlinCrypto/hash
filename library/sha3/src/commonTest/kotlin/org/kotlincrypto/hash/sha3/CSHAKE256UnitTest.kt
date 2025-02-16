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
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

open class CSHAKE256UnitTest: DigestUnitTest() {
    // Will ensure that an "N" only test's initBlock
    // is perfectly sized to the blockSize in order
    // to check if additional padding is NOT applied.
    //
    // blockSize - (encoding sizes)
    protected val N = ByteArray(136 - 2 - 3 - 2) { 5 }
    protected val S = "Test CSHAKE".encodeToByteArray()
    override val digest: Digest = CSHAKE256(null, null)
    override val expectedResetHash: String = "46b9dd2b0ba88d13233b3feb743eeb243fcd52ea62b81b82b50c27646ed5762fd75dc4ddd8c0f200cb05019d67b592f6fc821c49479ab48640292eacb3b7c4be"
    override val expectedMultiBlockHash: String = "e76032532bf3dc28f9f557a4e084c9e84364611cdd814d7f0a45d0f46e8d3bb95566454c997cdba055ba288f87aab21d63e7a90ff70a8461b62cc3b2726e186b"
    override val expectedUpdateSmallHash: String = "030f4a342728ddc61799a29afb8f904a3276e04ead3c3fb0f3ef2fb2ec9c85a3cd87c6549a17c727c3d544def386d005751a06cc2a96c489b48ce270762e7794"
    override val expectedUpdateMediumHash: String = "42dacceebb007046252e70a319ab2e162cd0ffb3b3e6fcf0e865fbf3763fa7325d4add48be7917c2bfb09e6fa0a197cbece5a1ee5b7e1a554b514197e84b7f46"

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
