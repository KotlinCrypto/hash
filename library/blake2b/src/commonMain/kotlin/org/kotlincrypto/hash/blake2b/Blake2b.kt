package org.kotlincrypto.hash.blake2b

/*  The BLAKE2 cryptographic hash function was designed by Jean-
 Philippe Aumasson, Samuel Neves, Zooko Wilcox-O'Hearn, and Christian
 Winnerlein.

 Reference Implementation and Description can be found at: https://blake2.net/
 Internet Draft: https://tools.ietf.org/html/draft-saarinen-blake2-02

 This implementation does not support the Tree Hashing Mode.

   For unkeyed hashing, developers adapting BLAKE2 to ASN.1 - based
   message formats SHOULD use the OID tree at x = 1.3.6.1.4.1.1722.12.2.

         Algorithm     | Target | Collision | Hash | Hash ASN.1 |
            Identifier |  Arch  |  Security |  nn  | OID Suffix |
        ---------------+--------+-----------+------+------------+
         id-blake2b160 | 64-bit |   2**80   |  20  |   x.1.20   |
         id-blake2b256 | 64-bit |   2**128  |  32  |   x.1.32   |
         id-blake2b384 | 64-bit |   2**192  |  48  |   x.1.48   |
         id-blake2b512 | 64-bit |   2**256  |  64  |   x.1.64   |
        ---------------+--------+-----------+------+------------+
 */

/**
 * Implementation of the cryptographic hash function Blakbe2b.
 * <p>
 * Blake2b offers a built-in keying mechanism to be used directly
 * for authentication ("Prefix-MAC") rather than a HMAC construction.
 * <p>
 * Blake2b offers a built-in support for a salt for randomized hashing
 * and a personal string for defining a unique hash function for each application.
 * <p>
 * BLAKE2b is optimized for 64-bit platforms and produces digests of any size
 * between 1 and 64 bytes.
 */

public class Blake2b {
    public companion object {
        // To use for Catenas H'
        private const val ROUNDS_IN_COMPRESS = 12

        // The size in bytes of the internal buffer the digest applies its compression
        private const val BLOCK_LENGTH_BYTES: Int = 128

        // Blake2b Initialization Vector:
        // Produced from the square root of primes 2, 3, 5, 7, 11, 13, 17, 19.
        // The same as SHA-512 IV.
        private val blake2b_IV = longArrayOf(
            0x6a09e667f3bcc908L,
            -0x4498517a7b3558c5L,
            0x3c6ef372fe94f82bL,
            -0x5ab00ac5a0e2c90fL,
            0x510e527fade682d1L,
            -0x64fa9773d4c193e1L,
            0x1f83d9abfb41bd6bL,
            0x5be0cd19137e2179L
        )

        // Message word permutations:
        private val blake2b_sigma = arrayOf(
            byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
            byteArrayOf(14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3),
            byteArrayOf(11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4),
            byteArrayOf(7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8),
            byteArrayOf(9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13),
            byteArrayOf(2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9),
            byteArrayOf(12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11),
            byteArrayOf(13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10),
            byteArrayOf(6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5),
            byteArrayOf(10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0),
            byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
            byteArrayOf(14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3)
        )

        /**
         * Hash [input] as Blake2B-160 .
         * @return hashed [ByteArray] with size=20.
         */
        public fun blake2bHash160(input: ByteArray): ByteArray {
            val blake2b = Blake2b(160)
            blake2b.update(input)
            val result = ByteArray(blake2b.digestLength)
            blake2b.doFinal(result, 0)
            return result
        }

        /**
         * Hash [input] as Blake2B-224 .
         * @return hashed [ByteArray] with size=28.
         */
        public fun blake2bHash224(input: ByteArray): ByteArray {
            val blake2b = Blake2b(224)
            blake2b.update(input)
            val result = ByteArray(blake2b.digestLength)
            blake2b.doFinal(result, 0)
            return result
        }

        /**
         * Hash [input] as Blake2B-256 .
         * @return hashed [ByteArray] with size=32.
         */
        public fun blake2bHash256(input: ByteArray): ByteArray {
            val blake2b = Blake2b(256)
            blake2b.update(input)
            val result = ByteArray(blake2b.digestLength)
            blake2b.doFinal(result, 0)
            return result
        }
    }

    // General parameters:
    private var digestLength = 64 // 1- 64 bytes
    private var keyLength = 0 // 0 - 64 bytes for keyed hashing for MAC
    private var salt: ByteArray? = null // new byte[16];
    private var personalization: ByteArray? = null // new byte[16];

    // the key
    private var key: ByteArray? = null

