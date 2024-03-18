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
@file:Suppress("FunctionName")

package org.kotlincrypto.hash.sha2

import org.kotlincrypto.core.digest.Digest
import org.kotlincrypto.core.digest.internal.DigestState

/**
 * SHA-512/224
 * */
public fun SHA512_224(): SHA512t = SHA512t(224)

/**
 * SHA-512/256
 * */
public fun SHA512_256(): SHA512t = SHA512t(256)

/**
 * SHA-512/t implementation
 * */
public class SHA512t: Bit64Digest {

    private var isInitialized: Boolean

    /**
     * Primary constructor for creating a new [SHA512t] instance
     *
     * @see [SHA512_224]
     * @see [SHA512_256]
     * @throws [IllegalArgumentException] when:
     *  - [digestLength] is less than or equal to 0
     *  - [t] is greater than or equal to 512
     *  - [t] is not a factor of 8
     *  - [t] is 384
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(t: Int): super(
        d = 512,
        t = t,
        h0 = 7640891576956012808L  xor -6510615555426900571L,
        h1 = -4942790177534073029L xor -6510615555426900571L,
        h2 = 4354685564936845355L  xor -6510615555426900571L,
        h3 = -6534734903238641935L xor -6510615555426900571L,
        h4 = 5840696475078001361L  xor -6510615555426900571L,
        h5 = -7276294671716946913L xor -6510615555426900571L,
        h6 = 2270897969802886507L  xor -6510615555426900571L,
        h7 = 6620516959819538809L  xor -6510615555426900571L,
    ) {
        isInitialized = false

        update(0x53)
        update(0x48)
        update(0x41)
        update(0x2D)
        update(0x35)
        update(0x31)
        update(0x32)
        update(0x2F)

        var bitLength = t

        if (t > 100) {
            update((bitLength / 100 + 0x30).toByte())
            bitLength %= 100
        }

        if (t > 10) {
            update((bitLength / 10 + 0x30).toByte())
            bitLength %= 10
        }

        update((bitLength + 0x30).toByte())

        digest()
    }

    private constructor(state: DigestState, digest: SHA512t): super(state, digest) {
        isInitialized = true
    }

    protected override fun copy(state: DigestState): Digest = SHA512t(state, this)

    protected override fun out(a: Long, b: Long, c: Long, d: Long, e: Long, f: Long, g: Long, h: Long): ByteArray {
        if (!isInitialized) {

            h0 = a
            h1 = b
            h2 = c
            h3 = d
            h4 = e
            h5 = f
            h6 = g
            h7 = h

            isInitialized = true
            return ByteArray(0)
        }

        val out = ByteArray(digestLength())
        var i = 0

        fun Long.setNextOutOrSkip() {
            if (i >= out.size) return
            out[i++] = toByte()
        }

        (a shr 56).setNextOutOrSkip()
        (a shr 48).setNextOutOrSkip()
        (a shr 40).setNextOutOrSkip()
        (a shr 32).setNextOutOrSkip()
        (a shr 24).setNextOutOrSkip()
        (a shr 16).setNextOutOrSkip()
        (a shr  8).setNextOutOrSkip()
        (a       ).setNextOutOrSkip()
        (b shr 56).setNextOutOrSkip()
        (b shr 48).setNextOutOrSkip()
        (b shr 40).setNextOutOrSkip()
        (b shr 32).setNextOutOrSkip()
        (b shr 24).setNextOutOrSkip()
        (b shr 16).setNextOutOrSkip()
        (b shr  8).setNextOutOrSkip()
        (b       ).setNextOutOrSkip()
        (c shr 56).setNextOutOrSkip()
        (c shr 48).setNextOutOrSkip()
        (c shr 40).setNextOutOrSkip()
        (c shr 32).setNextOutOrSkip()
        (c shr 24).setNextOutOrSkip()
        (c shr 16).setNextOutOrSkip()
        (c shr  8).setNextOutOrSkip()
        (c       ).setNextOutOrSkip()
        (d shr 56).setNextOutOrSkip()
        (d shr 48).setNextOutOrSkip()
        (d shr 40).setNextOutOrSkip()
        (d shr 32).setNextOutOrSkip()
        (d shr 24).setNextOutOrSkip()
        (d shr 16).setNextOutOrSkip()
        (d shr  8).setNextOutOrSkip()
        (d       ).setNextOutOrSkip()
        (e shr 56).setNextOutOrSkip()
        (e shr 48).setNextOutOrSkip()
        (e shr 40).setNextOutOrSkip()
        (e shr 32).setNextOutOrSkip()
        (e shr 24).setNextOutOrSkip()
        (e shr 16).setNextOutOrSkip()
        (e shr  8).setNextOutOrSkip()
        (e       ).setNextOutOrSkip()
        (f shr 56).setNextOutOrSkip()
        (f shr 48).setNextOutOrSkip()
        (f shr 40).setNextOutOrSkip()
        (f shr 32).setNextOutOrSkip()
        (f shr 24).setNextOutOrSkip()
        (f shr 16).setNextOutOrSkip()
        (f shr  8).setNextOutOrSkip()
        (f       ).setNextOutOrSkip()
        (g shr 56).setNextOutOrSkip()
        (g shr 48).setNextOutOrSkip()
        (g shr 40).setNextOutOrSkip()
        (g shr 32).setNextOutOrSkip()
        (g shr 24).setNextOutOrSkip()
        (g shr 16).setNextOutOrSkip()
        (g shr  8).setNextOutOrSkip()
        (g       ).setNextOutOrSkip()
        (h shr 56).setNextOutOrSkip()
        (h shr 48).setNextOutOrSkip()
        (h shr 40).setNextOutOrSkip()
        (h shr 32).setNextOutOrSkip()
        (h shr 24).setNextOutOrSkip()
        (h shr 16).setNextOutOrSkip()
        (h shr  8).setNextOutOrSkip()
        (h       ).setNextOutOrSkip()

        return out
    }
}
