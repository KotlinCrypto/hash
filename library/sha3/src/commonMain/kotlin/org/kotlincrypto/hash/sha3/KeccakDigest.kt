/*
 * Copyright (c) 2023 KotlinCrypto
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
@file:Suppress("LocalVariableName")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.bitops.endian.Endian.Little.leLongAt
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.error.InvalidParameterException
import org.kotlincrypto.sponges.keccak.F1600
import org.kotlincrypto.sponges.keccak.keccakP
import kotlin.experimental.xor
import kotlin.jvm.JvmField

/**
 * Core abstraction for:
 *  - [Keccak224]
 *  - [Keccak256]
 *  - [Keccak384]
 *  - [Keccak512]
 *  - [SHA3_224]
 *  - [SHA3_256]
 *  - [SHA3_384]
 *  - [SHA3_512]
 *
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * @see [SHAKEDigest]
 * */
public sealed class KeccakDigest: Digest {

    // domain separation byte
    private val dsByte: Byte
    private val state: F1600

    @Throws(InvalidParameterException::class)
    protected constructor(
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
        dsByte: Byte,
    ): super(algorithm, blockSize, digestLength) {
        this.dsByte = dsByte
        this.state = F1600()
    }

    protected constructor(other: KeccakDigest): super(other) {
        this.dsByte = other.dsByte
        this.state = other.state.copy()
    }

    public abstract override fun copy(): KeccakDigest

    protected final override fun compressProtected(input: ByteArray, offset: Int) {
        val A = state

        var APos = 0
        var inputPos = offset
        val inputLimit = inputPos + blockSize()

        // Max blockSize is 168 (SHAKE128), so at a maximum only
        // 21 of 25 state values will ever be modified with input
        // for each permutation.
        while (inputPos < inputLimit) {
            A.addData(index = APos++, data = input.leLongAt(offset = inputPos))
            inputPos += Long.SIZE_BYTES
        }

        A.keccakP()
    }

    protected final override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
        val len = digestLength()
        return finalizeAndExtractTo(ByteArray(len), 0, buf, bufPos, len)
    }

    protected final override fun digestIntoProtected(dest: ByteArray, destOffset: Int, buf: ByteArray, bufPos: Int) {
        finalizeAndExtractTo(dest, destOffset, buf, bufPos, digestLength())
    }

    protected open fun finalizeAndExtractTo(
        dest: ByteArray,
        destOffset: Int,
        buf: ByteArray,
        bufPos: Int,
        len: Int,
    ): ByteArray {
        buf[bufPos] = dsByte
        val iLast = buf.lastIndex
        buf[iLast] = buf[iLast] xor 0x80.toByte()
        compressProtected(buf, 0)
        return extract(state, null, dest, destOffset, len)
    }

    protected open fun extract(
        A: F1600,
        r: SpongeRemainder?,
        out: ByteArray,
        offset: Int,
        len: Int,
    ): ByteArray {
        var outPos = offset
        val outLimit = outPos + len

        val spongeSize = blockSize()
        val spongeLimit = (spongeSize / Long.SIZE_BYTES) + 1

        // Bytes available in the sponge for extraction before another permutation is needed
        var spongeRem = r?.value ?: spongeSize
        var spongePos = (spongeSize - spongeRem) / Long.SIZE_BYTES

        while (outPos < outLimit) {
            while (spongePos < spongeLimit) {
                val lane = A[spongePos++]

                val laneOffset = when {
                    spongeRem < Long.SIZE_BYTES -> {
                        // If there is 6 bytes remaining in the sponge
                        // 8 - 6 = 2
                        // indices: 2, 3, 4, 5, 6, 7 = 6 bytes to read out
                        //
                        // If there is 0 bytes remaining in the sponge
                        // 8 - 0 = 8
                        // indices: 8 so no bytes will be written
                        Long.SIZE_BYTES - spongeRem
                    }
                    outPos == offset -> {
                        // Must check first block of the extraction
                        // if it is a partial read (e.g. prior extract
                        // call was for 10 bytes, leaving 6 remaining
                        // in this block of the sponge).
                        val priorRem = spongeRem % Long.SIZE_BYTES
                        if (priorRem == 0) {
                            // Full block. Copy all of it.
                            0
                        } else {
                            // Partial block. Copy remainder.
                            Long.SIZE_BYTES - priorRem
                        }
                    }
                    else -> 0
                }

                var i = 0
                while (outPos < outLimit && (laneOffset + i) < Long.SIZE_BYTES) {
                    val bits = Byte.SIZE_BITS * (laneOffset + i++)
                    out[outPos++] = (lane ushr bits).toByte()
                }

                if (i == 0) {
                    // Either sponge is exhausted, or out is full.
                    break
                }
                spongeRem -= i
            }

            if (spongeRem == 0) {
                // Do another permutation to refill the sponge
                A.keccakP()
                spongePos = 0
                spongeRem = spongeSize
            }
        }

        r?.let { it.value = spongeRem }
        return out
    }

    protected override fun resetProtected() {
        state.reset()
    }

    // Helper for tracking sponge state across multiple
    // calls to extract (for SHAKEDigest Xof functionality).
    protected class SpongeRemainder(d: KeccakDigest) {
        @JvmField
        public var value: Int = d.blockSize()
    }

    protected companion object {
        internal const val KECCAK: String = "Keccak"
        internal const val SHA3: String = "SHA3"

        internal const val PAD_KECCAK: Byte = 0x01
        internal const val PAD_SHA3: Byte = 0x06
    }
}
