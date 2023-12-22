package org.kotlincrypto.hash.siphash

public class SipHasherContainer(
    private val key: ByteArray
) {
    private val v0: Long
    private val v1: Long
    private val v2: Long
    private val v3: Long

    init {
        require(key.size == 16) { "Key must be exactly 16 bytes!" }
        val k0 = SipHasher.bytesToLong(key, 0)
        val k1 = SipHasher.bytesToLong(key, 8)

        v0 = SipHasher.INITIAL_V0 xor k0
        v1 = SipHasher.INITIAL_V1 xor k1
        v2 = SipHasher.INITIAL_V2 xor k0
        v3 = SipHasher.INITIAL_V3 xor k1
    }

    public fun hash(data: ByteArray): Long {
        return hash(data, SipHasher.DEFAULT_C, SipHasher.DEFAULT_D)
    }

    public fun hash(data: ByteArray, c: Int, d: Int): Long = SipHasher.hash(
        c, d,
        v0,
        v1,
        v2,
        v3,
        data
    )
}