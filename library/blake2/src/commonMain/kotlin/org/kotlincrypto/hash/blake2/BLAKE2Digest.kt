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
@file:Suppress("NOTHING_TO_INLINE", "RedundantVisibilityModifier", "SameParameterValue", "FunctionName")

package org.kotlincrypto.hash.blake2

import org.kotlincrypto.bitops.bits.Counter
import org.kotlincrypto.bitops.endian.Endian.Little.leIntAt
import org.kotlincrypto.bitops.endian.Endian.Little.leLongAt
import org.kotlincrypto.bitops.endian.Endian.Little.lePackIntoUnsafe
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.error.InvalidKeyException
import org.kotlincrypto.error.InvalidParameterException
import org.kotlincrypto.error.requireParam
import org.kotlincrypto.hash.blake2.internal.*
import kotlin.jvm.JvmField

/**
 * Core abstraction for:
 *  - [BLAKE2Digest.Bit32]
 *  - [BLAKE2Digest.Bit64]
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 *
 * https://www.blake2.net/blake2.pdf
 * */
public sealed class BLAKE2Digest: Digest {

    @JvmField
    protected val isLastNode: Boolean

    // digestLength
    @JvmField
    protected val keyLength: Int
    @JvmField
    protected val fanOut: Int
    @JvmField
    protected val depth: Int
    @JvmField
    protected val leafLength: Int
    @JvmField
    protected val nodeOffset: Long
    @JvmField
    protected val nodeDepth: Int
    @JvmField
    protected val innerLength: Int
    @JvmField
    protected val salt: ByteArray?
    @JvmField
    protected val personalization: ByteArray?

    /**
     * Core abstraction for:
     *  - [BLAKE2s]
     * */
    public sealed class Bit32: BLAKE2Digest {

        private val v: IntArray
        private val h: IntArray
        private var m: Bit32Message?
        private val t: Counter.Bit32

        @Throws(InvalidKeyException::class, InvalidParameterException::class)
        protected constructor(
            variant: String,
            bitStrength: Int,
            isLastNode: Boolean,
            keyLength: Int,
            fanOut: Int,
            depth: Int,
            leafLength: Int,
            nodeOffset: Long,
            nodeDepth: Int,
            innerLength: Int,
            salt: ByteArray?,
            personalization: ByteArray?,
        ): super(
            variant = variant,
            blockSize = BLOCK_SIZE,
            bitStrength = bitStrength,
            isLastNode = isLastNode,
            keyLength = keyLength,
            fanOut = fanOut,
            depth = depth,
            leafLength = leafLength,
            nodeOffset = nodeOffset,
            nodeDepth = nodeDepth,
            innerLength = innerLength,
            salt = salt,
            personalization = personalization,
        ) {
            require(nodeOffset in 0..MAX_NODE_OFFSET) { "nodeOffset must be between 0 and $MAX_NODE_OFFSET (inclusive)" }

            this.v = IntArray(16)
            this.h = IntArray(8).initialize()
            this.m = null
            this.t = Counter.Bit32(incrementBy = BLOCK_SIZE)
        }

        protected constructor(other: Bit32): super(other) {
            this.v = other.v.copyOf()
            this.h = other.h.copyOf()
            this.m = other.m?.copy()
            this.t = other.t.copy()
        }

        public abstract override fun copy(): Bit32

        protected final override fun compressProtected(input: ByteArray, offset: Int) {
            val m = m

            if (m == null) {
                // The Digest abstraction will call compressProtected whenever 1 block of
                // input is available for processing. BLAKE2 requires a finalization byte
                // to be set for the last compression. Because of this and not knowing if
                // more input will be coming, compressions are always 1 block behind until
                // digestProtected is called which will polish things off.
                //
                // This scenario could arise if the Digest is updated with 1 full block of
                // bytes (compressProtected is called and that block would be processed
                // with no finalization flag), then digest gets called without adding more
                // input (so a bufPos of 0); the Digest abstraction's buffer would be empty,
                // but the last block of input had already been processed!
                this.m = Bit32Message(input, offset)
                return
            }

            t.increment()
            F(h = h, m = m, tLo = t.lo, tHi = t.hi, f0 = 0, f1 = 0)

            // Populate m with this invocation's input to process next
            m.populate(input, offset)
        }

