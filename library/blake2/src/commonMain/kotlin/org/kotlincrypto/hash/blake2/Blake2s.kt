package org.kotlincrypto.hash.blake2

import org.kotlincrypto.core.InternalKotlinCryptoApi
import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState
import org.kotlincrypto.endians.LittleEndian.Companion.bytesToInt
import org.kotlincrypto.endians.LittleEndian.Companion.toLittleEndian

/*
  The BLAKE2 cryptographic hash function was designed by Jean-
  Philippe Aumasson, Samuel Neves, Zooko Wilcox-O'Hearn, and Christian
  Winnerlein.
  Reference Implementation and Description can be found at: https://blake2.net/
  RFC: https://tools.ietf.org/html/rfc7693
  This implementation does not support the Tree Hashing Mode.
  For unkeyed hashing, developers adapting BLAKE2 to ASN.1 - based
  message formats SHOULD use the OID tree at x = 1.3.6.1.4.1.1722.12.2.
         Algorithm     | Target | Collision | Hash | Hash ASN.1 |
            Identifier |  Arch  |  Security |  nn  | OID Suffix |
        ---------------+--------+-----------+------+------------+
         id-blake2s128 | 32-bit |   2**64   |  16  |   x.2.4    |
         id-blake2s160 | 32-bit |   2**80   |  20  |   x.2.5    |
         id-blake2s224 | 32-bit |   2**112  |  28  |   x.2.7    |
         id-blake2s256 | 32-bit |   2**128  |  32  |   x.2.8    |
        ---------------+--------+-----------+------+------------+
 */

/**
 * Implementation of the cryptographic hash function BLAKE2s.
 *
 *
 * BLAKE2s offers a built-in keying mechanism to be used directly
 * for authentication ("Prefix-MAC") rather than a HMAC construction.
 *
 *
 * BLAKE2s offers a built-in support for a salt for randomized hashing
 * and a personal string for defining a unique hash function for each application.
 *
 *
 * BLAKE2s is optimized for 32-bit platforms and produces digests of any size
 * between 1 and 32 bytes.
 */
public class Blake2s : Digest {
    public companion object {
        // To use for Catenas H'
        private const val ROUNDS_IN_COMPRESS = 10

        // The size in bytes of the internal buffer the digest applies its compression
        private const val BLOCK_LENGTH_BYTES: Int = 64
        private const val MAX_DIGEST_BITS_LENGTH: Int = 256
        private const val ALGORITHM_NAME = "BLAKE2s"

        // BLAKE2s Initialization Vector:
        private val blake2s_IV = intArrayOf(
            0x6a09e667,
            -0x4498517b,
            0x3c6ef372,
            -0x5ab00ac6,
            0x510e527f,
            -0x64fa9774,
            0x1f83d9ab,
            0x5be0cd19
        )

        // Message word permutations:
        private val blake2s_sigma = arrayOf(
            byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
            byteArrayOf(14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3),
            byteArrayOf(11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4),
            byteArrayOf(7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8),
            byteArrayOf(9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13),
            byteArrayOf(2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9),
            byteArrayOf(12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11),
            byteArrayOf(13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10),
            byteArrayOf(6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5),
            byteArrayOf(10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0)
        )

        /**
         * Hash [input] as Blake2s-128 .
         * @return hashed [ByteArray] with size = 16.
         */
        public fun blake2sHash128(input: ByteArray): ByteArray {
            val blake2s = Blake2s(128)
            blake2s.update(input)
            return blake2s.digest()
        }
    }

    // Internal state, in the BLAKE2 paper it is called v
    private val internalState = IntArray(16)

    // State vector, in the BLAKE2 paper it is called h
    private var chainValue: IntArray? = null

    // holds last significant bits of counter (counts bytes)
    private var t0 = 0

    // finalization flag, for last block: ~0
    private var f0 = 0

    @OptIn(InternalKotlinCryptoApi::class)
    public constructor(digest: Blake2s) : super(ALGORITHM_NAME, BLOCK_LENGTH_BYTES, digest.digestLength()) {
        chainValue = digest.chainValue?.copyOf()
        t0 = digest.t0
        f0 = digest.f0
    }

    /**
     * BLAKE2s-256 for hashing.
     *
     * @param digestBits the desired digest length in bits. Must be a multiple of 8 and less than 256.
     */
    @OptIn(InternalKotlinCryptoApi::class)
    public constructor(digestBits: Int = MAX_DIGEST_BITS_LENGTH) : super(
        ALGORITHM_NAME,
        BLOCK_LENGTH_BYTES,
        digestBits / Byte.SIZE_BITS
    ) {
        require(!(digestBits < Byte.SIZE_BITS || digestBits > MAX_DIGEST_BITS_LENGTH || digestBits % Byte.SIZE_BITS != 0)) {
            "$ALGORITHM_NAME digest bit length must be a multiple of ${Byte.SIZE_BITS} and not greater than $MAX_DIGEST_BITS_LENGTH"
        }
        initChainValue()
    }

