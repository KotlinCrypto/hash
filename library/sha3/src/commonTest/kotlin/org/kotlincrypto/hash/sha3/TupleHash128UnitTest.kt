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

import kotlin.test.Test
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest

open class TupleHash128UnitTest: DigestUnitTest() {
    override val digest: Digest = TupleHash128(null)
    final override val expectedResetHash: String = "786aa3d4fcaadf0aa723a4818a1a72de2330d613e5de7ae4eb6cb4cdd26adba2"
    final override val expectedMultiBlockHash: String = "26f53178710b7fdb63c91a22b066669cb0907017461a162c90e18bcad003f632"
    final override val expectedUpdateSmallHash: String = "a3e4e5f23ff4cb7587a83fe386f37cf3e9f80b0ba5d74de9e72815079b13d660"
    final override val expectedUpdateMediumHash: String = "98be957009fca08674e795afd8660a3d46a1a9899f1539ef0b9d5e1acc73c0bf"

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
