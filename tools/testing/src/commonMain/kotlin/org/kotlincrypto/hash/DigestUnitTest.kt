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
import org.kotlincrypto.core.digest.Digest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class DigestUnitTest: HashUnitTest() {
    abstract val digest: Digest
    abstract val expectedMultiBlockHash: String

    open fun givenDigest_whenReset_thenDigestDigestReturnsExpected() {
        updateSmall(digest)
        digest.reset()
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedResetHash, actual)
    }

    open fun givenDigest_whenMultiBlockDigest_thenDigestDigestReturnsExpected() {
        val sizes = (digest.blockSize() - 10)..(digest.blockSize() + 10)

        val outputs = mutableListOf<ByteArray>()
        for (size in sizes) {
            val digested = digest.digest(TestData.BYTES_MEDIUM.copyOf(size))
            outputs.add(digested)
        }

        for (output in outputs) {
            digest.update(output)
        }

        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedMultiBlockHash, actual)
    }

    open fun givenDigest_whenUpdatedSmall_thenDigestDigestReturnsExpected() {
        updateSmall(digest)
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedUpdateSmallHash, actual)
    }

    open fun givenDigest_whenUpdatedMedium_thenDigestDigestReturnsExpected() {
        updateMedium(digest)
        val actual = digest.digest().encodeToString(TestData.base16)
        assertEquals(expectedUpdateMediumHash, actual)
    }

    open fun givenDigest_whenCopied_thenIsDifferentInstance() {
        val copy = digest.copy()
        updateSmall(digest)
        assertNotEquals(copy, digest)
        assertEquals(expectedResetHash, copy.digest().encodeToString(TestData.base16))

        updateSmall(copy)
        assertEquals(expectedUpdateSmallHash, digest.digest().encodeToString(TestData.base16))
        assertEquals(expectedUpdateSmallHash, copy.digest().encodeToString(TestData.base16))
    }

    open fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        val out = digest.digest()
        assertEquals(digest.digestLength(), out.size)
    }
}
