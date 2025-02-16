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
import kotlin.test.assertEquals

class SHAKEDigestUnitTest {
    
    @Test
    fun givenSHAKEDigest_whenLengthNonDefault_thenReturnsExpectedByteSize() {
        // This exercises ALL inheritors of SHAKEDigest and how they will
        // function with a non-default outputLength argument (i.e. digestLength)
        SHAKE128(outputLength = 0).let { digest ->
            assertEquals(0, digest.digestLength())
            assertEquals(0, digest.digest().size)
        }

        SHAKE256(outputLength = 500).let { digest ->
            assertEquals(500, digest.digestLength())
            assertEquals(500, digest.digest().size)
        }
    }
}
