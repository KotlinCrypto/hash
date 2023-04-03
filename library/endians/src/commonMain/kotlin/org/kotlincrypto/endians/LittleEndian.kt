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
package org.kotlincrypto.endians

import org.kotlincrypto.endians.internal.ifMaxElse
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * LittleEndian ordered byte representation of a [Number].
 *
 * The [data] held is "immutable" in nature.
 *
 * @see [toLittleEndian]
 * @see [bytesTo]
 * */
@JvmInline
public value class LittleEndian private constructor(private val data: ByteArray) {

    /** [Short] 16 bits */
    public constructor(b0: Byte, b1: Byte): this(
        ByteArray(2).apply {
            this[0] = b0; this[1] = b1
        }
    )

    /** [Int] 32 bits */
    public constructor(b0: Byte, b1: Byte, b2: Byte, b3: Byte): this(
        ByteArray(4).apply {
            this[0] = b0; this[1] = b1; this[2] = b2; this[3] = b3
        }
    )

    /** [Long] 64 bits */
    public constructor(b0: Byte, b1: Byte, b2: Byte, b3: Byte, b4: Byte, b5: Byte, b6: Byte, b7: Byte): this(
        ByteArray(8).apply {
            this[0] = b0; this[1] = b1; this[2] = b2; this[3] = b3
            this[4] = b4; this[5] = b5; this[6] = b6; this[7] = b7
        }
    )

    @Throws(IndexOutOfBoundsException::class)
    public operator fun get(index: Int): Byte = data[index]
    public fun getOrNull(index: Int): Byte? = data.getOrNull(index)
    public fun getOrElse(index: Int, defaultValue: (Int) -> Byte): Byte = data.getOrElse(index, defaultValue)

    /**
     * Adds [data] with another [ByteArray], creating a new [ByteArray]
     * */
    public operator fun plus(other: ByteArray): ByteArray = data.plus(other)

    /**
     * Addition with another [LittleEndian], creating a new [LittleEndian]
     * */
    public operator fun plus(other: LittleEndian): LittleEndian {
        return when (size.ifMaxElse(other.size)) {
            2 -> (toShort().plus(other.toShort())).toLittleEndian()
            4 -> (toInt().plus(other.toInt())).toLittleEndian()
            else -> (toLong().plus(other.toLong())).toLittleEndian()
        }
    }

    /**
     * Subtraction with another [LittleEndian], creating a new [LittleEndian]
     * */
    public operator fun minus(other: LittleEndian): LittleEndian {
        return when (size.ifMaxElse(other.size)) {
            2 -> (toShort().minus(other.toShort())).toLittleEndian()
            4 -> (toInt().minus(other.toInt())).toLittleEndian()
            else -> (toLong().minus(other.toLong())).toLittleEndian()
        }
    }

    /**
     * Multiplication with another [LittleEndian], creating a new [LittleEndian]
     * */
    public operator fun times(other: LittleEndian): LittleEndian {
        return when (size.ifMaxElse(other.size)) {
            2 -> (toShort().times(other.toShort())).toLittleEndian()
            4 -> (toInt().times(other.toInt())).toLittleEndian()
            else -> (toLong().times(other.toLong())).toLittleEndian()
        }
    }

    /**
     * Division with another [LittleEndian], creating a new [LittleEndian]
     * */
    public operator fun div(other: LittleEndian): LittleEndian {
        return when (size.ifMaxElse(other.size)) {
            2 -> (toShort().div(other.toShort())).toLittleEndian()
            4 -> (toInt().div(other.toInt())).toLittleEndian()
            else -> (toLong().div(other.toLong())).toLittleEndian()
        }
    }

    /**
     * Remainder of with another [LittleEndian], creating a new [LittleEndian]
     * */
    public operator fun rem(other: LittleEndian): LittleEndian {
        return when (size.ifMaxElse(other.size)) {
            2 -> (toShort().rem(other.toShort())).toLittleEndian()
            4 -> (toInt().rem(other.toInt())).toLittleEndian()
            else -> (toLong().rem(other.toLong())).toLittleEndian()
        }
    }

    public val size: Int get() = data.size
    public val sizeBits: Int get() = size * Byte.SIZE_BITS

    public fun iterator(): ByteIterator = data.iterator()
    public fun copyInto(
        destination: ByteArray,
        destinationOffset: Int = 0,
        startIndex: Int = 0,
        endIndex: Int = size,
    ): ByteArray {
        return data.copyInto(destination, destinationOffset, startIndex, endIndex)
    }

    public fun toByteArray(): ByteArray = data.copyOf()
    public fun toByte(): Byte = data.first()
    public fun toShort(): Short = toNumber().toShort()
    public fun toInt(): Int = toNumber().toInt()
    public fun toLong(): Long = toNumber().toLong()

    /**
     * Flips bytes to [BigEndian] ordering
     * */
    public fun toBigEndian(): BigEndian {
        return when (size) {
            2 -> BigEndian(data[1], data[0])
            4 -> BigEndian(data[3], data[2], data[1], data[0])
            else -> BigEndian(data[7], data[6], data[5], data[4], data[3], data[2], data[1], data[0])
        }
    }

    public override fun toString(): String = "LE${data.toList()}"

    private fun toNumber(): Number {
        return when (size) {
            2 -> bytesTo(data[0], data[1])
            4 -> bytesTo(data[0], data[1], data[2], data[3])
            else -> bytesTo(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7])
        }
    }

    public companion object {
        /**
         * Converts a [Short] to [LittleEndian] ordered bytes
         * */
        @JvmStatic
        public fun Short.toLittleEndian(): LittleEndian {
            return LittleEndian(
                (this               ).toByte(),
                (this.toInt() ushr 8).toByte(),
            )
        }

        /**
         * Converts a [Int] to [LittleEndian] ordered bytes
         * */
        @JvmStatic
        public fun Int.toLittleEndian(): LittleEndian {
            return LittleEndian(
                (this        ).toByte(),
                (this ushr  8).toByte(),
                (this ushr 16).toByte(),
                (this ushr 24).toByte(),
            )
        }

        /**
         * Converts a [Long] to [LittleEndian] ordered bytes
         * */
        @JvmStatic
        public fun Long.toLittleEndian(): LittleEndian {
            return LittleEndian(
                (this        ).toByte(),
                (this ushr  8).toByte(),
                (this ushr 16).toByte(),
                (this ushr 24).toByte(),
                (this ushr 32).toByte(),
                (this ushr 40).toByte(),
                (this ushr 48).toByte(),
                (this ushr 56).toByte(),
            )
        }

        /**
         * Converts 2 [Byte]s with [LittleEndian] ordering to a [Short]
         * */
        @JvmStatic
        public fun bytesTo(b0: Byte, b1: Byte): Short {
            return  (
                        ((b0.toInt() and 0xff)       ) or
                        ((b1.toInt() and 0xff) shl  8)
                    ).toShort()
        }

        /**
         * Converts 4 [Byte]s with [LittleEndian] ordering to a [Int]
         * */
        @JvmStatic
        public fun bytesTo(b0: Byte, b1: Byte, b2: Byte, b3: Byte): Int {
            return  ((b0.toInt() and 0xff)       ) or
                    ((b1.toInt() and 0xff) shl  8) or
                    ((b2.toInt() and 0xff) shl 16) or
                    ((b3.toInt()         ) shl 24)
        }

        /**
         * Converts 8 [Byte]s with [LittleEndian] ordering to a [Long]
         * */
        @JvmStatic
        public fun bytesTo(b0: Byte, b1: Byte, b2: Byte, b3: Byte, b4: Byte, b5: Byte, b6: Byte, b7: Byte): Long {
            val lo = bytesTo(b0, b1, b2, b3)
            val hi = bytesTo(b4, b5, b6, b7)

            return  ((hi.toLong() and 0xffffffff) shl 32) or
                    ((lo.toLong() and 0xffffffff)       )
        }
    }
}
