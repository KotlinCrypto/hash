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
@file:Suppress("ClassName")

package org.kotlincrypto.hash.siphash

import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.fail

open class SipHasherTest {

    // test vectors via https://www.131002.net/siphash/siphash24.c
    private val EXPECTED = ulongArrayOf(
        0x726fdb47dd0e0e31U, 0x74f839c593dc67fdU, 0x0d6c8009d9a94f5aU, 0x85676696d7fb7e2dU,
        0xcf2794e0277187b7U, 0x18765564cd99a68dU, 0xcbc9466e58fee3ceU, 0xab0200f58b01d137U,
        0x93f5f5799a932462U, 0x9e0082df0ba9e4b0U, 0x7a5dbbc594ddb9f3U, 0xf4b32f46226bada7U,
        0x751e8fbc860ee5fbU, 0x14ea5627c0843d90U, 0xf723ca908e7af2eeU, 0xa129ca6149be45e5U,
        0x3f2acc7f57c29bdbU, 0x699ae9f52cbe4794U, 0x4bc1b3f0968dd39cU, 0xbb6dc91da77961bdU,
        0xbed65cf21aa2ee98U, 0xd0f2cbb02e3b67c7U, 0x93536795e3a33e88U, 0xa80c038ccd5ccec8U,
        0xb8ad50c6f649af94U, 0xbce192de8a85b8eaU, 0x17d835b85bbb15f3U, 0x2f2e6163076bcfadU,
        0xde4daaaca71dc9a5U, 0xa6a2506687956571U, 0xad87a3535c49ef28U, 0x32d892fad841c342U,
        0x7127512f72f27cceU, 0xa7f32346f95978e3U, 0x12e0b01abb051238U, 0x15e034d40fa197aeU,
        0x314dffbe0815a3b4U, 0x027990f029623981U, 0xcadcd4e59ef40c4dU, 0x9abfd8766a33735cU,
        0x0e3ea96b5304a7d0U, 0xad0c42d6fc585992U, 0x187306c89bc215a9U, 0xd4a60abcf3792b95U,
        0xf935451de4f21df2U, 0xa9538f0419755787U, 0xdb9acddff56ca510U, 0xd06c98cd5c0975ebU,
        0xe612a3cb9ecba951U, 0xc766e62cfcadaf96U, 0xee64435a9752fe72U, 0xa192d576b245165aU,
        0x0a8787bf8ecb74b2U, 0x81b3e73d20b49b6fU, 0x7fa8220ba3b2eceaU, 0x245731c13ca42499U,
        0xb78dbfaf3a8d83bdU, 0xea1ad565322a1a0bU, 0x60e61c23a3795013U, 0x6606d7e446282b93U,
        0x6ca4ecb15c5f91e1U, 0x9f626da15c9625f3U, 0xe51b38608ef25f57U, 0x958a324ceb064572U
    )

    @Test
    fun testVectorsForZeroAllocHash() {
        testVectors { key, data ->
            SipHasher.hash(key, data)
        }
    }

    /**
     * Tests conversion of hashes to hexidecimal.
     *
     * This makes sure to validate results which may need padding
     * to the left with 0s in order to fill to 16 bytes.
     */
    @Test
    fun testConversionToHexString() {
        val hash1 = -3891084581787974112L
        val hash2 = 77813817455948350L
        val hex1: String = SipHasher.toHexString(hash1)
        val hex2: String = SipHasher.toHexString(hash2)
        assertEquals("err", hex1, "ca0017304f874620")
        assertEquals("err", hex2, "011473413414323e")
    }

    /**
     * Tests invalid key exceptions are thrown.
     */
    @Test
    fun testExceptionOnInvalidKey() {
        try {
            SipHasher.hash(ByteArray(0), ByteArray(0))
            fail("IllegalArgumentException expected")
        } catch (err: IllegalArgumentException) {

        }
    }

    protected fun testVectors(hasher: Hasher) {
        val key = ByteArray(16) { it.toByte() }
        EXPECTED.forEachIndexed { index, expected ->
            val data = ByteArray(index) { it.toByte() }
            val result = hasher.hash(key, data)
            assertEquals("Failed $index $expected", result, expected.toLong())
        }
    }

    fun interface Hasher {
        /**
         * Given a key and input data, return a hash.
         *
         * @param key
         * the key to seed the hash with.
         * @param data
         * the data being hashed.
         * @return
         * a long representation of a hash.
         */
        fun hash(key: ByteArray, data: ByteArray): Long
    }
}
