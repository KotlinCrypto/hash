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
package org.kotlincrypto.core

import org.kotlincrypto.endians.BigEndian.Companion.toBigEndian
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Extendable-Output Function (i.e. XOF)
 *
 * FIPS PUB 202 introduced XOFs where output for certain cryptographic functions can be
 * variable in length. This is an implementation which provides such functionality.
 *
 * e.g.
 *
 *     val xof = SHAKE128.xOf()
 *     xof.update(Random.Default.nextBytes(500))
 *
 *     val out1 = ByteArray(64)
 *     val out2 = ByteArray(out1.size * 2)
 *     xof.use(resetXof = false) { read(out1); read(out2) }
 *
 *     val out3 = ByteArray(out1.size)
 *     val out4 = ByteArray(out2.size)
 *     val reader = xof.reader()
 *     reader.read(out3)
 *     reader.use { read(out4, 0, out4.size) }
 *
 *     assertContentEquals(out1, out3)
 *     assertContentEquals(out2, out4)
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * @see [use]
 * @see [reader]
 * @see [Reader]
 * */
public sealed class Xof<A: Algorithm>: Algorithm, Copyable<Xof<A>>, Resettable, Updatable {

    /**
     * Takes a snapshot of the current [Xof]'s state and produces
     * a [Reader].
     *
     * [Reader] is automatically closed after [action] completes
     * such that reading from the closed [Reader] will throw
     * exception; another [Reader] must be produced if further
     * reading is required.
     *
     * The [Xof] can continue to be updated with new data or read
     * from again as it is unaffected by [Reader] [action]s.
     *
     * @param [resetXof] if true, also resets the [Xof] to its
     *   initial state.
     * */
    @JvmOverloads
    public fun <T: Any?> use(resetXof: Boolean = true, action: Reader.() -> T): T = reader(resetXof).use(action)

    /**
     * Takes a snapshot of the current [Xof]'s state and produces
     * a [Reader].
     *
     * The [Reader] remains open and will continue to produce new
     * output until [Reader.close] is explicitly called.
     *
     * The [Xof] can continue to be updated with new data or read
     * from again as it is unaffected by [Reader.read]s.
     *
     * @param [resetXof] if true, also resets the [Xof] to its
     *   initial state.
     * */
    @JvmOverloads
    public fun reader(resetXof: Boolean = true): Reader {
        val reader = newReader()

        // newReader() takes copy of Xof at its current
        // state, so calling reset() _after_ is ok.
        if (resetXof) reset()

        return reader
    }

    /**
     * Reads the [Xof] snapshot.
     * */
    public abstract inner class Reader {

        /**
         * The total amount of bytes read for this [Reader] instance
         * */
        @get:JvmName("bytesRead")
        public var bytesRead: Long = 0
            private set

        /**
         * If the reader is closed or not
         * */
        @get:JvmName("isClosed")
        public var isClosed: Boolean = false
            private set

        /**
         * Helper function which automatically calls [close]
         * once [action] completes.
         * */
        public fun <T: Any?> use(action: Reader.() -> T): T {
            return try {
                action(this)
            } finally {
                close()
            }
        }

        /**
         * Reads the [Xof] snapshot's state for when [Reader] was
         * produced, filling the provided [out] array completely.
         *
         * This can be called multiple times within the [Xof.use]
         * block (before [close] is automatically called).
         *
         * @param [out] The array to fill
         * @return The number of bytes written to [out]
         * @throws [IllegalStateException] if [isClosed] is true
         * */
        @Throws(IllegalStateException::class)
        public fun read(out: ByteArray): Int = read(out, 0, out.size)

        /**
         * Reads the [Xof] snapshot's state for when [Reader] was
         * produced, filling the provided [out] array for specified
         * [offset] and [len] arguments.
         *
         * This can be called multiple times within the [Xof.use]
         * block (before [close] is automatically called).
         *
         * @param [out] The array to put the data into
         * @param [offset] The index for [out] to start putting data
         * @param [len] The number of bytes to put into [out]
         * @return The number of bytes written to [out]
         * @throws [IllegalArgumentException] if [offset] and/or [len] are inappropriate
         * @throws [IllegalStateException] if [isClosed] is true
         * @throws [IndexOutOfBoundsException] if [offset] and/or [len] are inappropriate
         * */
        @Throws(IllegalArgumentException::class, IllegalStateException::class, IndexOutOfBoundsException::class)
        public fun read(out: ByteArray, offset: Int, len: Int): Int {
            if (isClosed) throw IllegalStateException("Reader is closed")
            if (out.size - offset < len) throw IllegalArgumentException("out is too short")
            if (len == 0) return 0
            if (offset < 0 || len < 0 || offset > out.size - len) throw IndexOutOfBoundsException()

            readProtected(out, offset, len, bytesRead)
            bytesRead += len
            return len
        }

        /**
         * Closes the [Reader], rendering it no-longer usable for [read]s.
         *
         * Successive calls after initial closure do nothing.
         * */
        public fun close() {
            if (isClosed) return
            closeProtected()
            isClosed = true
        }

        protected abstract fun closeProtected()
        protected abstract fun readProtected(out: ByteArray, offset: Int, len: Int, bytesRead: Long)

        public final override fun toString(): String = "${this@Xof}.Reader@${hashCode()}"
    }

    protected abstract fun newReader(): Reader

    public final override fun toString(): String = "Xof[${algorithm()}]@${hashCode()}"

    @InternalKotlinCryptoApi
    public object Utils {

        @JvmStatic
        public fun leftEncode(value: Long): ByteArray {
            // If it's zero, return early with [1, 0]
            if (value == 0L) return ByteArray(2).apply { this[0] = 1 }

            val be = value.toBigEndian()

            // Find index of first non-zero byte
            var i = 0
            while (i < be.size && be[i] == 0.toByte()) {
                i++
            }

            val b = ByteArray(be.size - i + 1)

            // Prepend with number of non-zero bytes
            b[0] = (be.size - i).toByte()

            be.copyInto(b, 1, i)

            return b
        }

        @JvmStatic
        public fun rightEncode(value: Long): ByteArray {
            // If it's zero, return early with [0, 1]
            if (value == 0L) return ByteArray(2).apply { this[1] = 1 }

            val be = value.toBigEndian()

            // Find index of first non-zero byte
            var i = 0
            while (i < be.size && be[i] == 0.toByte()) {
                i++
            }

            val b = ByteArray(be.size - i + 1)

            // Append with number of non-zero bytes
            b[b.lastIndex] = (be.size - i).toByte()

            be.copyInto(b, 0, i)

            return b
        }
    }
}
