/*
 * Copyright (c) 2024 KotlinCrypto
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
package org.kotlincrypto.hash.benchmarks

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.kotlincrypto.core.digest.Digest
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Security

abstract class BouncyCastleBenchmarkBase private constructor(
    private val d: MessageDigest,
    blockSize: Int,
): HashBenchmarkBase(blockSize) {

    constructor(kcDigest: Digest): this(kcDigest.toBCDigest(), kcDigest.blockSize())

    final override fun digest(input: ByteArray): ByteArray {
        return d.digest(input)
    }
    final override fun update(input: ByteArray, offset: Int, len: Int) {
        d.update(input, offset, len)
    }

    private companion object {

        private val INIT_BC by lazy {
            Security.addProvider(BouncyCastleProvider())
        }

        @Throws(NoSuchAlgorithmException::class)
        private fun Digest.toBCDigest(): MessageDigest {
            INIT_BC
            return MessageDigest.getInstance(algorithm(), BouncyCastleProvider.PROVIDER_NAME)
        }
    }
}
