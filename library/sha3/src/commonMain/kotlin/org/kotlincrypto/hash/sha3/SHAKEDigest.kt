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
@file:Suppress("LocalVariableName", "SpellCheckingInspection")

package org.kotlincrypto.hash.sha3

import org.kotlincrypto.bitops.endian.Endian.Little.leLongAt
import org.kotlincrypto.core.*
import org.kotlincrypto.core.xof.*
import org.kotlincrypto.sponges.keccak.F1600
import kotlin.jvm.JvmStatic

/**
 * Core abstraction for:
 *  - CSHAKE128
 *  - CSHAKE256
 *  - SHAKE128
 *  - SHAKE256
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 *
 * Also see support libraries utilized:
 *  - https://github.com/KotlinCrypto/bitops
 *  - https://github.com/KotlinCrypto/sponges
 *
 * @see [ParallelDigest]
 * @see [TupleDigest]
 * */
public sealed class SHAKEDigest: KeccakDigest, XofAlgorithm {

    private val initBlock: ByteArray?
    private var isReadingXof: Boolean
    private val xOfMode: Boolean

    protected constructor(
        N: ByteArray?,
        S: ByteArray?,
        xOfMode: Boolean,
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
    ): super(algorithm, blockSize, digestLength, dsByteFromInput(N, S)) {
        this.xOfMode = xOfMode
        this.isReadingXof = false

        @OptIn(InternalKotlinCryptoApi::class)
        this.initBlock = if (N?.isNotEmpty() == true || S?.isNotEmpty() == true) {
            val nSize = N?.size ?: 0
            val sSize = S?.size ?: 0

            // Prepare encodings
            val encBlockSize = Xof.Utils.leftEncode(blockSize)
            val encNSizeBits = nSize.leftEncodeBits()
            val encSSizeBits = sSize.leftEncodeBits()

            val b = ByteArray(encBlockSize.size + encNSizeBits.size + nSize + encSSizeBits.size + sSize)

            encBlockSize.copyInto(b)
            encNSizeBits.copyInto(b, encBlockSize.size)
            N?.copyInto(b, encBlockSize.size + encNSizeBits.size)
            encSSizeBits.copyInto(b, encBlockSize.size + encNSizeBits.size + nSize)
            S?.copyInto(b, encBlockSize.size + encNSizeBits.size + nSize + encSSizeBits.size)

            b
        } else {
            null
        }

        initBlock?.bytepad()
    }

    protected constructor(other: SHAKEDigest): super(other) {
        this.xOfMode = other.xOfMode
        this.isReadingXof = other.isReadingXof
        this.initBlock = other.initBlock
    }

    public abstract override fun copy(): SHAKEDigest

    protected final override fun extract(
        A: F1600,
        r: SpongeRemainder?,
        out: ByteArray,
        offset: Int,
        len: Int,
    ): ByteArray {
        return if (xOfMode && !isReadingXof) {
            // newReader called digest(). Snipe the extraction
            // and pass it the current state in bytes.
            val newOut = ByteArray(A.size * Long.SIZE_BYTES)
            var j = 0
            A.forEach { lane ->
                newOut[j++] = (lane        ).toByte()
                newOut[j++] = (lane ushr  8).toByte()
                newOut[j++] = (lane ushr 16).toByte()
                newOut[j++] = (lane ushr 24).toByte()
                newOut[j++] = (lane ushr 32).toByte()
                newOut[j++] = (lane ushr 40).toByte()
                newOut[j++] = (lane ushr 48).toByte()
                newOut[j++] = (lane ushr 56).toByte()
            }
            isReadingXof = true
            return newOut
        } else {
            super.extract(A, r, out, offset, len)
        }
    }

    protected override fun resetProtected() {
        super.resetProtected()
        initBlock?.bytepad()

        // Don't want to ever reset isReadingXof b/c Xof mode
        // uses a copy of the digest in an isolated Reader environment.
        // The copy created for that Xof.Reader will never go back to
        // being updated, nor will it be copied again.
//        isReadingXof = false
    }

