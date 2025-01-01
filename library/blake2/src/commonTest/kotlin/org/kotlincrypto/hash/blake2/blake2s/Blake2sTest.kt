package org.kotlincrypto.hash.blake2.blake2s

import org.kotlincrypto.hash.blake2.Blake2s
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalStdlibApi
class Blake2sTest {
    data class TestVector(
        val input: String,
        val output: String,
    )

    companion object {
        private val testVectors by lazy {
            listOf(
                // from: https://github.com/appmattus/crypto/blob/main/cryptohash/src/commonTest/kotlin/com/appmattus/crypto/internal/Blake2s_256Test.kt
                TestVector(
                    "blake2",
                    "03ff98699d53d8c2680f98e2557bd96c2e4e1f4610fedabba50c266d0988c74b"
                ),
                TestVector(
                    "hello world",
                    "9aec6806794561107e594b1f6a8a6b0c92a0cba9acf5e5e93cca06f781813b0b"
                ),
                TestVector(
                    "verystrongandlongpassword",
                    "d49abeeced4a85ee685a98a29a5ff3a46ad41bfdf6b8e5088716699a30c52265"
                ),
                TestVector(
                    "The quick brown fox jumps over the lazy dog",
                    "606beeec743ccbeff6cbcdf5d5302aa855c256c29b88c8ed331ea1a6bf3c8812"
                ),
                TestVector(
                    "",
                    "69217a3079908094e11121d042354a7c1f55b6482ca1a51e1b250dfd1ed0eef9"
                ),
                TestVector(
                    "abc",
                    "508c5e8c327c14e2e1a72ba34eeb452f37458b209ed63a294d999b4c86675982"
                ),
                TestVector(
                    "UPPERCASE",
                    "8939a0dff88b336033bedf5da5ca536984c4e4865dc5d6ecea17e6c7e8df212a"
                ),
                TestVector(
                    "123456789",
                    "7acc2dd21a2909140507f37396acce906864b5f118dfa766b107962b7a82a0d4"
                ),
            )
        }
    }

    @Test
    fun testBlake2s() {
        val blake2s = Blake2s()
        for (testVector in testVectors) {
            val input: ByteArray = testVector.input.encodeToByteArray()
            for (j in input.indices) {
                blake2s.update(input[j])
            }

            val hash = blake2s.digest()

            assertEquals(testVector.output, hash.toHexString())
        }
    }

    @Test
    fun testLengthConstruction() {
        assertFailsWith<IllegalArgumentException> {
            Blake2s(-1)
        }.also {
            assertEquals("BLAKE2s digest bit length must be a multiple of 8 and not greater than 256", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2s(9)
        }.also {
            assertEquals("BLAKE2s digest bit length must be a multiple of 8 and not greater than 256", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2s(257)
        }.also {
            assertEquals("BLAKE2s digest bit length must be a multiple of 8 and not greater than 256", it.message)
        }
    }
}
