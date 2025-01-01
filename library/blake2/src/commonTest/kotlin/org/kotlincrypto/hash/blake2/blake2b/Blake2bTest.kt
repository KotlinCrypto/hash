package org.kotlincrypto.hash.blake2.blake2b

import org.kotlincrypto.hash.blake2.Blake2b
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalStdlibApi
class Blake2bTest {
    data class TestVector(
        val input: String,
        val output: String,
    )

    companion object {
        private val testVectors by lazy {
            listOf(
                // from: https://fossies.org/linux/john/src/rawBLAKE2_512_fmt_plug.c
                // digests without leading $BLAKE2$
                TestVector(
                    "blake2",
                    "4245af08b46fbb290222ab8a68613621d92ce78577152d712467742417ebc1153668f1c9e1ec1e152a32a9c242dc686d175e087906377f0c483c5be2cb68953e",
                ),
                TestVector(
                    "hello world",
                    "021ced8799296ceca557832ab941a50b4a11f83478cf141f51f933f653ab9fbcc05a037cddbed06e309bf334942c4e58cdf1a46e237911ccd7fcf9787cbc7fd0",
                ),
                TestVector(
                    "verystrongandlongpassword",
                    "1f7d9b7c9a90f7bfc66e52b69f3b6c3befbd6aee11aac860e99347a495526f30c9e51f6b0db01c24825092a09dd1a15740f0ade8def87e60c15da487571bcef7",
                ),
                TestVector(
                    "The quick brown fox jumps over the lazy dog",
                    "a8add4bdddfd93e4877d2746e62817b116364a1fa7bc148d95090bc7333b3673f82401cf7aa2e4cb1ecd90296e3f14cb5413f8ed77be73045b13914cdcd6a918",
                ),
                TestVector(
                    "",
                    "786a02f742015903c6c6fd852552d272912f4740e15847618a86e217f71f5419d25e1031afee585313896444934eb04b903a685b1448b755d56f701afe9be2ce",
                ),
                TestVector(
                    "abc",
                    "ba80a53f981c4d0d6a2797b69f12f6e94c212f14685ac4b74b12bb6fdbffa2d17d87c5392aab792dc252d5de4533cc9518d38aa8dbf1925ab92386edd4009923",
                ),
                TestVector(
                    "UPPERCASE",
                    "da40d8f48e9e7560c56e2b92205aed6342a276994ca0287ea4f8c1423ef07d519ecb4bf8668c118379a36be8aa6c077bbc6213fa81fbb332fad9d8a19a7756e6"
                ),
                TestVector(
                    "123456789",
                    "f5ab8bafa6f2f72b431188ac38ae2de7bb618fb3d38b6cbf639defcdd5e10a86b22fccff571da37e42b23b80b657ee4d936478f582280a87d6dbb1da73f5c47d"
                ),
            )
        }
    }

    @Test
    fun testBlake2b() {
        val blake2b = Blake2b()
        for (testVector in testVectors) {
            val input: ByteArray = testVector.input.encodeToByteArray()
            for (j in input.indices) {
                blake2b.update(input[j])
            }

            val hash = blake2b.digest()

            assertEquals(testVector.output, hash.toHexString())
        }
    }

    @Test
    fun testLengthConstruction() {
        assertFailsWith<IllegalArgumentException> {
            Blake2b(-1)
        }.also {
            assertEquals("BLAKE2b digest bit length must be a multiple of 8 and not greater than 512", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2b(9)
        }.also {
            assertEquals("BLAKE2b digest bit length must be a multiple of 8 and not greater than 512", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2b(513)
        }.also {
            assertEquals("BLAKE2b digest bit length must be a multiple of 8 and not greater than 512", it.message)
        }
    }
}
