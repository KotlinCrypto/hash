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
open class BLAKE2b_384UnitTest: DigestUnitTest() {
    override val digest: Digest = BLAKE2b_384()
    final override val expectedResetHash: String = "b32811423377f52d7862286ee1a72ee540524380fda1724a6f25d7978c6fd3244a6caf0498812673c5e05ef583825100"
    final override val expectedMultiBlockHash: String = "81cfa6c737c20ca963be32c4d7fcb0b0efb7adec906c51bcec1d36f7bc3cc902b9d90746e3fc39850f537e8c5fa13f1d"
    final override val expectedUpdateSmallHash: String = "19a3e1249ec79fa7718b0ae5c37f3bffd12ab744efb0ac65cfd7bac3ca180392761aa4b38bb124eb77662488e655e459"
    final override val expectedUpdateMediumHash: String = "ce2c194d88806456d4b982ad6de78208be6a21b1f1da26e394ba75dcdd11e277b87314b0dd9f722df34e97244e5d0d50"

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
