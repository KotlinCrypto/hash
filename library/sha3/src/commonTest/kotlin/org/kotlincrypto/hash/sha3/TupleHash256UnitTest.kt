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
import org.kotlincrypto.core.Digest
import org.kotlincrypto.hash.DigestUnitTest

open class TupleHash256UnitTest: DigestUnitTest() {
    override val digest: Digest = TupleHash256(null)
    final override val expectedResetHash: String = "3afbba494aedd16073746e9a04ac28c3e7b023fed42bcb1935d26b0ce9ed212703448a3b08b8656bd32e5fdd3ebe72fb7575ab1eefa93b84286556bead103a0a"
    final override val expectedMultiBlockHash: String = "82c91dea4a29848566ee147fafb20a008d5318829b9511e8f67481ea82669c5e74d68ac3270451558f98ff20b0023296fe131d13b1718208af2d4a12b2029d2b"
    final override val expectedUpdateSmallHash: String = "40229c2938b049e6999139d01d43aaaed408d74801ca0304ed4d4b70c23b43b69fe0da06914bde2bc2d3f22f33ada4f94d51446bd75b8a435deb6b9e6b3cf69b"
    final override val expectedUpdateMediumHash: String = "3436cb12063597f95cbe6dc7fbe2e99ddcf6906d9795de61a8b98ed1987801049d5d0791dc184638e861d128a8286fcf15132117556d670d780037e98c936e5f"

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
