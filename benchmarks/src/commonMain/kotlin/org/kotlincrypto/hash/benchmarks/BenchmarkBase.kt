/*
 * Copyright (c) 2024 Matthew Nelson
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

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Setup
import org.kotlincrypto.core.digest.Digest
import kotlin.random.Random

const val ITERATIONS = 5
const val TIME_WARMUP = 2
const val TIME_MEASURE = 4

abstract class HashBenchmarkBase(private val blockSize: Int) {

    abstract fun update(input: ByteArray, offset: Int, len: Int)
    abstract fun digest(input: ByteArray): ByteArray

    // Number of bytes is based off of the algorithm's block size in
    // order to fairly benchmark the Digest implementation such that 2
    // full compressions + overflow input are processed.
    private val bytes = Random.Default.nextBytes((blockSize * 2) + (blockSize / 4))

    @Setup
    fun setup() { update(bytes, 0, blockSize) }

    @Benchmark
    fun digest() { digest(bytes) }
}

abstract class DigestBenchmarkBase(private val d: Digest): HashBenchmarkBase(d.blockSize()) {

    final override fun digest(input: ByteArray): ByteArray {
        return d.digest(input)
    }
    final override fun update(input: ByteArray, offset: Int, len: Int) {
        d.update(input, offset, len)
    }
}
