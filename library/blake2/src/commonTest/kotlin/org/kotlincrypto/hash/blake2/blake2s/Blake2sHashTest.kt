package org.kotlincrypto.hash.blake2.blake2s

import org.kotlincrypto.hash.blake2.Blake2s
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalStdlibApi
class Blake2sHashTest {
    data class TestVector(
        val input: String,
        val output: String,
    )

    companion object {
        private val blake2s128TestVectors by lazy {
            listOf(
                // from: https://github.com/appmattus/crypto/blob/main/cryptohash/src/commonTest/kotlin/com/appmattus/crypto/internal/Blake2s_128Test.kt
                TestVector(
                    "blake2",
                    "13212c0218c995a400ec9da5ee76ab0a"
                ),
                TestVector(
                    "hello world",
                    "37deae0226c30da2ab424a7b8ee14e83"
                ),
                TestVector(
                    "verystrongandlongpassword",
                    "f1a8e54c1008db40683e5afd8dad6535"
                ),
                TestVector(
                    "The quick brown fox jumps over the lazy dog",
                    "96fd07258925748a0d2fb1c8a1167a73"
                ),
                TestVector(
                    "",
                    "64550d6ffe2c0a01a14aba1eade0200c"
                ),
                TestVector(
                    "abc",
                    "aa4938119b1dc7b87cbad0ffd200d0ae"
                ),
                TestVector(
                    "UPPERCASE",
                    "c509c829bc8319d5ea8e5ebf7aa743ca"
                ),
                TestVector(
                    "123456789",
                    "dce1c41568c6aa166e2f8eafce34e617"
                ),
            )
        }
    }

    @Test
    fun testBlake2sHash128() {
        for (testVector in blake2s128TestVectors) {
            val result = Blake2s.blake2sHash128(testVector.input.encodeToByteArray())
            assertEquals(16, result.size)
            assertEquals(testVector.output, result.toHexString())
        }
    }
}
