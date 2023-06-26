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

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState
import java.lang.IllegalStateException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Security
import kotlin.jvm.Throws

/**
 * Simple test class that warps a [java.security.MessageDigest]
 * with [org.kotlincrypto.core.digest.Digest]
 * */
@OptIn(InternalKotlinCryptoApi::class)
class TestJvmDigest: Digest {

    private val delegate: MessageDigest

    constructor(digest: Digest): this(provideInstance(digest.algorithm()), digest.blockSize())

    private constructor(
        digest: MessageDigest,
        blockSize: Int
    ): super(digest.algorithm, blockSize, digest.digestLength) {
        delegate = digest
    }

    private constructor(
        state: DigestState,
        digest: TestJvmDigest,
    ): super(state) {
        delegate = digest.delegate.clone() as MessageDigest
    }

    override fun copy(state: DigestState): Digest = TestJvmDigest(state, this)

    override fun compress(input: ByteArray, offset: Int) { throw IllegalStateException("update is overridden...") }

    override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray = delegate.digest()

    override fun updateDigest(input: Byte) { delegate.update(input) }

    override fun updateDigest(input: ByteArray, offset: Int, len: Int) { delegate.update(input, offset, len) }

    override fun resetDigest() { delegate.reset() }

    private companion object {

        /**
         * Will fall back to using [BouncyCastleProvider] in the event
         * an algorithm is not available via Java.
         * */
        @Throws(NoSuchAlgorithmException::class)
        private fun provideInstance(algorithm: String): MessageDigest {
            try {
                return getInstance(algorithm)
            } catch (_: NoSuchAlgorithmException) {
                synchronized(this) {
                    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                        Security.addProvider(BouncyCastleProvider())
                    }

                    return getInstance(algorithm)
                }
            }
        }
    }
}
