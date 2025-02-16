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
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.DigestUnitTest
import kotlin.test.Test

@Suppress("ClassName")
@OptIn(InternalKotlinCryptoApi::class)
open class BLAKE2b_512_Salt_Personalization_UnitTest: DigestUnitTest() {
    protected val salt = ByteArray(16) { i -> i.toByte() }
    protected val personalization = ByteArray(16) { i -> (i + 10).toByte() }

    override val digest: Digest = BLAKE2b(bitStrength = 512, keyLength = 0, salt = salt, personalization = personalization)
    final override val expectedResetHash: String = "e7430c9636230cdaf666d80cde27a8a5bd1c56433e5254908b8c8dcc86617d03f611dfa9377894906ac0e7d787a3bac40aa47d9b9956f646406391cb5f4ff97c"
    final override val expectedMultiBlockHash: String = "c32b79af750f5bc4edb0d9aa5a1a1214c9f57af08e5a36c66f5ba1c851aeb5179fdbf18d52651057e6d5f8b86c21b16fb69a0b5bc8c6b20f68e227817acf8e06"
    final override val expectedUpdateSmallHash: String = "264a572da6ed920acc62ac39b67675789242fa97a24cb13c7541793ffe26c1f807c71cbd768a0d535114c790c49eea13fa44df6df36a946b20d81b51818bee52"
    final override val expectedUpdateMediumHash: String = "75657c44947a09ea63a5e14f287773819e825b4d2ccece9fcee379acec2914db158fbf282999b70cfe2181ba1547567b6023a4b0109c648848c00591430df527"

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
