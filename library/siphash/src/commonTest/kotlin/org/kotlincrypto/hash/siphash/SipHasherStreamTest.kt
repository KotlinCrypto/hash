package org.kotlincrypto.hash.siphash

import kotlin.test.Test
import kotlin.test.fail

class SipHasherStreamTest : SipHasherTest() {

    @Test
    fun testStreamExceptionOnInvalidKey() {
        try {
            SipHasher.init(ByteArray(0))
            fail("Should throw exception")
        } catch (err: IllegalArgumentException) { }
    }

    /**
     * Tests all vectors using the streaming hash implementation.
     */
    @Test
    fun testVectorsForStreamHash() {
        testVectors { key, data ->
            SipHasher.init(key).update(data).digest()
        }
    }
}