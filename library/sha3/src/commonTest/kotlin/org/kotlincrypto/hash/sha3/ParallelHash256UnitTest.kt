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

open class ParallelHash256UnitTest: DigestUnitTest() {
    protected val B = 40
    override val digest: Digest = ParallelHash256(null, B)
    final override val expectedResetHash: String = "c912d0bdb32207cdde2741dd89b024d347bf6c4b21b0dfb993ac7c655338efb8600758399450135a617e0196b2aa2aa530f89f45f5a08b6e30bb14336aebf6ac"
    final override val expectedMultiBlockHash: String = "db01f29fb2ec302a81d008adf280c61f7f53464a6d1f6bb358c91cf0f65270a31bfe8d290a9cba0112337049e9dc3cb0df46572bc94e171ccd31b3687741f634"
    final override val expectedUpdateSmallHash: String = "0b5b7211348311af030dc87fa746a6715156d4cd8c8ba6080d787a2434b865eb0427c39ffa91dcc7d1a28033a56f2fc766a218eb435dd15de44a25a448e2d5ac"
    final override val expectedUpdateMediumHash: String = "1d3bde77bf3ab54e5501d2160854a707088aa0060429c273c7b428dc5dad007a65868d0bdf6cec557d5efb8c6c9d48923621aa034edb7e32cbdd878e82897acf"

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
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

    @Test
    final override fun givenDigest_whenDigested_thenLengthMatchesOutput() {
        super.givenDigest_whenDigested_thenLengthMatchesOutput()
    }

}
