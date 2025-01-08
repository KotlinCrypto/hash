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
@file:Suppress("KotlinRedundantDiagnosticSuppress", "NOTHING_TO_INLINE")

package org.kotlincrypto.hash.blake2.internal

import org.kotlincrypto.bitops.endian.Endian.Little.leIntAt
import org.kotlincrypto.bitops.endian.Endian.Little.leLongAt
import kotlin.jvm.JvmInline

private const val SIZE_MESSAGE = 16

@JvmInline
internal value class Bit32Message internal constructor(internal val m: IntArray) {
    internal constructor(b: ByteArray, offset: Int): this(IntArray(SIZE_MESSAGE) { i ->
        b.leIntAt((i * Int.SIZE_BYTES) + offset)
    })
}

@JvmInline
internal value class Bit64Message internal constructor(internal val m: LongArray) {
    internal constructor(b: ByteArray, offset: Int): this(LongArray(SIZE_MESSAGE) { i ->
        b.leLongAt((i * Long.SIZE_BYTES) + offset)
    })
}

internal inline operator fun Bit32Message.get(sigmaByte: Byte): Int = m[sigmaByte.toInt()]
internal inline operator fun Bit64Message.get(sigmaByte: Byte): Long = m[sigmaByte.toInt()]

internal inline fun Bit32Message.copy(): Bit32Message = Bit32Message(m.copyOf())
internal inline fun Bit64Message.copy(): Bit64Message = Bit64Message(m.copyOf())

internal inline fun Bit32Message.fill() { m.fill(0) }
internal inline fun Bit64Message.fill() { m.fill(0) }

internal inline fun Bit32Message.populate(b: ByteArray, offset: Int) {
    val m = m
    for (i in 0..<SIZE_MESSAGE) { m[i] = b.leIntAt((i * Int.SIZE_BYTES) + offset) }
}

internal inline fun Bit64Message.populate(b: ByteArray, offset: Int) {
    val m = m
    for (i in 0..<SIZE_MESSAGE) { m[i] = b.leLongAt((i * Long.SIZE_BYTES) + offset) }
}
