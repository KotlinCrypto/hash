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

@Suppress("ClassName")
open class SHA3_512UnitTest: DigestUnitTest() {
    override val digest: Digest = SHA3_512()
    final override val expectedResetHash: String = "a69f73cca23a9ac5c8b567dc185a756e97c982164fe25859e0d1dcc1475c80a615b2123af1f5f94c11e3e9402c3ac558f500199d95b6d3e301758586281dcd26"
    final override val expectedMultiBlockHash: String = "31ef1874aa60198b5ae36adb3a700751461ab6af3ad1f2dac95ccfe0d93f4a1b1b36122414f3d819d5d85cfee2789dd3793af6166892009682e893a9e19a9716"
    final override val expectedUpdateSmallHash: String = "c264245a4e9ed0b44ff30465ecb32cc03813fc1d14fc9baf67c65c65ce815bb594aeabd4b20eac6f24090537f0e667e00eb3e2a3e6ad63a54962860c180f45b0"
    final override val expectedUpdateMediumHash: String = "b4f4480828d0e2e4bfdf2e39671fa296e355457e7db585a26dee0d0201bc589f24a6342ede76eb345b35b504f196967044c53223722e3330bee12f346dea4a2c"

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
