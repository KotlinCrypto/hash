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
package org.kotlincrypto.hash

import io.matthewnelson.encoding.base16.Base16
import io.matthewnelson.encoding.core.Encoder.Companion.encodeToString
import org.kotlincrypto.core.Resettable
import org.kotlincrypto.core.Updatable
import kotlin.test.assertEquals

abstract class XofUnitTest: HashUnitTest(), Resettable {
    abstract val xof: Updatable

    abstract val expectedPartialReadHash: String

    abstract fun read(vararg args: ByteArray)
    abstract fun partialRead(out: ByteArray, offset: Int, len: Int)

    open fun givenXof_whenReset_thenReadReturnsExected() {
        updateSmall(xof)
        reset()

        val r = Array(50) { i -> ByteArray(i) }
        read(*r)
        var b = r.first()
        for (i in 1 until r.size) {
            b += r[i]
        }

        val actual = b.encodeToString(base16)
        assertEquals(expectedResetHash, actual)
    }

    open fun givenXof_whenPartialRead_thenReadReturnsExpected() {
        updateSmall(xof)
        val r = ByteArray(200)
        partialRead(r, 10, r.size - 20)
        for (i in 0 until 10) {
            assertEquals(0, r[i])
        }
        for (i in (r.size - 10) until r.size) {
            assertEquals(0, r[i])
        }
        val actual = r.encodeToString(base16)
        assertEquals(expectedPartialReadHash, actual)
    }

    open fun givenXof_whenUpdatedSmall_thenReadReturnsExpected() {
        updateSmall(xof)
        val r1 = ByteArray(80)
        val r2 = ByteArray(500)
        read(r1, r2)
        val actual = (r1 + r2).encodeToString(base16)
        assertEquals(expectedUpdateSmallHash, actual)
    }

    open fun givenXof_whenUpdateMedium_thenReadReturnsExpected() {
        updateMedium(xof)
        val r1 = ByteArray(80)
        val r2 = ByteArray(500)
        val r3 = ByteArray(40)
        val r4 = ByteArray(300)
        read(r1, r2, r3, r4)
        val actual = (r1 + r2 + r3 + r4).encodeToString(base16)
        assertEquals(expectedUpdateMediumHash, actual)
    }

    private companion object {
        private val base16 = Base16 {
            encodeToLowercase = true
            lineBreakInterval = 64
        }
    }
}