        protected final override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
            val digest = ByteArray(digestLength())
            digestIntoProtected(digest, 0, buf, bufPos)
            return digest
        }

        protected final override fun digestIntoProtected(dest: ByteArray, destOffset: Int, buf: ByteArray, bufPos: Int) {
            var m = m

            if (m != null) {
                // Have a buffered block that needs to be processed

                if (bufPos == 0) {
                    // No additional input, buffered block is final.
                    t.increment()
                } else {
                    // Process buffered block and update m with buf
                    compressProtected(buf, 0)
                }
            } else {
                // compressProtected has not been called yet. Input
                // in the buffer is all that has been collected.
                m = Bit32Message(buf, 0)

                // Set global m so that reset call after return will
                // clear it out for us.
                this.m = m
            }

            val (tLo, tHi) = t.final(additional = bufPos)

            val h = h
            F(h = h, m = m, tLo = tLo, tHi = tHi, f0 = -1, f1 = if (isLastNode) -1 else 0)

            val len = digestLength()
            val rem = len % Int.SIZE_BYTES
            val hIndexEnd = len / Int.SIZE_BYTES

            h.lePackIntoUnsafe(
                dest = dest,
                destOffset = destOffset,
                sourceIndexStart = 0,
                sourceIndexEnd = hIndexEnd,
            )

            if (rem == 0) return
            h[hIndexEnd].lePackIntoUnsafe(
                dest = dest,
                destOffset = destOffset + len - rem,
                sourceIndexStart = 0,
                sourceIndexEnd = rem,
            )
        }

        private inline fun F(h: IntArray, m: Bit32Message, tLo: Int, tHi: Int, f0: Int, f1: Int) {
            val iv = IV
            val v = v

            // Initialize local work vector v
            h.copyInto(v)
            iv.copyInto(v, h.size, 0, 4)
            v[12] = tLo xor iv[4]
            v[13] = tHi xor iv[5]
            v[14] = f0  xor iv[6]
            v[15] = f1  xor iv[7]

            // Cryptographic mixing
            val s = SIGMA

            for (i in 0 until ROUND_COUNT) {
                G(v = v, a = 0, b = 4, c =  8, d = 12, x = m[s[i][ 0]], y = m[s[i][ 1]])
                G(v = v, a = 1, b = 5, c =  9, d = 13, x = m[s[i][ 2]], y = m[s[i][ 3]])
                G(v = v, a = 2, b = 6, c = 10, d = 14, x = m[s[i][ 4]], y = m[s[i][ 5]])
                G(v = v, a = 3, b = 7, c = 11, d = 15, x = m[s[i][ 6]], y = m[s[i][ 7]])

                G(v = v, a = 0, b = 5, c = 10, d = 15, x = m[s[i][ 8]], y = m[s[i][ 9]])
                G(v = v, a = 1, b = 6, c = 11, d = 12, x = m[s[i][10]], y = m[s[i][11]])
                G(v = v, a = 2, b = 7, c =  8, d = 13, x = m[s[i][12]], y = m[s[i][13]])
                G(v = v, a = 3, b = 4, c =  9, d = 14, x = m[s[i][14]], y = m[s[i][15]])
            }

            // xor the two halves
            for (i in 0 until 8) {
                h[i] = h[i] xor v[i] xor v[i + 8]
            }
        }

        private inline fun G(v: IntArray, a: Int, b: Int, c: Int, d: Int, x: Int, y: Int) {
            var va = v[a]
            var vb = v[b]
            var vc = v[c]
            var vd = v[d]

            va = (va  +  vb + x)
            vd = (vd xor va    ).rotateRight(R1)
            vc = (vc  +  vd    )
            vb = (vb xor vc    ).rotateRight(R2)
            va = (va  +  vb + y)
            vd = (vd xor va    ).rotateRight(R3)
            vc = (vc  +  vd    )
            vb = (vb xor vc    ).rotateRight(R4)

            v[a] = va
            v[b] = vb
            v[c] = vc
            v[d] = vd
        }

