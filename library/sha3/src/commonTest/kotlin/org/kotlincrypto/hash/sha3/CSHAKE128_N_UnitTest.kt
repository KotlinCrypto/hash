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

open class CSHAKE128_N_UnitTest: CSHAKE128UnitTest() {
    override val digest: Digest = CSHAKE128(N, null)
    final override val expectedResetHash: String = "a310f48745f5546f73dcbbd2b26e02c4242628f447530566cafe58e3dcc2786d"
    final override val expectedUpdateSmallHash: String = "f4161585d94be8b4ba5f9e6d4814361c42fdcfbb605477f547f73a8fa6f83da8"
    final override val expectedUpdateMediumHash: String = "b8113406bf27e08c46d3f8588bdb2380be03ea6d63ff25821c8fcf41150abf1b"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
