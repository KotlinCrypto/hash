package org.kotlincrypto.hash.blake2b

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@ExperimentalStdlibApi
class Blake2bKeyedTest {
    data class KeyedTestVector(
        val input: String,
        val key: String,
        val output: String,
    )

    companion object {
        private val keyedTestVectors by lazy {
            listOf(
                // Vectors from BLAKE2 website: https://blake2.net/blake2b-test.txt
                KeyedTestVector(
                    "",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "10ebb67700b1868efb4417987acf4690ae9d972fb7a590c2f02871799aaa4786b5e996e8f0f4eb981fc214b005f42d2ff4233499391653df7aefcbc13fc51568"
                ),

                KeyedTestVector(
                    "00",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "961f6dd1e4dd30f63901690c512e78e4b45e4742ed197c3c5e45c549fd25f2e4187b0bc9fe30492b16b0d0bc4ef9b0f34c7003fac09a5ef1532e69430234cebd"
                ),

                KeyedTestVector(
                    "0001",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "da2cfbe2d8409a0f38026113884f84b50156371ae304c4430173d08a99d9fb1b983164a3770706d537f49e0c916d9f32b95cc37a95b99d857436f0232c88a965"
                ),

                KeyedTestVector(
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "f1aa2b044f8f0c638a3f362e677b5d891d6fd2ab0765f6ee1e4987de057ead357883d9b405b9d609eea1b869d97fb16d9b51017c553f3b93c0a1e0f1296fedcd"
                ),

                KeyedTestVector(
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f505152535455565758595a5b5c5d5e5f606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "c230f0802679cb33822ef8b3b21bf7a9a28942092901d7dac3760300831026cf354c9232df3e084d9903130c601f63c1f4a4a4b8106e468cd443bbe5a734f45f"
                ),

                KeyedTestVector(
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f505152535455565758595a5b5c5d5e5f606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeafb0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfe",
                    "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f",
                    "142709d62e28fcccd0af97fad0f8465b971e82201dc51070faa0372aa43e92484be1c1e73ba10906d5d1853db6a4106e0a7bf9800d373d6dee2d46d62ef2a461"
                )
            )
        }
    }

    @Test
    fun testKeyed() {
        val blake2bKeyed = Blake2b(keyedTestVectors[0].key.hexToByteArray())
        for (testVector in keyedTestVectors) {
            val input: ByteArray = testVector.input.hexToByteArray()

            blake2bKeyed.update(input, 0, input.size)
            val keyedHash = ByteArray(64)
            blake2bKeyed.doFinal(keyedHash, 0)

            assertEquals(testVector.output, keyedHash.toHexString())
            testOffset(blake2bKeyed, input, keyedHash)
        }
    }

    private fun testOffset(
        digest: Blake2b,
        input: ByteArray,
        expected: ByteArray
    ) {
        val resBuf = ByteArray(expected.size + 11)

        digest.update(input, 0, input.size)

        digest.doFinal(resBuf, 11)
        assertContentEquals(resBuf.copyOfRange(11, resBuf.size), expected)
    }

    @Test
    fun testClone() {
        var blake2bCloneSource = Blake2b(
            keyedTestVectors[3].key.hexToByteArray(),
            16,
            "000102030405060708090a0b0c0d0e0f".hexToByteArray(),
            "101112131415161718191a1b1c1d1e1f".hexToByteArray(),
        )
        var expected: ByteArray = "b6d48ed5771b17414c4e08bd8d8a3bc4".hexToByteArray()

        checkClone(blake2bCloneSource, expected)

        // just digest size
        blake2bCloneSource = Blake2b(160)
        expected = "64202454e538279b21cea0f5a7688be656f8f484".hexToByteArray()
        checkClone(blake2bCloneSource, expected)

        // null salt and personalisation
        blake2bCloneSource = Blake2b(
            keyedTestVectors[3].key.hexToByteArray(),
            16,
            null,
            null,
        )
        expected = "2b4a081fae2d7b488f5eed7e83e42a20".hexToByteArray()
        checkClone(blake2bCloneSource, expected)

        // null personalisation
        blake2bCloneSource = Blake2b(
            keyedTestVectors[3].key.hexToByteArray(), 16, "000102030405060708090a0b0c0d0e0f".hexToByteArray(), null
        )
        expected = "00c3a2a02fcb9f389857626e19d706f6".hexToByteArray()
        checkClone(blake2bCloneSource, expected)

        // null salt
        blake2bCloneSource = Blake2b(
            keyedTestVectors[3].key.hexToByteArray(),
            16, null, "101112131415161718191a1b1c1d1e1f".hexToByteArray(),
        )
        expected = "f445ec9c062a3c724f8fdef824417abb".hexToByteArray()
        checkClone(blake2bCloneSource, expected)
    }

    private fun checkClone(blake2bCloneSource: Blake2b, expected: ByteArray) {
        val message: ByteArray = keyedTestVectors[3].input.hexToByteArray()

        blake2bCloneSource.update(message)

        val hash = ByteArray(blake2bCloneSource.getDigestLength())

        val digClone = Blake2b(blake2bCloneSource)

        blake2bCloneSource.doFinal(hash, 0)
        assertContentEquals(expected, hash)

        digClone.doFinal(hash, 0)
        assertContentEquals(expected, hash)
    }
}