        // h (32 byte block)
        private inline fun IntArray.initialize(): IntArray {
            val iv = IV
            val salt = salt
            val personalization = personalization

            // bytes 0-3
            this[0] = iv[0] xor (
                (digestLength()  ) or
                (keyLength shl  8) or
                (fanOut    shl 16) or
                (depth     shl 24)
            )

            // bytes 4-7
            this[1] = iv[1] xor leafLength

            // Page 6 of blake2.pdf states the least significant bits go into bytes
            // 8-11, where 16 bits of the most significant go into bytes 12-13
            this[2] = iv[2] xor nodeOffset.toInt()
            this[3] = iv[3] xor ((nodeOffset shr 32).toInt() or (nodeDepth shl 16) or (innerLength shl 24))

            // bytes 16-23
            if (salt == null) {
                this[4] = iv[4]
                this[5] = iv[5]
            } else {
                this[4] = iv[4] xor salt.leIntAt(0)
                this[5] = iv[5] xor salt.leIntAt(4)
            }

            // bytes 24-31
            if (personalization == null) {
                this[6] = iv[6]
                this[7] = iv[7]
            } else {
                this[6] = iv[6] xor personalization.leIntAt(0)
                this[7] = iv[7] xor personalization.leIntAt(4)
            }

            return this
        }

        protected final override fun resetProtected() {
            v.fill(0)
            h.initialize()
            m?.fill()
            m = null
            t.reset()
        }

