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
@file:Suppress("UnnecessaryOptInAnnotation")

package org.kotlincrypto.hash.blake2

import org.kotlincrypto.core.digest.Digest
import kotlin.jvm.JvmStatic

/**
 * Core abstraction for:
 *  - BLAKE2b
 *  - BLAKE2s
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 * */
public sealed class BLAKE2Digest: Digest {

    @Throws(IllegalArgumentException::class)
    protected constructor(
        blockSize: Int,
        bitStrength: Int,
    ): super(
        algorithm = algorithmFrom(blockSize, bitStrength),
        blockSize = blockSize,
        digestLength = bitStrength / 8,
    ) {
        // s:  64 * 4 = 256
        // b: 128 * 4 = 512
        val max = bitStrength * 4
        require(bitStrength <= max) { "bitStrength must be less than or equal to $max" }
        require(bitStrength >= 8) { "bitStrength must be greater than or equal to 8" }
        require(bitStrength % 8 == 0) { "bitStrength must be a factor of 8" }
    }

    protected constructor(other: BLAKE2Digest): super(other)

    public abstract override fun copy(): BLAKE2Digest

    protected companion object {
        internal const val BLOCK_SIZE_S = 64
        internal const val BLOCK_SIZE_B = 128

        internal val SIGMA = run {
            val s0 =
                byteArrayOf( 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15)
            val s1 =
                byteArrayOf(14, 10,  4,  8,  9, 15, 13,  6,  1, 12,  0,  2, 11,  7,  5,  3)

            arrayOf(
                s0,
                s1,
                byteArrayOf(11,  8, 12,  0,  5,  2, 15, 13, 10, 14,  3,  6,  7,  1,  9,  4),
                byteArrayOf( 7,  9,  3,  1, 13, 12, 11, 14,  2,  6,  5, 10,  4,  0, 15,  8),
                byteArrayOf( 9,  0,  5,  7,  2,  4, 10, 15, 14,  1, 11, 12,  6,  8,  3, 13),
                byteArrayOf( 2, 12,  6, 10,  0, 11,  8,  3,  4, 13,  7,  5, 15, 14,  1,  9),
                byteArrayOf(12,  5,  1, 15, 14, 13,  4, 10,  0,  7,  6,  3,  9,  2,  8, 11),
                byteArrayOf(13, 11,  7, 14, 12,  1,  3,  9,  5,  0, 15,  4,  8,  6,  2, 10),
                byteArrayOf( 6, 15, 14,  9, 11,  3,  0,  8, 12,  2, 13,  7,  1,  4, 10,  5),
                byteArrayOf(10,  2,  8,  4,  7,  6,  1,  5, 15, 11,  9, 14,  3, 12, 13,  0),

                // Extra rounds for BLAKE2b
                s0,
                s1,
            )
        }

        @JvmStatic
        @Throws(IllegalArgumentException::class)
        private fun algorithmFrom(blockSize: Int, bitStrength: Int): String {
            // TODO: xOfMode
            //  val mode = if (digestLength == 0) "x" else ""

            val variant = when (blockSize) {
                BLOCK_SIZE_S -> 's'
                BLOCK_SIZE_B -> 'b'
                else -> throw IllegalArgumentException("blockSize must be $BLOCK_SIZE_S or $BLOCK_SIZE_B")
            }

            return "BLAKE2$variant-$bitStrength"
        }
    }
}
