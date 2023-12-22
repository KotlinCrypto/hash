package org.kotlincrypto.hash.siphash

import kotlin.test.Test
import kotlin.test.fail

class SipHasherContainerTest : SipHasherTest() {
    @Test
    fun testContainerExceptionOnInvalidKey() {
        try {
            SipHasher.container(byteArrayOf()).hash(byteArrayOf())
            fail("Should throw exception")
        } catch (err: IllegalArgumentException) { }
    }

    @Test
    fun testVectorsForContainerHash() {
        testVectors { key, data ->
            SipHasher.container(key).hash(data)
        }
    }
}