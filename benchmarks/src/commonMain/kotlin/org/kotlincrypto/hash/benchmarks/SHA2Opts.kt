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
@file:Suppress("ClassName")

package org.kotlincrypto.hash.benchmarks

import kotlinx.benchmark.*
import org.kotlincrypto.hash.md.MD5
import org.kotlincrypto.hash.sha1.SHA1
import org.kotlincrypto.hash.sha2.SHA256
import org.kotlincrypto.hash.sha2.SHA512
import org.kotlincrypto.hash.sha2.SHA512_224

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class MD5Benchmark: DigestBenchmarkBase(MD5())

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class SHA1Benchmark: DigestBenchmarkBase(SHA1())

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class SHA256Benchmark: DigestBenchmarkBase(SHA256())

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
@Warmup(iterations = ITERATIONS, time = TIME_WARMUP)
@Measurement(iterations = ITERATIONS, time = TIME_MEASURE)
open class SHA512Benchmark: DigestBenchmarkBase(SHA512())