    // whenever this buffer overflows, it will be processed
    // in the compress() function.
    // For performance issues, long messages will not use this buffer.
    private var buffer: ByteArray? = null // new byte[BLOCK_LENGTH_BYTES];

    // Position of last inserted byte:
    private var bufferPos = 0 // a value from 0 up to 128

    // In the Blake2b paper it is called: v
    private val internalState = LongArray(16)

    // State vector, in the Blake2b paper it is called: h
    private var chainValue: LongArray? = null

    // holds last significant bits, counter (counts bytes)
    private var t0 = 0L

    // counter: Length up to 2^128 are supported
    private var t1 = 0L

    // finalization flag, for last block: ~0L
    private var f0 = 0L

    public constructor(digest: Blake2b) {
        bufferPos = digest.bufferPos
        buffer = digest.buffer?.copyOf()
        keyLength = digest.keyLength
        key = digest.key?.copyOf()
        digestLength = digest.digestLength
        chainValue = digest.chainValue?.copyOf()
        personalization = digest.personalization?.copyOf()
        salt = digest.salt?.copyOf()
        t0 = digest.t0
        t1 = digest.t1
        f0 = digest.f0
    }

    /**
     * Basic sized constructor - size in bits.
     *
     * @param digestSize size of digest (in bits)
     */
    public constructor(digestSize: Int = 512) {
        require(!(digestSize < 8 || digestSize > 512 || digestSize % 8 != 0)) {
            "BLAKE2b digest bit length must be a multiple of 8 and not greater than 512"
        }
        buffer = ByteArray(BLOCK_LENGTH_BYTES)
        keyLength = 0
        this.digestLength = digestSize / 8
        initChainValue()
    }

    /**
     * Blake2b for authentication ("Prefix-MAC mode").
     * After calling the doFinal() method, the key will
     * remain to be used for further computations of
     * this instance.
     * The key can be overwritten using the clearKey() method.
     *
     * @param key A key up to 64 bytes or null
     */
    public constructor(key: ByteArray?) {
        buffer = ByteArray(BLOCK_LENGTH_BYTES)
        if (key != null) {
            this.key = key.copyInto(ByteArray(key.size), 0, 0, key.size)
            require(key.size <= 64) { "Keys > 64 are not supported" }
            keyLength = key.size
            key.copyInto(buffer!!, 0, 0, key.size)
            bufferPos = BLOCK_LENGTH_BYTES // zero padding
        }
        digestLength = 64
        initChainValue()
    }

    /**
     * Blake2b with key, required digest length (in bytes), salt and personalization.
     * After calling the doFinal() method, the key, the salt and the personal string
     * will remain and might be used for further computations with this instance.
     * The key can be overwritten using the clearKey() method, the salt (pepper)
     * can be overwritten using the clearSalt() method.
     *
     * @param key             A key up to 64 bytes or null
     * @param digestLength    from 1 up to 64 bytes
     * @param salt            16 bytes or null
     * @param personalization 16 bytes or null
     */
    public constructor(
        key: ByteArray?,
        digestLength: Int,
        salt: ByteArray?,
        personalization: ByteArray?,
    ) {
        require(digestLength in 1..64) {
            "Invalid digest length (required: 1 - 64)"
        }
        this.digestLength = digestLength

        buffer = ByteArray(BLOCK_LENGTH_BYTES)
        if (salt != null) {
            require(salt.size == 16) { "salt length must be exactly 16 bytes" }
            this.salt = salt.copyInto(ByteArray(16), 0, 0, salt.size)
        }
        if (personalization != null) {
            require(personalization.size == 16) { "personalization length must be exactly 16 bytes" }
            this.personalization = personalization.copyInto(ByteArray(16), 0, 0, personalization.size)
        }
        if (key != null) {
            this.key = key.copyInto(ByteArray(key.size), 0, 0, key.size)
            require(key.size <= 64) { "Keys > 64 are not supported" }
            keyLength = key.size
            key.copyInto(buffer!!, 0, 0, key.size)
            bufferPos = BLOCK_LENGTH_BYTES // zero padding
        }
        initChainValue()
    }

    /**
     * Get digest length (in bytes) which range from 1 to 64
     */
    public fun getDigestLength(): Int = digestLength

    /**
     * update the message digest with a single byte.
     *
     * @param input the input byte to be entered.
     */
    public fun update(input: Byte) {
        // process the buffer if full else add to buffer:
        val remainingLength = BLOCK_LENGTH_BYTES - bufferPos
        if (remainingLength == 0) {
            // full buffer
            t0 += BLOCK_LENGTH_BYTES.toLong()
            if (t0 == 0L) { // if message > 2^64
                t1++
            }
            compress(buffer!!, 0)
            buffer!!.fill(0) // clear buffer
            buffer!![0] = input
            bufferPos = 1
        } else {
            buffer!![bufferPos] = input
            bufferPos++
            return
        }
    }

