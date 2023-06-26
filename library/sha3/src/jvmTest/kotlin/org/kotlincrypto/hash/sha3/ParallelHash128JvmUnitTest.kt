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

import org.bouncycastle.crypto.digests.ParallelHash
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.TestBCDigest

class ParallelHash128JvmUnitTest: ParallelHash128UnitTest() {
    override val digest: Digest = TestBCDigest(ParallelHash(128, null, B)) {
        ParallelHash(this)
    }

    override fun givenDigest_whenCopied_thenIsDifferentInstance() {
        // https://github.com/bcgit/bc-java/issues/1375
//        super.givenDigest_whenCopied_thenIsDifferentInstance()
    }
}
