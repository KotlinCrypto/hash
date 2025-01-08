/*
 * Copyright (c) 2025 Matthew Nelson
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

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

@Suppress("ClassName")
open class BLAKE2b_512UnitTest: DigestUnitTest() {
    override val digest: Digest = BLAKE2b_512()
    final override val expectedResetHash: String = "786a02f742015903c6c6fd852552d272912f4740e15847618a86e217f71f5419d25e1031afee585313896444934eb04b903a685b1448b755d56f701afe9be2ce"
    final override val expectedMultiBlockHash: String = "b45a6caeaf044b1922c5043dc7e727571482510505c7e6a1420d2c3546ee49e934efd5ef8a7a66046d19879d050060da919867f447db86d4f3e79b8f34d8db8e"
    final override val expectedUpdateSmallHash: String = "514dd438eca0771525229fe76f90f59917b4f071d614345c99b1ae58e04c521464e94b7df8a6638b4c49c0611e91dfea2efd6b2bad4d4eac988bd0cde3ee3c44"
    final override val expectedUpdateMediumHash: String = "98270d73f082ecb978a773b0e2e2c8e5b1f77c70b374fd932d64ce77d6f0085a9b53c6c6d59ddd55a56667ec5a36f9eaa3e34d09727e0120b4806a299c678fa4"

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