    /**
     * update the message digest with a block of bytes.
     *
     * @param input the byte array containing the data.
     * @param offset  the offset into the byte array where the data starts.
     * @param length     the length of the data.
     */
    public fun update(
        input: ByteArray,
        offset: Int = 0,
        length: Int = input.size
    ) {
        if (length == 0) {
            return
        }
        var remainingLength = 0 // left bytes of buffer

        if (bufferPos != 0) {
            // commenced, incomplete buffer
            // complete the buffer:
            remainingLength = BLOCK_LENGTH_BYTES - bufferPos
            if (remainingLength < length) { // full buffer + at least 1 byte
                input.copyInto(buffer!!, bufferPos, offset, offset + remainingLength)
                t0 += BLOCK_LENGTH_BYTES.toLong()
                if (t0 == 0L) { // if message > 2^64
                    t1++
                }
                compress(buffer!!, 0)
                bufferPos = 0
                // clear buffer
                buffer?.fill(0)
            } else {
                input.copyInto(buffer!!, bufferPos, offset, offset + length)
                bufferPos += length
                return
            }
        }

        // process blocks except last block (also if last block is full)
        val blockWiseLastPos = offset + length - BLOCK_LENGTH_BYTES
        var messagePos: Int = offset + remainingLength
        while (messagePos < blockWiseLastPos) {
            // block wise 128 bytes
            // without buffer:
            t0 += BLOCK_LENGTH_BYTES.toLong()
            if (t0 == 0L) {
                t1++
            }
            compress(input, messagePos)
            messagePos += BLOCK_LENGTH_BYTES
        }

        // fill the buffer with left bytes, this might be a full block
        input.copyInto(buffer!!, 0, messagePos, offset + length)
        bufferPos += offset + length - messagePos
    }

    /**
     * close the digest, producing the final digest value. The doFinal
     * call leaves the digest reset.
     * Key, salt and personal string remain.
     *
     * @param out       the array the digest is to be copied into.
     * @param outOffset the offset into the out array the digest is to start at.
     */
    public fun doFinal(out: ByteArray?, outOffset: Int): Int {
        f0 = -0x1L
        t0 += bufferPos.toLong()
        if (bufferPos > 0 && t0 == 0L) {
            t1++
        }
        compress(buffer!!, 0)
        buffer?.fill(0) // Holds eventually the key if input is null
        internalState.fill(0L)
        var i = 0
        while (i < chainValue!!.size && i * 8 < digestLength) {
            val bytes = ByteArray(8)
            encodeLELong(chainValue!![i], bytes, 0)
            if (i * 8 < digestLength - 8) {
                bytes.copyInto(out!!, outOffset + i * 8, 0, 8)
            } else {
                bytes.copyInto(out!!, outOffset + i * 8, 0, digestLength - i * 8)
            }
            i++
        }
        chainValue?.fill(0L)
        reset()
        return digestLength
    }

    /**
     * Reset the digest back to it's initial state.
     * The key, the salt and the personal string will
     * remain for further computations.
     */
    public fun reset() {
        bufferPos = 0
        f0 = 0L
        t0 = 0L
        t1 = 0L
        chainValue = null
        buffer?.fill(0)
        if (key != null) {
            key!!.copyInto(buffer!!, 0, 0, key!!.size)
            bufferPos = BLOCK_LENGTH_BYTES // zero padding
        }
        initChainValue()
    }

    /**
     * Overwrite the key
     * if it is no longer used (zeroization)
     */
    public fun clearKey() {
        if (key != null) {
            key?.fill(0)
            buffer?.fill(0)
        }
    }

    /**
     * Overwrite the salt (pepper) if it
     * is secret and no longer used (zeroization)
     */
    public fun clearSalt() {
        if (salt != null) {
            salt?.fill(0)
        }
    }

