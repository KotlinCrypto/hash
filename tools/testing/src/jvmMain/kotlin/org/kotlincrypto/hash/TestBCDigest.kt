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
@file:Suppress("UnnecessaryOptInAnnotation")

package org.kotlincrypto.hash

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.internal.DigestState
import java.lang.IllegalStateException

@OptIn(InternalKotlinCryptoApi::class)
class TestBCDigest<T: org.bouncycastle.crypto.ExtendedDigest>: Digest {
    
    private val delegate: T
    private val copy: T.() -> T
    
    constructor(
        digest: T,
        copy: T.() -> T
    ): super(
        digest.algorithmName,
        digest.byteLength,
        digest.digestSize
    ) {
        this.delegate = digest
        this.copy = copy
    }

    @Suppress("UNCHECKED_CAST")
    private constructor(state: DigestState, digest: TestBCDigest<*>): super(state) {
        this.copy = digest.copy as T.() -> T
        this.delegate = digest.copy.invoke(digest.delegate as T)
    }

    override fun copy(state: DigestState): Digest = TestBCDigest<T>(state, this)

    override fun compress(input: ByteArray, offset: Int) { throw IllegalStateException("update is overridden...") }

    override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        val out = ByteArray(digestLength())
        delegate.doFinal(out, 0)
        return out
    }

    override fun updateDigest(input: Byte) { delegate.update(input) }
    override fun updateDigest(input: ByteArray, offset: Int, len: Int) { delegate.update(input, offset, len) }

    override fun resetDigest() { delegate.reset() }
}
