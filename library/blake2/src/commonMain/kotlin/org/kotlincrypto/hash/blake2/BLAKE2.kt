/*
 * Copyright (c) 2025 Matthew Nelson
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
@file:JvmName("BLAKE2")
@file:Suppress("FunctionName", "KotlinRedundantDiagnosticSuppress", "NOTHING_TO_INLINE")

package org.kotlincrypto.hash.blake2

import kotlin.jvm.JvmName

/**
 * BLAKE2b-160
 * */
@JvmName("b_160")
public inline fun BLAKE2b_160(): BLAKE2b = BLAKE2b(bitStrength = 160)

/**
 * BLAKE2b-256
 * */
@JvmName("b_256")
public inline fun BLAKE2b_256(): BLAKE2b = BLAKE2b(bitStrength = 256)

/**
 * BLAKE2b-384
 * */
@JvmName("b_384")
public inline fun BLAKE2b_384(): BLAKE2b = BLAKE2b(bitStrength = 384)

/**
 * BLAKE2b-512
 * */
@JvmName("b_512")
public inline fun BLAKE2b_512(): BLAKE2b = BLAKE2b(bitStrength = 512)

/**
 * BLAKE2s-128
 * */
@JvmName("s_128")
public inline fun BLAKE2s_128(): BLAKE2s = BLAKE2s(bitStrength = 128)

/**
 * BLAKE2s-160
 * */
@JvmName("s_160")
public inline fun BLAKE2s_160(): BLAKE2s = BLAKE2s(bitStrength = 160)

/**
 * BLAKE2s-224
 * */
@JvmName("s_224")
public inline fun BLAKE2s_224(): BLAKE2s = BLAKE2s(bitStrength = 224)

/**
 * BLAKE2s-256
 * */
@JvmName("s_256")
public inline fun BLAKE2s_256(): BLAKE2s = BLAKE2s(bitStrength = 256)