    private fun compress(input: ByteArray, offset: Int) {
        initInternalState()
        val m = LongArray(16)
        for (j in 0..15) {
            m[j] = decodeLELong(input, offset + j * 8)
        }
        for (round in 0 until ROUNDS_IN_COMPRESS) {
            // G apply to columns of internalState
            // :m[blake2b_sigma[round][2 * blockPos]] /+1
            g(m[blake2b_sigma[round][0].toInt()], m[blake2b_sigma[round][1].toInt()], 0, 4, 8, 12)
            g(m[blake2b_sigma[round][2].toInt()], m[blake2b_sigma[round][3].toInt()], 1, 5, 9, 13)
            g(m[blake2b_sigma[round][4].toInt()], m[blake2b_sigma[round][5].toInt()], 2, 6, 10, 14)
            g(m[blake2b_sigma[round][6].toInt()], m[blake2b_sigma[round][7].toInt()], 3, 7, 11, 15)
            // G apply to diagonals of internalState:
            g(m[blake2b_sigma[round][8].toInt()], m[blake2b_sigma[round][9].toInt()], 0, 5, 10, 15)
            g(m[blake2b_sigma[round][10].toInt()], m[blake2b_sigma[round][11].toInt()], 1, 6, 11, 12)
            g(m[blake2b_sigma[round][12].toInt()], m[blake2b_sigma[round][13].toInt()], 2, 7, 8, 13)
            g(m[blake2b_sigma[round][14].toInt()], m[blake2b_sigma[round][15].toInt()], 3, 4, 9, 14)
        }

        // update chain values:
        for (position in chainValue!!.indices) {
            chainValue!![position] = chainValue!![position] xor internalState[position] xor internalState[position + 8]
        }
    }

    private fun initChainValue() {
        if (chainValue == null) {
            chainValue = LongArray(8)
            blake2b_IV.copyInto(chainValue!!)
            chainValue!![0] = (blake2b_IV[0] xor (digestLength.toLong() or (keyLength.toLong() shl 8) or 0x1010000L))
            if (salt != null) {
                chainValue!![4] = chainValue!![4] xor decodeLELong(salt!!, 0)
                chainValue!![5] = chainValue!![5] xor decodeLELong(salt!!, 8)
            }
            if (personalization != null) {
                chainValue!![6] = chainValue!![6] xor decodeLELong(personalization!!, 0)
                chainValue!![7] = chainValue!![7] xor decodeLELong(personalization!!, 8)
            }
        }
    }

    private fun initInternalState() {
        // initialize v:
        chainValue!!.copyInto(internalState, 0, 0, chainValue!!.size)
        blake2b_IV.copyInto(internalState, chainValue!!.size, 0, 4)

        internalState[12] = t0 xor blake2b_IV[4]
        internalState[13] = t1 xor blake2b_IV[5]
        internalState[14] = f0 xor blake2b_IV[6]
        internalState[15] = blake2b_IV[7] // ^ f1 with f1 = 0
    }

    private fun g(m1: Long, m2: Long, posA: Int, posB: Int, posC: Int, posD: Int) {
        internalState[posA] = internalState[posA] + internalState[posB] + m1
        internalState[posD] = (internalState[posD] xor internalState[posA]).rotateRight(32)
        internalState[posC] = internalState[posC] + internalState[posD]
        internalState[posB] = (internalState[posB] xor internalState[posC]).rotateRight(24) // replaces 25 of BLAKE
        internalState[posA] = internalState[posA] + internalState[posB] + m2
        internalState[posD] = (internalState[posD] xor internalState[posA]).rotateRight(16)
        internalState[posC] = internalState[posC] + internalState[posD]
        internalState[posB] = (internalState[posB] xor internalState[posC]).rotateRight(63) // replaces 11 of BLAKE
    }

    /**
     * Decode a 64-bit little-endian integer.
     *
     * @param buf   the source buffer
     * @param off   the source offset
     * @return the decoded integer
     */
    private inline fun decodeLELong(buf: ByteArray, off: Int): Long {
        return buf[off + 0].toLong() and 0xFF or
                ((buf[off + 1].toLong() and 0xFF) shl 8) or
                ((buf[off + 2].toLong() and 0xFF) shl 16) or
                ((buf[off + 3].toLong() and 0xFF) shl 24) or
                ((buf[off + 4].toLong() and 0xFF) shl 32) or
                ((buf[off + 5].toLong() and 0xFF) shl 40) or
                ((buf[off + 6].toLong() and 0xFF) shl 48) or
                ((buf[off + 7].toLong() and 0xFF) shl 56)
    }

    /**
     * Encode a 64-bit integer with little-endian convention.
     *
     * @param [value]   the integer to encode
     * @param dst   the destination buffer
     * @param off   the destination offset
     */
    private fun encodeLELong(value: Long, dst: ByteArray, off: Int) {
        dst[off + 0] = value.toByte()
        dst[off + 1] = (value.toInt() ushr 8).toByte()
        dst[off + 2] = (value.toInt() ushr 16).toByte()
        dst[off + 3] = (value.toInt() ushr 24).toByte()
        dst[off + 4] = (value ushr 32).toByte()
        dst[off + 5] = (value ushr 40).toByte()
        dst[off + 6] = (value ushr 48).toByte()
        dst[off + 7] = (value ushr 56).toByte()
    }
}
