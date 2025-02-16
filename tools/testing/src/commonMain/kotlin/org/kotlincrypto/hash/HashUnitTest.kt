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
package org.kotlincrypto.hash

import org.kotlincrypto.core.Updatable
import kotlin.test.assertEquals

sealed class HashUnitTest {
    abstract val expectedResetHash: String
    abstract val expectedUpdateSmallHash: String
    abstract val expectedUpdateMediumHash: String

    internal val assertExpectedHashes by lazy {
        val expected = 4
        val set = mutableSetOf(
            expectedResetHash,
            expectedUpdateSmallHash,
            expectedUpdateMediumHash,
        )

        when (this) {
            is DigestUnitTest -> expectedMultiBlockHash
            is XofUnitTest -> expectedPartialReadHash
        }.let { set.add(it) }

        assertEquals(expected, set.size, "Expected hash values must all be different")
    }

    companion object {
        fun updateSmall(updatable: Updatable) {
            updatable.update(TestData.BYTES_SMALL)
        }

        fun updateMedium(updatable: Updatable) {
            // Some algorithms don't discard 0 length input, effecting
            // the end result (e.g. TupleHash). This ensures that, if
            // that's the case, it is exercised in testing.
            updatable.update(TestData.BYTES_EMPTY)
            updatable.update(TestData.BYTES_EMPTY, 0, TestData.BYTES_EMPTY.size)

            updatable.update(TestData.BYTES_MEDIUM[0])
            updatable.update(TestData.BYTES_MEDIUM)
            updatable.update(TestData.BYTES_MEDIUM, 100, 1_000)
        }
    }
}
