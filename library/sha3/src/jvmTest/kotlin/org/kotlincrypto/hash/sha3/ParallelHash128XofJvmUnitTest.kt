/*
 * Copyright (c) 2023 KotlinCrypto
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
import org.kotlincrypto.core.Updatable

class ParallelHash128XofJvmUnitTest: ParallelHash128XofUnitTest() {
    private val digest = ParallelHash(128, null, B)
    override val xof: Updatable = object : Updatable {
        override fun update(input: Byte) { digest.update(input) }
        override fun update(input: ByteArray) { digest.update(input, 0, input.size) }
        override fun update(input: ByteArray, offset: Int, len: Int) { digest.update(input, offset, len) }
    }

    override fun read(vararg args: ByteArray) {
        args.forEach {
            digest.doOutput(it, 0, it.size)
        }
    }

    override fun partialRead(out: ByteArray, offset: Int, len: Int) {
        digest.doOutput(out, offset, len)
    }

    override fun reset() {
        digest.reset()
    }

}
