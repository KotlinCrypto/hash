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
import java.security.MessageDigest

/**
 * Simple test class that warps a [java.security.MessageDigest]
 * with [org.kotlincrypto.core.Digest]
 * */
@OptIn(InternalKotlinCryptoApi::class)
class TestJvmDigest: Digest {

    private val delegate: MessageDigest

    private constructor(
        digest: MessageDigest,
        blockSize: Int
    ): super(
        digest.algorithm,
        blockSize,
        digest.digestLength
    ) {
        delegate = digest
    }

    private constructor(
        state: DigestState,
        digest: TestJvmDigest,
    ): super(state) {
        delegate = digest.delegate.clone() as MessageDigest
    }

    override fun copy(state: DigestState): Digest = TestJvmDigest(state, this)

    override fun compress(buffer: ByteArray) {
        delegate.update(buffer)
    }

    override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        delegate.update(buffer, 0, bufferOffset)
        return delegate.digest()
    }

    override fun resetDigest() {
        delegate.reset()
    }

    companion object {
        @JvmStatic
        fun md5() = TestJvmDigest(getInstance("MD5"), 64)

        @JvmStatic
        fun sha1() = TestJvmDigest(getInstance("SHA-1"), 64)

        @JvmStatic
        fun sha256() = TestJvmDigest(getInstance("SHA-256"), 64)

        @JvmStatic
        fun sha512() = TestJvmDigest(getInstance("SHA-512"), 128)
    }
}
