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

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.xor

public class F400: State<Short, F400> {
    public constructor(): super(roundCount = 20, state = Array(PLEN) { 0 })
    private constructor(state: F400): super(state.roundCount, state.state.copyOf())
    public override fun copy(): F400 = F400(this)

    internal override fun <T: Any?> withContext(block: Context<Short>.() -> T): T = block(F400Context)

    private object F400Context: Context<Short> {
        private const val MASK: Int = 0xffff

        override fun and(a: Short, other: Short): Short = a and other
        override fun inv(a: Short): Short = a.inv()
        override fun xor(a: Short, other: Short): Short = a xor other
        override fun rotateLeft(a: Short, n: Int): Short {
            val bitCount = n % Short.SIZE_BITS
            return (
                ((a.toInt() and MASK) shl bitCount) or ((a.toInt() and MASK) ushr (Short.SIZE_BITS - bitCount))
            ).toShort()
        }
        override fun RC(index: Int): Short = RC[index].toShort()
    }
}
