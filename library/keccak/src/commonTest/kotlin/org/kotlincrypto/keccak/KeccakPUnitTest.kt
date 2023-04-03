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
package org.kotlincrypto.keccak

import io.matthewnelson.encoding.builders.Base16
import io.matthewnelson.encoding.core.Decoder.Companion.decodeToByteArray
import org.kotlincrypto.endians.BigEndian
import kotlin.test.Test
import kotlin.test.assertContentEquals

class KeccakPUnitTest {

    private fun <N: Number> assertKeccakP(state: State<N, *>, permutation1: List<N>, permutation2: List<N>) {
        KeccakP(state)
        printState(permutation1, state)
        assertContentEquals(permutation1, state.toList())

        KeccakP(state)
        printState(permutation2, state)
        assertContentEquals(permutation2, state.toList())
    }

    @Test
    fun givenKeccakP_whenF200_thenPermutationsAreAsExpected() {
        // https://github.com/XKCP/XKCP/blob/master/tests/TestVectors/KeccakF-200-IntermediateValues.txt
        val permutation1 = listOf(
            "3C", "28", "26", "84", "1C",
            "B3", "5C", "17", "1E", "AA",
            "E9", "B8", "11", "13", "4C",
            "EA", "A3", "85", "2C", "69",
            "D2", "C5", "AB", "AF", "EA",
        ).map { it.decodeToByteArray(BASE_16).first() }

        val permutation2 = listOf(
            "1B", "EF", "68", "94", "92",
            "A8", "A5", "43", "A5", "99",
            "9F", "DB", "83", "4E", "31",
            "66", "A1", "4B", "E8", "27",
            "D9", "50", "40", "47", "9E",
        ).map { it.decodeToByteArray(BASE_16).first() }

        assertKeccakP(F200(), permutation1, permutation2)
    }

    @Test
    fun givenKeccakP_whenF400_thenPermutationsAreAsExpected() {
        // https://github.com/XKCP/XKCP/blob/master/tests/TestVectors/KeccakF-400-IntermediateValues.txt
        val permutation1 = listOf(
            "09F5", "40AC", "0FA9", "14F5", "E89F",
            "ECA0", "5BD1", "7870", "EFF0", "BF8F",
            "0337", "6052", "DC75", "0EC9", "E776",
            "5246", "59A1", "5D81", "6D95", "6E14",
            "633E", "58EE", "71FF", "714C", "B38E",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1])
        }

        val permutation2 = listOf(
            "E537", "D5D6", "DBE7", "AAF3", "9BC7",
            "CA7D", "86B2", "FDEC", "692C", "4E5B",
            "67B1", "15AD", "A7F7", "A66F", "67FF",
            "3F8A", "2F99", "E2C2", "656B", "5F31",
            "5BA6", "CA29", "C224", "B85C", "097C",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1])
        }

        assertKeccakP(F400(), permutation1, permutation2)
    }

    @Test
    fun givenKeccakP_whenF800_thenPermutationsAreAsExpected() {
        // https://github.com/XKCP/XKCP/blob/master/tests/TestVectors/KeccakF-800-IntermediateValues.txt
        val permutation1 = listOf(
            "E531D45D", "F404C6FB", "23A0BF99", "F1F8452F", "51FFD042",
            "E539F578", "F00B80A7", "AF973664", "BF5AF34C", "227A2424",
            "88172715", "9F685884", "B15CD054", "1BF4FC0E", "6166FA91",
            "1A9E599A", "A3970A1F", "AB659687", "AFAB8D68", "E74B1015",
            "34001A98", "4119EFF3", "930A0E76", "87B28070", "11EFE996",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1], b[2], b[3])
        }

        val permutation2 = listOf(
            "75BF2D0D", "9B610E89", "C826AF40", "64CD84AB", "F905BDD6",
            "BC832835", "5F8001B9", "15662CCE", "8E38C95E", "701FE543",
            "1B544380", "89ACDEFF", "51EDB5DE", "0E9702D9", "6C19AA16",
            "A2913EEE", "60754E9A", "9819063C", "F4709254", "D09F9084",
            "772DA259", "1DB35DF7", "5AA60162", "358825D5", "B3783BAB",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1], b[2], b[3])
        }

        assertKeccakP(F800(), permutation1, permutation2)
    }

    @Test
    fun givenKeccakP_whenF1600_thenPermutationsAreAsExpected() {
        // https://github.com/XKCP/XKCP/blob/master/tests/TestVectors/KeccakF-1600-IntermediateValues.txt
        val permutation1 = listOf(
            "F1258F7940E1DDE7", "84D5CCF933C0478A", "D598261EA65AA9EE", "BD1547306F80494D", "8B284E056253D057",
            "FF97A42D7F8E6FD4", "90FEE5A0A44647C4", "8C5BDA0CD6192E76", "AD30A6F71B19059C", "30935AB7D08FFC64",
            "EB5AA93F2317D635", "A9A6E6260D712103", "81A57C16DBCF555F", "43B831CD0347C826", "01F22F1A11A5569F",
            "05E5635A21D9AE61", "64BEFEF28CC970F2", "613670957BC46611", "B87C5A554FD00ECB", "8C3EE88A1CCF32C8",
            "940C7922AE3A2614", "1841F924A2C509E4", "16F53526E70465C2", "75F644E97F30A13B", "EAF1FF7B5CECA249",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7])
        }

        val permutation2 = listOf(
            "2D5C954DF96ECB3C", "6A332CD07057B56D", "093D8D1270D76B6C", "8A20D9B25569D094", "4F9C4F99E5E7F156",
            "F957B9A2DA65FB38", "85773DAE1275AF0D", "FAF4F247C3D810F7", "1F1B9EE6F79A8759", "E4FECC0FEE98B425",
            "68CE61B6B9CE68A1", "DEEA66C4BA8F974F", "33C43D836EAFB1F5", "E00654042719DBD9", "7CF8A9F009831265",
            "FD5449A6BF174743", "97DDAD33D8994B40", "48EAD5FC5D0BE774", "E3B8C8EE55B7B03C", "91A0226E649E42E9",
            "900E3129E7BADD7B", "202A9EC5FAA3CCE8", "5B3402464E1C3DB6", "609F4E62A44C1059", "20D06CD26A8FBF5C",
        ).map {
            val b = it.decodeToByteArray(BASE_16)
            BigEndian.bytesTo(b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7])
        }

        assertKeccakP(F1600(), permutation1, permutation2)
    }

    private fun <N: Number> printState(
        expected: List<N>,
        state: State<N, *>,
        print: Boolean = false,
    ) {
        if (!print) return

        println("""
            STATE:    ${state::class.simpleName}
            EXPECTED: $expected
            ACTUAL:   ${state.toList()}
        """.trimIndent())
    }

    private companion object {
        private val BASE_16 = Base16 { encodeToLowercase = true }
    }
}
