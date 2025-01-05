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
@file:Suppress("ClassName")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.digest.Digest
import kotlin.test.Test

open class CSHAKE128_NS_UnitTest: CSHAKE128UnitTest() {
    override val digest: Digest = CSHAKE128(N, S)
    final override val expectedResetHash: String = "91d6b4ecfd7f295f8e11a2a738a294bb1e4f9fa5aec7834e37ac97c81c41bf8e"
    final override val expectedMultiBlockHash: String = "f642e42d66a0192dfa28cfdfd04f96db7f39922b6bca659b10555a571951cd94"
    final override val expectedUpdateSmallHash: String = "ba9a9ecb49bac859073f141aa782aa3b0f2006183819b0b2aacf52bbc77b85aa"
    final override val expectedUpdateMediumHash: String = "bcc7b6cb50fe65aa14bbc050d973d87c3dad221f402f6114c1c22c643ae966f1"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