    private fun ByteArray.bytepad() {
        super.updateProtected(this, 0, size)

        val remainder = size % blockSize()

        // No padding is needed
        if (remainder == 0) return

        repeat(blockSize() - remainder) {
            super.updateProtected(0)
        }
    }

    public sealed class SHAKEXofFactory<A: SHAKEDigest>: XofFactory<A>() {

        protected inner class SHAKEXof
        @Throws(IllegalArgumentException::class)
        constructor(delegate: A) : XofFactory<A>.XofDelegate(delegate) {

            init {
                require(delegate.xOfMode) { "xOfMode must be true" }

                // Some algorithms for the SHA3 derived functions require encoding
                // the final output length before outputting data. While in XOF mode,
                // the arbitrary output length, L, is represented as 0.
                require(delegate.digestLength() == 0) { "digestLength must be 0" }
            }

            protected override fun newReader(delegateCopy: A): Xof<A>.Reader {

                // Calling digest() will flush the copy's buffered input and
                // apply padding and final permutation(s). Because it is in
                // xOfMode, the returned bytes will be the entire contents
                // of its final state such that it can be rebuilt here in
                // order to be used for variable output length reads.
                val A: F1600 = delegateCopy.digest().let { b ->
                    val new = F1600()

                    for (i in new.indices) {
                        new.addData(index = i, data = b.leLongAt(i * Long.SIZE_BYTES))
                    }

                    b.fill(0)

                    new
                }

                val remainder = SpongeRemainder(d = delegateCopy)

                return object : Reader() {
                    override fun readProtected(out: ByteArray, offset: Int, len: Int): Int {
                        delegateCopy.extract(A, remainder, out, offset, len)
                        return len
                    }

                    override fun closeProtected() {
                        // delegateCopy was already reset when digest()
                        // was called in order to pass state.

                        A.reset()
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            public override fun copy(): Xof<A> = SHAKEXof(delegate.copy() as A)
        }
    }

    protected companion object {
        internal const val SHAKE: String = "SHAKE"
        internal const val CSHAKE: String = "C$SHAKE"

        private const val PAD_SHAKE: Byte = 0x1f
        private const val PAD_CSHAKE: Byte = 0x04

        internal const val  BIT_STRENGTH_128 = 128
        internal const val  BIT_STRENGTH_256 = 256

        // blockSize for bitStrength of 128
        internal const val BLOCK_SIZE_BIT_128 = 168
        // blockSize for bitStrength of 256
        internal const val BLOCK_SIZE_BIT_256 = 136

        // default digestLength for bitStrength of 128
        internal const val DIGEST_LENGTH_BIT_128 = 32
        // default digestLength for bitStrength of 256
        internal const val DIGEST_LENGTH_BIT_256 = 64

        /**
         * Given that [N] and [S] are both null and/or empty, CSHAKE is
         * functionally equivalent to SHAKE and thus the [dsByte] must
         * reflect so.
         * */
        @JvmStatic
        private fun dsByteFromInput(N: ByteArray?, S: ByteArray?): Byte {
            return if (N?.isNotEmpty() == true || S?.isNotEmpty() == true) PAD_CSHAKE else PAD_SHAKE
        }

        @Throws(IllegalArgumentException::class)
        internal fun blockSizeFromBitStrength(bitStrength: Int): Int {
            return when (bitStrength) {
                BIT_STRENGTH_128 -> BLOCK_SIZE_BIT_128
                BIT_STRENGTH_256 -> BLOCK_SIZE_BIT_256
                else -> throw IllegalArgumentException("bitStrength must be $BIT_STRENGTH_128 or $BIT_STRENGTH_256")
            }
        }

        internal fun Int.leftEncodeBits(): ByteArray {
            @OptIn(InternalKotlinCryptoApi::class)
            return Xof.Utils.leftEncode(lo = shl(3), hi = ushr(29))
        }

        internal fun Int.rightEncodeBits(): ByteArray {
            @OptIn(InternalKotlinCryptoApi::class)
            return Xof.Utils.rightEncode(lo = shl(3), hi = ushr(29))
        }
    }
}
