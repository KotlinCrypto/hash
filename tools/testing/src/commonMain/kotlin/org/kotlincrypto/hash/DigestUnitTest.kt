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
@file:Suppress("FunctionName")

package org.kotlincrypto.hash

import io.matthewnelson.encoding.core.Encoder.Companion.encodeToString
import org.kotlincrypto.core.digest.Digest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Test abstraction to verify that the Digest implementation is in
 * proper working order. All input utilized is deterministic in order
 * to ensure expected hash values never change.
 *
 * Expected hash values are obtained by an already working implementation
 * of the algorithm under test (e.g. Bouncy Castle provider). Those values
 * must then match when the KotlinCrypto implementation is put under test.
 *
 * To implement this abstraction for a new algorithm:
 *
 * See [org.kotlincrypto.hash.md.MD5UnitTest] located in `commonTest`. Note that
 * the test class is `open`. This allows it to be inherited from in the `jvmTest`
 * source set, for example the [org.kotlincrypto.hash.md.MD5JvmUnitTest] test.
 * This ensures that both implementations run under the same test parameters
 * output the same values.
 * */
abstract class DigestUnitTest: HashUnitTest() {
    abstract val digest: Digest
    abstract val expectedMultiBlockHash: String

    private fun ByteArray.hex(): String = encodeToString(TestData.base16)

    /**
     * Tests the reset functionality of the Digest implementation.
     * */
    open fun givenDigest_whenReset_thenDigestDigestReturnsExpected() {
        assertExpectedHashes
        updateMedium(digest)
        digest.reset()
        assertEquals(expectedResetHash, digest.digest().hex())

        updateMedium(digest)
        digest.reset()
        val into = ByteArray(digest.digestLength())
        digest.digestInto(into, 0)
        assertEquals(expectedResetHash, into.hex())
    }

    /**
     * Exercises the Digest implementation with different sized inputs,
     * from (blockSize() - 30) to (blockSize() + 30), then combines all
     * those hashes into a single, final hash.
     * */
    open fun givenDigest_whenMultiBlockDigest_thenDigestDigestReturnsExpected() {
        assertExpectedHashes
        val inputLengths = (digest.blockSize() - 30)..(digest.blockSize() + 30)

        val outputs = mutableListOf<ByteArray>()
        for (len in inputLengths) {
            val digested = digest.digest(TestData.BYTES_MEDIUM.copyOf(len))
            outputs.add(digested)
        }

        for (output in outputs) {
            digest.update(output)
        }

        assertEquals(expectedMultiBlockHash, digest.digest().hex())
    }

    /**
     * Exercises the Digest implementation with 50 bytes of input
     * */
    open fun givenDigest_whenUpdatedSmall_thenDigestDigestReturnsExpected() {
        assertExpectedHashes
        updateSmall(digest)
        assertEquals(expectedUpdateSmallHash, digest.digest().hex())

        updateSmall(digest)
        var into = ByteArray(digest.digestLength() + 16)
        digest.digestInto(into, destOffset = 16)
        for (i in 0..<16) {
            assertEquals(0, into[i])
        }
        into = into.copyInto(destination = ByteArray(digest.digestLength()), startIndex = 16)
        assertEquals(expectedUpdateSmallHash, into.hex())
    }

    /**
     * Exercises the Digest implementation with 100_000 bytes of input
     * */
    open fun givenDigest_whenUpdatedMedium_thenDigestDigestReturnsExpected() {
        assertExpectedHashes
        updateMedium(digest)
        assertEquals(expectedUpdateMediumHash, digest.digest().hex())

        updateMedium(digest)
        val into = ByteArray(digest.digestLength())
        digest.digestInto(into, 0)
        assertEquals(expectedUpdateMediumHash, into.hex())
    }

    /**
     * Exercises the Digest implementation's copy functionality to ensure
     * that, when copied, all state was copied to a new instance.
     * */
    open fun givenDigest_whenCopied_thenIsDifferentInstance() {
        assertExpectedHashes

        // Ensure Digest abstraction's buffer was properly copied over to the new digest
        updateSmall(digest)
        val copy = digest.copy()
        assertNotEquals(copy, digest)
        assertEquals(expectedUpdateSmallHash, copy.digest().hex())
        assertEquals(expectedResetHash, copy.digest().hex())

        updateSmall(copy)
        assertEquals(expectedUpdateSmallHash, digest.digest().hex())
        assertEquals(expectedUpdateSmallHash, copy.digest().hex())
    }

    /**
     * Ensures that the Digest implementation has passed the proper digestLength
     * constructor parameter to the [Digest] abstraction.
     * */
    open fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        assertExpectedHashes
        val out = digest.digest()
        assertEquals(digest.digestLength(), out.size)
    }
}
