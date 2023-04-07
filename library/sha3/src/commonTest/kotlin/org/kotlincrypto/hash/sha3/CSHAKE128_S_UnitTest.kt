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

import org.kotlincrypto.core.Digest
import kotlin.test.Test

open class CSHAKE128_S_UnitTest: CSHAKE128UnitTest() {
    override val digest: Digest = CSHAKE128(null, S)
    final override val expectedResetHash: String = "4f3047dee03c3b698f2b6da12bffe7ff89bb5c5bb0bc4e4a8a2ba77c12d70af6"
    final override val expectedUpdateSmallHash: String = "42de220e99553116a60d6316540774d2e9984419cffe5ac2fc62cf0b41227ac3"
    final override val expectedUpdateMediumHash: String = "1379ca33af3ec1032c51eed4c3996c18f90e3121d8ce8ef36323fa159045472f"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
