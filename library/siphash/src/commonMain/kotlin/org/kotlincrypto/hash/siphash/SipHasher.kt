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
package org.kotlincrypto.hash.siphash


/**
 * Siphash implementation
 *
 * https://en.wikipedia.org/wiki/SipHash
 *
 * @see [xOf]
 * */
public class SipHasher {

    public companion object {

        /**
         * Default value for the C rounds of compression.
         */
        internal const val DEFAULT_C: Int = 2

        /**
         * Default value for the D rounds of compression.
         */
        internal const val DEFAULT_D: Int = 4

        /**
         * Initial value for the v0 magic number.
         */
        internal const val INITIAL_V0: Long = 0x736f6d6570736575L

        /**
         * Initial value for the v1 magic number.
         */
        internal const val INITIAL_V1: Long = 0x646f72616e646f6dL

        /**
         * Initial value for the v2 magic number.
         */
        internal const val INITIAL_V2: Long = 0x6c7967656e657261L

        /**
         * Initial value for the v3 magic number.
         */
        internal const val INITIAL_V3: Long = 0x7465646279746573L

        public fun container(key: ByteArray): SipHasherContainer = SipHasherContainer(key)

        public fun hash(key: ByteArray, data: ByteArray): Long =
            hash(key, data, DEFAULT_C, DEFAULT_D)

        public fun hash(key: ByteArray, data: ByteArray, c: Int, d: Int): Long {
            require(key.size == 16) { "Key must be exactly 16 bytes!" }
            val k0: Long = bytesToLong(key, 0)
            val k1: Long = bytesToLong(key, 8)

            return hash(
                c, d,
                INITIAL_V0 xor k0,
                INITIAL_V1 xor k1,
                INITIAL_V2 xor k0,
                INITIAL_V3 xor k1,
                data
            )
        }

        /**
         * Initializes a streaming hash, seeded with the given key.
         *
         * This will used the default values for C and D rounds.
         *
         * @param key
         *      the key to seed the hash with.
         * @return
         *      a {@link SipHasherStream} instance to update and digest.
         */
        public fun init(key: ByteArray) : SipHasherStream = init(key, DEFAULT_C, DEFAULT_D)

        /**
         * Initializes a streaming hash, seeded with the given key and desired
         * rounds of compression.
         *
         * This will used the default values for C and D rounds.
         *
         * @param key
         *      the key to seed the hash with.
         * @param c
         *      the number of C rounds of compression
         * @param d
         *      the number of D rounds of compression.
         * @return
         *      a {@link SipHasherStream} instance to update and digest.
         */
        public fun init(key: ByteArray, c: Int, d: Int) : SipHasherStream = SipHasherStream(key, c, d)

        /**
         * Converts a hash to a hexidecimal representation.
         *
         * @param hash
         *      the finalized hash value to convert to hex.
         * @return
         *      a {@link String} representation of the hash.
         */
        @OptIn(ExperimentalStdlibApi::class)
        public fun toHexString(hash: Long): String {
            val hex: String = hash.toHexString()

            if (hex.length == 16) {
                return hex
            }

            return hex.padEnd(16, '0')
        }

        /**
         * Converts a chunk of 8 bytes to a number in little endian.
         *
         * Accepts an offset to determine where the chunk begins.
         *
         * @param bytes
         *      the byte array containing our bytes to convert.
         * @param offset
         *      the index to start at when chunking bytes.
         * @return
         *      a long representation, in little endian.
         */
        public fun bytesToLong(bytes: ByteArray, offset: Int): Long {
            var m: Long = 0
            for (i in 0..7) {
                m = m or (bytes[i + offset].toLong() and 0xffL shl 8 * i)
            }
            return m
        }

        /**
         * Internal 0A hashing implementation.
         *
         * Requires initial state being manually provided (to avoid allocation). The
         * compression rounds must also be provided, as nothing will be validated in
         * this layer (such as defaults).
         *
         * @param c
         * the rounds of C compression to apply.
         * @param d
         * the rounds of D compression to apply.
         * @param v0
         * the seeded initial value of v0.
         * @param v1
         * the seeded initial value of v1.
         * @param v2
         * the seeded initial value of v2.
         * @param v3
         * the seeded initial value of v3.
         * @param data
         * the input data to hash using the SipHash algorithm.
         * @return
         * a long value as the output of the hash.
         */
        internal fun hash(c: Int, d: Int, v0: Long, v1: Long, v2: Long, v3: Long, data: ByteArray): Long {
            var v0 = v0
            var v1 = v1
            var v2 = v2
            var v3 = v3
            var m: Long
            val last = data.size / 8 * 8
            var i = 0
            var r: Int
            while (i < last) {
                m = data[i++].toLong() and 0xffL
                r = 1
                while (r < 8) {
                    m = m or (data[i++].toLong() and 0xffL shl r * 8)
                    r++
                }
                v3 = v3 xor m
                r = 0
                while (r < c) {
                    v0 += v1
                    v2 += v3
                    v1 = rotateLeft(v1, 13)
                    v3 = rotateLeft(v3, 16)
                    v1 = v1 xor v0
                    v3 = v3 xor v2
                    v0 = rotateLeft(v0, 32)
                    v2 += v1
                    v0 += v3
                    v1 = rotateLeft(v1, 17)
                    v3 = rotateLeft(v3, 21)
                    v1 = v1 xor v2
                    v3 = v3 xor v0
                    v2 = rotateLeft(v2, 32)
                    r++
                }
                v0 = v0 xor m
            }
            m = 0
            i = data.size - 1
            while (i >= last) {
                m = m shl 8
                m = m or (data[i].toLong() and 0xffL)
                --i
            }
            m = m or (data.size.toLong() shl 56)
            v3 = v3 xor m
            r = 0
            while (r < c) {
                v0 += v1
                v2 += v3
                v1 = rotateLeft(v1, 13)
                v3 = rotateLeft(v3, 16)
                v1 = v1 xor v0
                v3 = v3 xor v2
                v0 = rotateLeft(v0, 32)
                v2 += v1
                v0 += v3
                v1 = rotateLeft(v1, 17)
                v3 = rotateLeft(v3, 21)
                v1 = v1 xor v2
                v3 = v3 xor v0
                v2 = rotateLeft(v2, 32)
                r++
            }
            v0 = v0 xor m
            v2 = v2 xor 0xffL
            r = 0
            while (r < d) {
                v0 += v1
                v2 += v3
                v1 = rotateLeft(v1, 13)
                v3 = rotateLeft(v3, 16)
                v1 = v1 xor v0
                v3 = v3 xor v2
                v0 = rotateLeft(v0, 32)
                v2 += v1
                v0 += v3
                v1 = rotateLeft(v1, 17)
                v3 = rotateLeft(v3, 21)
                v1 = v1 xor v2
                v3 = v3 xor v0
                v2 = rotateLeft(v2, 32)
                r++
            }
            return v0 xor v1 xor v2 xor v3
        }

        /**
         * Rotates an input number `val` left by `shift` number of bits.
         *
         * Bits which are pushed off to the left are rotated back onto the right,
         * making this a left rotation (a circular shift).
         *
         * This is very close to [Long.rotateLeft] aside from
         * the use of the 64 bit masking.
         *
         * @param value
         * the value to be shifted.
         * @param shift
         * how far left to shift.
         * @return
         * a long value after being shifted.
         */
        internal fun rotateLeft(value: Long, shift: Int): Long {
            return value shl shift or (value ushr 64 - shift)
        }
    }
}
