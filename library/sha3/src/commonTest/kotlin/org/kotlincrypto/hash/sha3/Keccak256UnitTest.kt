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

open class Keccak256UnitTest: DigestUnitTest() {
    override val digest: Digest = Keccak256()
    final override val expectedResetHash: String = "c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470"
    final override val expectedMultiBlockHash: String = "326262bcbb724c678f35e66e01395b18cde0d308807379010617018f0b3e4496"
    final override val expectedUpdateSmallHash: String = "8da7db9b1b86176084b942444756fd50f17afd89a16f9df1de1d1a0a8903c583"
    final override val expectedUpdateMediumHash: String = "06c89b5c8b7f8e6ad6480b29b50180cbfc92c3c2c9b2954833289e695c9eec0b"

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
