package org.kotlincrypto.hash.blake2b

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalStdlibApi
class Blake2bNotKeyTest {
    data class NotKeyTestVector(
        val input: String,
        val output: String,
    )

    companion object {
        private val notKeyTestVectors = listOf(
            // from: https://fossies.org/linux/john/src/rawBLAKE2_512_fmt_plug.c
            // digests without leading $BLAKE2$
            NotKeyTestVector(
                "blake2",
                "4245af08b46fbb290222ab8a68613621d92ce78577152d712467742417ebc1153668f1c9e1ec1e152a32a9c242dc686d175e087906377f0c483c5be2cb68953e",
            ),
            NotKeyTestVector(
                "hello world",
                "021ced8799296ceca557832ab941a50b4a11f83478cf141f51f933f653ab9fbcc05a037cddbed06e309bf334942c4e58cdf1a46e237911ccd7fcf9787cbc7fd0",
            ),
            NotKeyTestVector(
                "verystrongandlongpassword",
                "1f7d9b7c9a90f7bfc66e52b69f3b6c3befbd6aee11aac860e99347a495526f30c9e51f6b0db01c24825092a09dd1a15740f0ade8def87e60c15da487571bcef7",
            ),
            NotKeyTestVector(
                "The quick brown fox jumps over the lazy dog",
                "a8add4bdddfd93e4877d2746e62817b116364a1fa7bc148d95090bc7333b3673f82401cf7aa2e4cb1ecd90296e3f14cb5413f8ed77be73045b13914cdcd6a918",
            ),
            NotKeyTestVector(
                "",
                "786a02f742015903c6c6fd852552d272912f4740e15847618a86e217f71f5419d25e1031afee585313896444934eb04b903a685b1448b755d56f701afe9be2ce",
            ),
            NotKeyTestVector(
                "abc",
                "ba80a53f981c4d0d6a2797b69f12f6e94c212f14685ac4b74b12bb6fdbffa2d17d87c5392aab792dc252d5de4533cc9518d38aa8dbf1925ab92386edd4009923",
            ),
        )
    }

    @Test
    fun testNotKey() {
        val blake2bNotKey = Blake2b()
        for (testVector in notKeyTestVectors) {
            val input: ByteArray = testVector.input.encodeToByteArray()
            for (j in input.indices) {
                blake2bNotKey.update(input[j])
            }

            val notKeyHash = ByteArray(64)
            blake2bNotKey.doFinal(notKeyHash, 0)

            assertEquals(testVector.output, notKeyHash.toHexString())
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
            Blake2b(520)
        }.also {
            assertEquals("BLAKE2b digest bit length must be a multiple of 8 and not greater than 512", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2b(null, -1, null, null)
        }.also {
            assertEquals("Invalid digest length (required: 1 - 64)", it.message)
        }

        assertFailsWith<IllegalArgumentException> {
            Blake2b(null, 65, null, null)
        }.also {
            assertEquals("Invalid digest length (required: 1 - 64)", it.message)
        }
    }

    @Test
    fun testNullKeyVsNotKey() {
        val abc: ByteArray = "abc".encodeToByteArray()

        for (i in 1..63) {
            val dig1 = Blake2b(i * 8)
            val dig2 = Blake2b(null, i, null, null)

            val out1 = ByteArray(i)
            val out2 = ByteArray(i)

            dig1.update(abc, 0, abc.size)
            dig2.update(abc, 0, abc.size)

            dig1.doFinal(out1, 0)
            dig2.doFinal(out2, 0)

            assertContentEquals(out1, out2)
        }
    }

    @Test
    fun testReset() {
        // Generate a non-zero key
        val key = ByteArray(32)
        for (i in key.indices) {
            key[i] = i.toByte()
        }
        // Generate some non-zero input longer than the key
        val input = ByteArray(key.size + 1)
        for (i in input.indices) {
            input[i] = i.toByte()
        }
        // Hash the input
        val digest = Blake2b(key)
        digest.update(input, 0, input.size)
        val hash = ByteArray(digest.getDigestLength())
        digest.doFinal(hash, 0)
        // Using a second instance, hash the input without calling doFinal()
        val digest1 = Blake2b(key)
        digest1.update(input, 0, input.size)
        // Reset the second instance and hash the input again
        digest1.reset()
        digest1.update(input, 0, input.size)
        val hash1 = ByteArray(digest.getDigestLength())
        digest1.doFinal(hash1, 0)
        // The hashes should be identical
        assertContentEquals(hash1, hash)
    }
}
