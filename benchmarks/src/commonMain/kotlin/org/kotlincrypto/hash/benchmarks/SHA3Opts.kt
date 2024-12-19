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

import kotlinx.benchmark.*
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.hash.sha3.ParallelHash128
import org.kotlincrypto.hash.sha3.SHA3_256
import org.kotlincrypto.hash.sha3.SHAKE128
import org.kotlincrypto.hash.sha3.TupleHash128

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class SHA3_256Benchmark: DigestBenchmarkBase() {
    override val d: Digest = SHA3_256()
}

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class SHAKE128Benchmark: DigestBenchmarkBase() {
    override val d: Digest = SHAKE128()
}

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class ParallelHash128Benchmark: DigestBenchmarkBase() {
    override val d: Digest = ParallelHash128(null, 64)
}

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class TupleHash128Benchmark: DigestBenchmarkBase() {
    override val d: Digest = TupleHash128(null)
}
