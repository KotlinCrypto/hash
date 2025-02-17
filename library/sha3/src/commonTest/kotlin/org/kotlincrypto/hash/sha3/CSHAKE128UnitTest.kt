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
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

open class CSHAKE128UnitTest: DigestUnitTest() {
    // Will ensure that an "N" only test's initBlock
    // is perfectly sized to the blockSize, in order
    // to check if additional padding is NOT applied.
    //
    // blockSize - (encoding sizes)
    protected val N = ByteArray(168 - 2 - 3 - 2) { 5 }
    protected val S = "Test CSHAKE".encodeToByteArray()
    override val digest: Digest = CSHAKE128(null, null)
    override val expectedResetHash: String = "7f9c2ba4e88f827d616045507605853ed73b8093f6efbc88eb1a6eacfa66ef26"
    override val expectedMultiBlockHash: String = "fb77bf6b8c431a775e3780d3dda611c948c5d3d6306143ca2089c2749de7c2e2"
    override val expectedUpdateSmallHash: String = "0b33a664b281d9a38638832fd314444c2fa5865072e61505d0776c5cdd322ca3"
    override val expectedUpdateMediumHash: String = "9bf64f28a64f294769abd18b8903820453c9f31fe1bf620f95dede8f0b5d9f56"

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
