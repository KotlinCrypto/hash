/*
 * Copyright (c) 2025 KotlinCrypto
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
@file:Suppress("NOTHING_TO_INLINE")

package org.kotlincrypto.hash.blake2.internal

import org.kotlincrypto.bitops.endian.Endian.Little.lePackIntoUnsafe
import kotlin.jvm.JvmInline

private const val LEN_MESSAGE = 16
private const val SIZE_MESSAGE_32B = LEN_MESSAGE * Int.SIZE_BYTES
private const val SIZE_MESSAGE_64B = LEN_MESSAGE * Long.SIZE_BYTES

@JvmInline
internal value class Bit32Message internal constructor(internal val m: IntArray) {
    internal constructor(b: ByteArray, offset: Int): this(IntArray(LEN_MESSAGE)) { populate(b, offset) }
}

@JvmInline
internal value class Bit64Message internal constructor(internal val m: LongArray) {
    internal constructor(b: ByteArray, offset: Int): this(LongArray(LEN_MESSAGE)) { populate(b, offset) }
}

internal inline operator fun Bit32Message.get(sigmaByte: Byte): Int = m[sigmaByte.toInt()]
internal inline operator fun Bit64Message.get(sigmaByte: Byte): Long = m[sigmaByte.toInt()]

internal inline fun Bit32Message.copy(): Bit32Message = Bit32Message(m.copyOf())
internal inline fun Bit64Message.copy(): Bit64Message = Bit64Message(m.copyOf())

internal inline fun Bit32Message.fill() { m.fill(0) }
internal inline fun Bit64Message.fill() { m.fill(0) }

internal inline fun Bit32Message.populate(b: ByteArray, offset: Int) {
    b.lePackIntoUnsafe(m, destOffset = 0, sourceIndexStart = offset, sourceIndexEnd = offset + SIZE_MESSAGE_32B)
}

internal inline fun Bit64Message.populate(b: ByteArray, offset: Int) {
    b.lePackIntoUnsafe(m, destOffset = 0, sourceIndexStart = offset, sourceIndexEnd = offset + SIZE_MESSAGE_64B)
}
