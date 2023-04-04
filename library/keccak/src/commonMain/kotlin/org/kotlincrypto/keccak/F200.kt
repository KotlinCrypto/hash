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
import kotlin.jvm.JvmSynthetic

public class F200: State<Byte, F200> {
    public constructor(): super(roundCount = 18, state = Array(PLEN) { 0 })
    private constructor(state: F200): super(state.roundCount, state.state.copyOf())
    public override fun copy(): F200 = F200(this)

    @JvmSynthetic
    internal override fun <T: Any?> withContext(block: Context<Byte>.() -> T): T = block(F200Context)

    private object F200Context: Context<Byte> {
        private const val MASK: Int = 0xff

        override fun and(a: Byte, other: Byte): Byte = a and other
        override fun inv(a: Byte): Byte = a.inv()
        override fun xor(a: Byte, other: Byte): Byte = a xor other
        override fun rotateLeft(a: Byte, n: Int): Byte {
            val bitCount = n % Byte.SIZE_BITS
            return (
                ((a.toInt() and MASK) shl bitCount) or ((a.toInt() and MASK) ushr (Byte.SIZE_BITS - bitCount))
            ).toByte()
        }
        override fun RC(index: Int): Byte = RC[index].toByte()
    }
}
