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
package org.kotlincrypto.hash

import io.matthewnelson.encoding.core.Encoder.Companion.encodeToString
import org.kotlincrypto.core.Digest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class DigestUnitTest {
    abstract val digest: Digest
    abstract val expectedResetHash: String
    abstract val expectedUpdateSmallHash: String
    abstract val expectedUpdateMediumHash: String

    open fun givenDigest_whenReset_thenDigestDigestReturnsExpected() {
        digest.update(TestData.BYTES_SMALL)
        digest.reset()
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedResetHash, actual)
    }

    open fun givenDigest_whenUpdatedSmall_thenDigestDigestReturnsExpected() {
        digest.update(TestData.BYTES_SMALL)
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedUpdateSmallHash, actual)
    }

    open fun givenDigest_whenUpdatedMedium_thenDigestDigestReturnsExpected() {
        digest.update(TestData.BYTES_MEDIUM[0])
        digest.update(TestData.BYTES_MEDIUM)
        digest.update(TestData.BYTES_MEDIUM, 100, 1_000)
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedUpdateMediumHash, actual)
    }

    open fun givenDigest_whenCopied_thenIsDifferentInstance() {
        val copy = digest.copy()
        digest.update(TestData.BYTES_SMALL)
        assertNotEquals(copy, digest)
        assertEquals(expectedResetHash, copy.digest().encodeToString(TestData.base16))
        assertEquals(expectedUpdateSmallHash, digest.digest().encodeToString(TestData.base16))
        assertEquals(expectedUpdateSmallHash, copy.digest(TestData.BYTES_SMALL).encodeToString(TestData.base16))
    }

    open fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        assertEquals(digest.digest().encodeToString(TestData.base16).length, expectedResetHash.length)
    }
}
