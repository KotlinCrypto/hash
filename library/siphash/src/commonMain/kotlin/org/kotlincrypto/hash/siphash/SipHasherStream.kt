package org.kotlincrypto.hash.siphash

import org.kotlincrypto.hash.siphash.SipHasher.Companion.INITIAL_V0
import org.kotlincrypto.hash.siphash.SipHasher.Companion.INITIAL_V1
import org.kotlincrypto.hash.siphash.SipHasher.Companion.INITIAL_V2
import org.kotlincrypto.hash.siphash.SipHasher.Companion.INITIAL_V3
import org.kotlincrypto.hash.siphash.SipHasher.Companion.bytesToLong
import org.kotlincrypto.hash.siphash.SipHasher.Companion.rotateLeft

/**
 * Initializes a streaming digest using a key and compression rounds.
 *
 * @param key
 * the key to use to seed this hash container.
 * @param c
 * the desired rounds of C compression.
 * @param d
 * the desired rounds of D compression.
 */
public class SipHasherStream(private val key: ByteArray, private val c: Int, private val d: Int) {

    /**
     * Counter to keep track of the input
     */
    private var len: Byte = 0

    /**
     * Index to keep track of chunk positioning.
     */
    private var m_idx = 0

    /**
     * The current value for the m number.
     */
    private var m: Long = 0

    /**
     * The current value for the this.v0 number.
     */
    private var v0: Long = 0

    /**
     * The current value for the this.v1 number.
     */
    private var v1: Long = 0

    /**
     * The current value for the this.v2 number.
     */
    private var v2: Long = 0

    /**
     * The current value for the this.v3 number.
     */
    private var v3: Long = 0

    init {
        require(key.size == 16) { "Key must be exactly 16 bytes!" }
        val k0: Long = bytesToLong(key, 0)
        val k1: Long = bytesToLong(key, 8)
        v0 = INITIAL_V0 xor k0
        v1 = INITIAL_V1 xor k1
        v2 = INITIAL_V2 xor k0
        v3 = INITIAL_V3 xor k1
        m = 0
        len = 0
        m_idx = 0
    }

    /**
     * Updates the hash with a single byte.
     *
     * This will only modify the internal `m` value, nothing will be modified
     * in the actual `v*` states until an 8-byte block has been provided.
     *
     * @param b
     * the byte being added to the digest.
     * @return
     * the same [SipHasherStream] for chaining.
     */
    public fun update(b: Byte): SipHasherStream {
        len++
        m = m or (b.toLong() and 0xffL shl m_idx++ * 8)
        if (m_idx < 8) {
            return this
        }
        v3 = v3 xor m
        for (i in 0 until c) {
            round()
        }
        v0 = v0 xor m
        m_idx = 0
        m = 0
        return this
    }

    /**
     * Updates the hash with an array of bytes.
     *
     * @param bytes
     * the bytes being added to the digest.
     * @return
     * the same [SipHasherStream] for chaining.
     */
    public fun update(bytes: ByteArray): SipHasherStream {
        for (b in bytes) {
            update(b)
        }
        return this
    }

    /**
     * Finalizes the digest and returns the hash.
     *
     * This works by padding to the next 8-byte block, before applying
     * the compression rounds once more - but this time using D rounds
     * of compression rather than C.
     *
     * @return
     * the final result of the hash as a long.
     */
    public fun digest(): Long {
        val msgLenMod256 = len
        while (m_idx < 7) {
            update(0.toByte())
        }
        update(msgLenMod256)
        v2 = v2 xor 0xffL
        for (i in 0 until d) {
            round()
        }
        return v0 xor v1 xor v2 xor v3
    }

    /**
     * SipRound implementation for internal use.
     */
    private fun round() {
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
    }
}