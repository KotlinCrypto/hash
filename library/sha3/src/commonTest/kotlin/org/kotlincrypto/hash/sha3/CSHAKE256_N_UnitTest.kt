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

import org.kotlincrypto.core.Digest
import kotlin.test.Test

@Suppress("ClassName")
open class CSHAKE256_N_UnitTest: CSHAKE256UnitTest() {
    override val digest: Digest = CSHAKE256(N, null)
    final override val expectedResetHash: String = "56259e24d1d2ca5400a86d12ad5eabe07b56775bca0e14c4df830246881a62618cd0fbfeeedd9478a6a5a620b385df382e3075acb3617bed3b9b38aa8278607b"
    final override val expectedUpdateSmallHash: String = "af4c741ba13b8ed16ab66bca8ba3d27b8d59b8684287a3dff063684c513c8422b9a31b341ae5aefee2b57d68d509790004b2e519757da5093f9ba5b6ea4ef317"
    final override val expectedUpdateMediumHash: String = "7dd59bc3efe51455f6127396ebf44981860322d93118f40b65e92b078c9ecda92b28e42ed354218d8b625f99cb14e11bc4c7d85440d5b0aa08ab18569e5fe224"

    @Test
    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }

}