        private companion object {
            // 2^48 - 1
            private const val MAX_NODE_OFFSET = 281474976710655

            private const val BLOCK_SIZE = 64
            private const val ROUND_COUNT = 10

            private const val R1 = 16
            private const val R2 = 12
            private const val R3 = 8
            private const val R4 = 7

            private val IV = intArrayOf(
                1779033703, -1150833019, 1013904242, -1521486534,
                1359893119, -1694144372,  528734635,  1541459225,
            )
        }
    }

    /**
     * Core abstraction for:
     *  - [BLAKE2b]
     * */
    public sealed class Bit64: BLAKE2Digest {

        private val v: LongArray
        private val h: LongArray
        private var m: Bit64Message?
        private val t: Counter.Bit64

        @Throws(InvalidKeyException::class, InvalidParameterException::class)
        protected constructor(
            variant: String,
            bitStrength: Int,
            isLastNode: Boolean,
            keyLength: Int,
            fanOut: Int,
            depth: Int,
            leafLength: Int,
            nodeOffset: Long,
            nodeDepth: Int,
            innerLength: Int,
            salt: ByteArray?,
            personalization: ByteArray?,
        ): super(
            variant = variant,
            blockSize = BLOCK_SIZE,
            bitStrength = bitStrength,
            isLastNode = isLastNode,
            keyLength = keyLength,
            fanOut = fanOut,
            depth = depth,
            leafLength = leafLength,
            nodeOffset = nodeOffset,
            nodeDepth = nodeDepth,
            innerLength = innerLength,
            salt = salt,
            personalization = personalization,
        ) {
            this.v = LongArray(16)
            this.h = LongArray(8).initialize()
            this.m = null
            this.t = Counter.Bit64(incrementBy = BLOCK_SIZE.toLong())
        }

        protected constructor(other: Bit64): super(other) {
            this.v = other.v.copyOf()
            this.h = other.h.copyOf()
            this.m = other.m?.copy()
            this.t = other.t.copy()
        }

        public abstract override fun copy(): Bit64

        protected final override fun compressProtected(input: ByteArray, offset: Int) {
            val m = m

            if (m == null) {
                // The Digest abstraction will call compressProtected whenever 1 block of
                // input is available for processing. BLAKE2 requires a finalization byte
                // to be set for the last compression. Because of this and not knowing if
                // more input will be coming, compressions are always 1 block behind until
                // digestProtected is called which will polish things off.
                //
                // This scenario could arise if the Digest is updated with 1 full block of
                // bytes (compressProtected is called and that block would be processed
                // with no finalization flag), then digest gets called without adding more
                // input (so a bufPos of 0); the Digest abstraction's buffer would be empty,
                // but the last block of input had already been processed!
                this.m = Bit64Message(input, offset)
                return
            }

            t.increment()
            F(h = h, m = m, tLo = t.lo, tHi = t.hi, f0 = 0L, f1 = 0L)

            // Populate m with this invocation's input to process next
            m.populate(input, offset)
        }

        protected final override fun digestProtected(buf: ByteArray, bufPos: Int): ByteArray {
            val digest = ByteArray(digestLength())
            digestIntoProtected(digest, 0, buf, bufPos)
            return digest
        }

        protected final override fun digestIntoProtected(dest: ByteArray, destOffset: Int, buf: ByteArray, bufPos: Int) {
            var m = m

            if (m != null) {
                // Have a buffered block that needs to be processed

                if (bufPos == 0) {
                    // No additional input, buffered block is final.
                    t.increment()
                } else {
                    // Process buffered block and update m with buf
                    compressProtected(buf, 0)
                }
            } else {
                // compressProtected has not been called yet. Input
                // in the buffer is all that has been collected.
                m = Bit64Message(buf, 0)

                // Set global m so that reset call after return will
                // clear it out for us.
                this.m = m
            }

            val (tLo, tHi) = t.final(additional = bufPos)

            val h = h
            F(h = h, m = m, tLo = tLo, tHi = tHi, f0 = -1L, f1 = if (isLastNode) -1L else 0L)

            val len = digestLength()
            val rem = len % Long.SIZE_BYTES
            val hIndexEnd = len / Long.SIZE_BYTES

            h.lePackIntoUnsafe(
                dest = dest,
                destOffset = destOffset,
                sourceIndexStart = 0,
                sourceIndexEnd = hIndexEnd,
            )

            if (rem == 0) return
            h[hIndexEnd].lePackIntoUnsafe(
                dest = dest,
                destOffset = destOffset + len - rem,
                sourceIndexStart = 0,
                sourceIndexEnd = rem,
            )
        }

        private inline fun F(h: LongArray, m: Bit64Message, tLo: Long, tHi: Long, f0: Long, f1: Long) {
            val iv = IV
            val v = v

            // Initialize local work vector v
            h.copyInto(v)
            iv.copyInto(v, h.size, 0, 4)
            v[12] = tLo xor iv[4]
            v[13] = tHi xor iv[5]
            v[14] = f0  xor iv[6]
            v[15] = f1  xor iv[7]

            // Cryptographic mixing
            val s = SIGMA

            for (i in 0 until ROUND_COUNT) {
                G(v = v, a = 0, b = 4, c =  8, d = 12, x = m[s[i][ 0]], y = m[s[i][ 1]])
                G(v = v, a = 1, b = 5, c =  9, d = 13, x = m[s[i][ 2]], y = m[s[i][ 3]])
                G(v = v, a = 2, b = 6, c = 10, d = 14, x = m[s[i][ 4]], y = m[s[i][ 5]])
                G(v = v, a = 3, b = 7, c = 11, d = 15, x = m[s[i][ 6]], y = m[s[i][ 7]])

                G(v = v, a = 0, b = 5, c = 10, d = 15, x = m[s[i][ 8]], y = m[s[i][ 9]])
                G(v = v, a = 1, b = 6, c = 11, d = 12, x = m[s[i][10]], y = m[s[i][11]])
                G(v = v, a = 2, b = 7, c =  8, d = 13, x = m[s[i][12]], y = m[s[i][13]])
                G(v = v, a = 3, b = 4, c =  9, d = 14, x = m[s[i][14]], y = m[s[i][15]])
            }

            // xor the two halves
            for (i in 0 until 8) {
                h[i] = h[i] xor v[i] xor v[i + 8]
            }
        }

        private inline fun G(v: LongArray, a: Int, b: Int, c: Int, d: Int, x: Long, y: Long) {
            var va = v[a]
            var vb = v[b]
            var vc = v[c]
            var vd = v[d]

            va = (va  +  vb + x)
            vd = (vd xor va    ).rotateRight(R1)
            vc = (vc  +  vd    )
            vb = (vb xor vc    ).rotateRight(R2)
            va = (va  +  vb + y)
            vd = (vd xor va    ).rotateRight(R3)
            vc = (vc  +  vd    )
            vb = (vb xor vc    ).rotateRight(R4)

            v[a] = va
            v[b] = vb
            v[c] = vc
            v[d] = vd
        }

        // h (64 byte block)
        private inline fun LongArray.initialize(): LongArray {
            val iv = IV
            val salt = salt
            val personalization = personalization

            // bytes 0-7
            this[0] = iv[0] xor (
                (
                    (digestLength()  ) or
                    (keyLength shl  8) or
                    (fanOut    shl 16) or
                    (depth     shl 24)
                ).toLong() or (leafLength.toLong() shl 32)
            )

            // bytes 8-15
            this[1] = iv[1] xor nodeOffset
            // bytes 16-17 (18-19 reserved)
            this[2] = iv[2] xor (nodeDepth or (innerLength shl 8)).toLong()
            // bytes 20-27 (reserved)
            this[3] = iv[3]

            // bytes 27-43
            if (salt == null) {
                this[4] = iv[4]
                this[5] = iv[5]
            } else {
                this[4] = iv[4] xor salt.leLongAt(0)
                this[5] = iv[5] xor salt.leLongAt(8)
            }

            // bytes 44-63
            if (personalization == null) {
                this[6] = iv[6]
                this[7] = iv[7]
            } else {
                this[6] = iv[6] xor personalization.leLongAt(0)
                this[7] = iv[7] xor personalization.leLongAt(8)
            }

            return this
        }

        protected final override fun resetProtected() {
            v.fill(0)
            h.initialize()
            m?.fill()
            m = null
            t.reset()
        }

        private companion object {
            private const val BLOCK_SIZE = 128
            private const val ROUND_COUNT = 12

            private const val R1 = 32
            private const val R2 = 24
            private const val R3 = 16
            private const val R4 = 63

            private val IV = longArrayOf(
                7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L,
                5840696475078001361L, -7276294671716946913L, 2270897969802886507L,  6620516959819538809L,
            )
        }
    }

    // BLAKE2Digest
    @Throws(InvalidKeyException::class, InvalidParameterException::class)
    private constructor(
        variant: String,
        blockSize: Int,
        bitStrength: Int,
        isLastNode: Boolean,
        keyLength: Int,
        fanOut: Int,
        depth: Int,
        leafLength: Int,
        nodeOffset: Long,
        nodeDepth: Int,
        innerLength: Int,
        salt: ByteArray?,
        personalization: ByteArray?,
    ): super(
        algorithm = "BLAKE2$variant-$bitStrength",
        blockSize = blockSize,
        digestLength = bitStrength / Byte.SIZE_BITS,
    ) {
        // s:  64 * 4 = 256
        // b: 128 * 4 = 512
        requireParam(bitStrength <= (blockSize * 4)) { "bitStrength must be less than or equal to ${blockSize * 4}" }
        requireParam(bitStrength >= Byte.SIZE_BITS) { "bitStrength must be greater than or equal to ${Byte.SIZE_BITS}" }
        requireParam(bitStrength % Byte.SIZE_BITS == 0) { "bitStrength must be a factor of ${Byte.SIZE_BITS}" }

        // s:  64 / 2 = 32
        // b: 128 / 2 = 64
        (blockSize / 2).let { size ->
            if (keyLength !in 0..size) {
                throw InvalidKeyException("keyLength must be between 0 and $size (inclusive)")
            }
            requireParam(innerLength in 0..size) { "innerLength must be between 0 and $size (inclusive)" }
        }

        // s:  64 / 8 = 8
        // b: 128 / 8 = 16
        (blockSize / Byte.SIZE_BITS).let { size ->
            if (salt != null) {
                requireParam(salt.size == size) { "salt must be exactly $size bytes" }
            }
            if (personalization != null) {
                requireParam(personalization.size == size) { "personalization must be exactly $size bytes" }
            }
        }

        requireParam(fanOut in 0..255) { "fanOut must be between 0 and 255 (inclusive)" }
        requireParam(depth in 1..255) { "depth must be between 1 and 255 (inclusive)" }
        requireParam(nodeDepth in 0..255) { "nodeDepth must be between 1 and 255 (inclusive)" }

        this.isLastNode = isLastNode
        this.keyLength = keyLength
        this.fanOut = fanOut
        this.depth = depth
        this.leafLength = leafLength
        this.nodeOffset = nodeOffset
        this.nodeDepth = nodeDepth
        this.innerLength = innerLength
        this.salt = salt?.copyOf()
        this.personalization = personalization?.copyOf()
    }

    @Suppress("UNUSED")
    private constructor(other: BLAKE2Digest): super(other) {
        this.isLastNode = other.isLastNode
        this.keyLength = other.keyLength
        this.fanOut = other.fanOut
        this.depth = other.depth
        this.leafLength = other.leafLength
        this.nodeOffset = other.nodeOffset
        this.nodeDepth = other.nodeDepth
        this.innerLength = other.innerLength
        this.salt = other.salt
        this.personalization = other.personalization
    }

    public abstract override fun copy(): BLAKE2Digest

    private companion object {
        private val SIGMA = run {
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
    }
}
