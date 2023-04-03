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
package org.kotlincrypto.endians

import org.kotlincrypto.endians.LittleEndian.Companion.toLittleEndian
import kotlin.test.Test
import kotlin.test.assertEquals

class LittleEndianUnitTest {

    companion object {
        private const val SM = Short.MAX_VALUE
        private const val IM = Int.MAX_VALUE
        private const val LM = Long.MAX_VALUE

        private const val S5: Short = 5
        private const val I5: Int = S5.toInt()
        private const val L5: Long = S5.toLong()
    }

    @Test
    fun givenLittleEndian_whenToByte_thenIsAsExpected() {
        assertEquals(SM.toByte(), SM.toLittleEndian().toByte())
        assertEquals(IM.toByte(), IM.toLittleEndian().toByte())
        assertEquals(LM.toByte(), LM.toLittleEndian().toByte())
    }

    @Test
    fun givenLittleEndian_whenToShort_thenIsAsExpected() {
        assertEquals(SM          , SM.toLittleEndian().toShort())
        assertEquals(IM.toShort(), IM.toLittleEndian().toShort())
        assertEquals(LM.toShort(), LM.toLittleEndian().toShort())
    }

    @Test
    fun givenLittleEndian_whenToInt_thenIsAsExpected() {
        assertEquals(SM.toInt(), SM.toLittleEndian().toInt())
        assertEquals(IM        , IM.toLittleEndian().toInt())
        assertEquals(LM.toInt(), LM.toLittleEndian().toInt())
    }

    @Test
    fun givenLittleEndian_whenToLong_thenIsAsExpected() {
        assertEquals(SM.toLong(), SM.toLittleEndian().toLong())
        assertEquals(IM.toLong(), IM.toLittleEndian().toLong())
        assertEquals(LM         , LM.toLittleEndian().toLong())
    }

    @Test
    fun givenLittleEndian_whenToBigEndian_thenByteOrderReversed() {
        assertEquals(SM, SM.toLittleEndian().toBigEndian().toShort())
        assertEquals(IM, IM.toLittleEndian().toBigEndian().toInt())
        assertEquals(LM, LM.toLittleEndian().toBigEndian().toLong())
    }

    @Test
    fun givenLittleEndian_whenPlusByteArray_thenIsAdded() {
        val aExpect: Byte = 1
        val bExpect: Byte = 2
        val a = ByteArray(5) { aExpect }
        val b = LittleEndian(bExpect, bExpect, bExpect, bExpect)
        val actual = a.plus(b)

        assertEquals(a.size + b.size, actual.size)

        for ((i, byte) in actual.withIndex()) {
            if (i in a.indices) {
                assertEquals(aExpect, byte)
            } else {
                assertEquals(bExpect, byte)
            }
        }
    }

    @Test
    fun givenByteArray_whenPlusLittleEndian_thenIsAdded() {
        val aExpect: Byte = 1
        val bExpect: Byte = 2
        val a = ByteArray(5) { aExpect }
        val b = LittleEndian(bExpect, bExpect, bExpect, bExpect)
        val actual = b + a

        assertEquals(b.size + a.size, actual.size)

        for ((i, byte) in actual.withIndex()) {
            if (i in 0 until b.size) {
                assertEquals(bExpect, byte)
            } else {
                assertEquals(aExpect, byte)
            }
        }
    }

    @Test
    fun givenLittleEndian_whenPlusLittleEndian_thenIsAdded() {
        assertOperation(S5, { it + it }, { it + it })
        assertOperation(I5, { it + it }, { it + it })
        assertOperation(L5, { it + it }, { it + it })
    }

    @Test
    fun givenLittleEndian_whenMinusLittleEndian_thenIsSubtracted() {
        assertOperation(S5, { it - it }, { it - it })
        assertOperation(I5, { it - it }, { it - it })
        assertOperation(L5, { it - it }, { it - it })
    }

    @Test
    fun givenLittleEndian_whenTimesLittleEndian_thenIsMultiplied() {
        assertOperation(S5, { it * it }, { it * it })
        assertOperation(I5, { it * it }, { it * it })
        assertOperation(L5, { it * it }, { it * it })
    }

    @Test
    fun givenLittleEndian_whenDivideLittleEndian_thenIsDivided() {
        assertOperation(S5, { it / it }, { it / it })
        assertOperation(I5, { it / it }, { it / it })
        assertOperation(L5, { it / it }, { it / it })
    }

    private inline fun <reified N: Number> assertOperation(
        number: N,
        expect: (N) -> Number,
        actual: (le: LittleEndian) -> LittleEndian
    ) {
        when (number) {
           is Short -> {
               val e = expect.invoke(number)
               val a = actual.invoke(number.toLittleEndian())
               assertEquals(e.toInt(), a.toInt())
               assertEquals(4, a.size)
           }
           is Int -> {
               val e = expect.invoke(number)
               val a = actual.invoke(number.toLittleEndian())
               assertEquals(e.toInt(), a.toInt())
               assertEquals(4, a.size)
           }
           is Long -> {
               val e = expect.invoke(number)
               val a = actual.invoke(number.toLittleEndian())
               assertEquals(e.toLong(), a.toLong())
               assertEquals(8, a.size)
           }
           else -> throw AssertionError("Type ${number::class.simpleName} not supported")
        }
    }
}
