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
package org.kotlincrypto.hash

import org.kotlincrypto.core.digest.Digest
import java.lang.IllegalStateException

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
    private constructor(other: TestBCDigest<*>): super(other) {
        this.copy = other.copy as T.() -> T
        this.delegate = other.copy.invoke(other.delegate as T)
    }

    override fun copy(): Digest = TestBCDigest<T>(this)

    override fun compressProtected(input: ByteArray, offset: Int) { throw IllegalStateException("update is overridden...") }

    override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
        val out = ByteArray(digestLength())
        delegate.doFinal(out, 0)
        return out
    }

    override fun updateProtected(input: Byte) { delegate.update(input) }
    override fun updateProtected(input: ByteArray, offset: Int, len: Int) { delegate.update(input, offset, len) }

    override fun resetProtected() { delegate.reset() }
}
