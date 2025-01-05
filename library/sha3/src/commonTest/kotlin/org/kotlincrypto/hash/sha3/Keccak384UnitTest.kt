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

open class Keccak384UnitTest: DigestUnitTest() {
    override val digest: Digest = Keccak384()
    final override val expectedResetHash: String = "2c23146a63a29acf99e73b88f8c24eaa7dc60aa771780ccc006afbfa8fe2479b2dd2b21362337441ac12b515911957ff"
    final override val expectedMultiBlockHash: String = "c202c54422b2e7c0054828da52eb9aad26ba789b1105e455cbe1cb5a03372649ff2550dbf1669d738d293c35467f0fa5"
    final override val expectedUpdateSmallHash: String = "b72bbe6eb5e663354b10cbdb744141319b2fc7eda91da087291459a26f17e513b7ba689840afb92c4246b05a14d1b78f"
    final override val expectedUpdateMediumHash: String = "288cc2eae0bc30cddf7be0b09cc658146d4b00d40009dc5ac11feafed46a4d00c9a5bf3ed77e29d28a0eb2cb8e202679"

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
