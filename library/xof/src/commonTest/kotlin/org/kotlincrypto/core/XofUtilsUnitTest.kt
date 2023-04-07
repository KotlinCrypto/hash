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
package org.kotlincrypto.core

import kotlin.test.Test
import kotlin.test.assertContentEquals

@OptIn(InternalKotlinCryptoApi::class)
class XofUtilsUnitTest {

    @Test
    fun givenLeftEncoding_whenValueZero_thenResultIsAsExpected() {
        val expected = ByteArray(2).apply { this[0] = 1 }
        val actual = Xof.Utils.leftEncode(0L)
        assertContentEquals(expected, actual)
    }

    @Test
    fun givenRightEncoding_whenValueZero_thenResultIsAsExpected() {
        val expected = ByteArray(2).apply { this[1] = 1 }
        val actual = Xof.Utils.rightEncode(0L)
        assertContentEquals(expected, actual)
    }
}