    override fun digest(bitLength: Long, bufferOffset: Int, buffer: ByteArray): ByteArray {
        val digestLength = digestLength()
        val out = ByteArray(digestLength)
        f0 = -0x1
        t0 = (bitLength / Byte.SIZE_BITS).toInt()

        compress(buffer, 0)
        internalState.fill(0)
        var i = 0
        val intSizeBytes = Int.SIZE_BYTES
        while (i < chainValue!!.size && i * intSizeBytes < digestLength) {
            val bytes = chainValue!![i].toLittleEndian()
            if (i * intSizeBytes < digestLength - intSizeBytes) {
                bytes.copyInto(out, i * intSizeBytes, 0, intSizeBytes)
            } else {
                bytes.copyInto(out, i * intSizeBytes, 0, digestLength - i * intSizeBytes)
            }
            i++
        }
        chainValue?.fill(0)
        reset()
        return out
    }

    override fun resetDigest() {
        f0 = 0
        t0 = 0
        chainValue = null
        initChainValue()
    }

    override fun copy(state: DigestState): Digest = Blake2s(this)

    override fun compress(input: ByteArray, offset: Int) {
        if (input.size > BLOCK_LENGTH_BYTES) t0 = BLOCK_LENGTH_BYTES
        initInternalState()
        val m = IntArray(16)
        for (j in 0..15) {
            var startIndex = offset + j * Int.SIZE_BYTES
            m[j] = bytesToInt(
                input[startIndex],
                input[++startIndex],
                input[++startIndex],
                input[++startIndex],
            )
        }
        for (round in 0 until ROUNDS_IN_COMPRESS) {
            // G apply to columns of internalState:
            // m[blake2s_sigma[round][2 * blockPos]] /+1
            g(m[blake2s_sigma[round][0].toInt()], m[blake2s_sigma[round][1].toInt()], 0, 4, 8, 12)
            g(m[blake2s_sigma[round][2].toInt()], m[blake2s_sigma[round][3].toInt()], 1, 5, 9, 13)
            g(m[blake2s_sigma[round][4].toInt()], m[blake2s_sigma[round][5].toInt()], 2, 6, 10, 14)
            g(m[blake2s_sigma[round][6].toInt()], m[blake2s_sigma[round][7].toInt()], 3, 7, 11, 15)
            // G apply to diagonals of internalState:
            g(m[blake2s_sigma[round][8].toInt()], m[blake2s_sigma[round][9].toInt()], 0, 5, 10, 15)
            g(m[blake2s_sigma[round][10].toInt()], m[blake2s_sigma[round][11].toInt()], 1, 6, 11, 12)
            g(m[blake2s_sigma[round][12].toInt()], m[blake2s_sigma[round][13].toInt()], 2, 7, 8, 13)
            g(m[blake2s_sigma[round][14].toInt()], m[blake2s_sigma[round][15].toInt()], 3, 4, 9, 14)
        }

        // update chain values:
        for (position in chainValue!!.indices) {
            chainValue!![position] = chainValue!![position] xor internalState[position] xor internalState[position + 8]
        }
    }

    private fun initChainValue() {
        if (chainValue == null) {
            chainValue = IntArray(8)
            blake2s_IV.copyInto(chainValue!!)
            chainValue!![0] = blake2s_IV[0] xor (digestLength() or 0x1010000)
        }
    }

    private fun initInternalState() {
        // initialize v:
        chainValue!!.copyInto(internalState, 0, 0, chainValue!!.size)
        blake2s_IV.copyInto(internalState, chainValue!!.size, 0, 4)

        internalState[12] = t0 xor blake2s_IV[4]
        internalState[13] = 0 xor blake2s_IV[5]
        internalState[14] = f0 xor blake2s_IV[6]
        internalState[15] = blake2s_IV[7]
    }

    private fun g(m1: Int, m2: Int, posA: Int, posB: Int, posC: Int, posD: Int) {
        internalState[posA] = internalState[posA] + internalState[posB] + m1
        internalState[posD] = (internalState[posD] xor internalState[posA]).rotateRight(16)
        internalState[posC] = internalState[posC] + internalState[posD]
        internalState[posB] = (internalState[posB] xor internalState[posC]).rotateRight(12)
        internalState[posA] = internalState[posA] + internalState[posB] + m2
        internalState[posD] = (internalState[posD] xor internalState[posA]).rotateRight(8)
        internalState[posC] = internalState[posC] + internalState[posD]
        internalState[posB] = (internalState[posB] xor internalState[posC]).rotateRight(7)
    }
}
