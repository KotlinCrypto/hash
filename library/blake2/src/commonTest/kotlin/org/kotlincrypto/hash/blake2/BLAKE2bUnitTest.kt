/*
 * Copyright (c) 2025 KotlinCrypto
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

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.error.InvalidKeyException
import org.kotlincrypto.error.InvalidParameterException
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(InternalKotlinCryptoApi::class)
class BLAKE2bUnitTest {

    @Test
    fun givenBLAKE2bDigest_whenInvalidBitStrength_thenThrowsException() {
        assertFailsWith<InvalidParameterException> { BLAKE2b(8 - 8) }
        assertFailsWith<InvalidParameterException> { BLAKE2b(8 - 1) }
        assertFailsWith<InvalidParameterException> { BLAKE2b(512 + 8) }
        assertFailsWith<InvalidParameterException> { BLAKE2b(512 + 1) }
    }

    @Test
    fun givenBLAKE2bDigest_whenInvalidPersonalization_thenThrowsException() {
        // Checks assertion parameters used for non-personalization arguments are valid
        BLAKE2b(512, ByteArray(16))
        assertFailsWith<InvalidParameterException> { BLAKE2b(512, ByteArray(16 - 1)) }
    }

    @Test
    fun givenBLAKE2bDigest_whenInvalidSalt_thenThrowsException() {
        // Checks assertion parameters used for non-salt arguments are valid
        BLAKE2b(512, 0, salt = ByteArray(16), null)
        assertFailsWith<InvalidParameterException> { BLAKE2b(512, 0, salt = ByteArray(16 -1), null) }
    }

    @Test
    fun givenBLAKE2bDigest_whenInvalidKeyLength_thenThrowsException() {
        // Checks assertion parameters used for non keyLength arguments are valid
        BLAKE2b(512, 0, null, null)
        assertFailsWith<InvalidKeyException> { BLAKE2b(512, 0 - 1, null, null) }
        assertFailsWith<InvalidKeyException> { BLAKE2b(512, 64 + 1, null, null) }
    }
}
