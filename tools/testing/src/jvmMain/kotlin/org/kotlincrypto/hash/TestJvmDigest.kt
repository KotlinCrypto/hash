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

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.kotlincrypto.core.digest.Digest
import java.lang.IllegalStateException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Security
import kotlin.jvm.Throws

/**
 * Simple test class that warps a [java.security.MessageDigest]
 * with [org.kotlincrypto.core.digest.Digest]
 * */
class TestJvmDigest: Digest {

    private val delegate: MessageDigest

    constructor(digest: Digest): this(provideInstance(digest.algorithm()), digest.blockSize())

    private constructor(
        digest: MessageDigest,
        blockSize: Int
    ): super(digest.algorithm, blockSize, digest.digestLength) {
        delegate = digest
    }

    private constructor(other: TestJvmDigest): super(other) {
        delegate = other.delegate.clone() as MessageDigest
    }

    override fun copy(): Digest = TestJvmDigest(this)

    override fun compressProtected(input: ByteArray, offset: Int) { throw IllegalStateException("update is overridden...") }

    override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray = delegate.digest()

    override fun updateProtected(input: Byte) { delegate.update(input) }

    override fun updateProtected(input: ByteArray, offset: Int, len: Int) { delegate.update(input, offset, len) }

    override fun resetProtected() { delegate.reset() }

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
