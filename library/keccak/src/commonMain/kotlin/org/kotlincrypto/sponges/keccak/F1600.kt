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
package org.kotlincrypto.sponges.keccak

import kotlin.jvm.JvmSynthetic

/**
 * [State] for Keccak-f[1600]
 * */
public class F1600: State<Long, F1600> {
    public constructor(): super(roundCount = 24, state = Array(P_LEN) { 0 })
    private constructor(state: F1600): super(state.roundCount, state.state.copyOf())
    public override fun copy(): F1600 = F1600(this)

    @JvmSynthetic
    internal override fun <T: Any?> withContext(block: Context<Long>.() -> T): T = block(F1600Context)

    private object F1600Context: Context<Long> {
        override fun and(a: Long, other: Long): Long = a and other
        override fun inv(a: Long): Long = a.inv()
        override fun xor(a: Long, other: Long): Long = a xor other
        override fun rotateLeft(a: Long, n: Int): Long {
            val bitCount = n % Long.SIZE_BITS
            return (a shl bitCount) or (a ushr (Long.SIZE_BITS - bitCount))
        }
        override fun RC(index: Int): Long = RC[index]
    }
}
