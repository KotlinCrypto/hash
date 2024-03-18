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

        fun Long.putOut() {
            if (i == out.size) return
            out[i++] = toByte()
        }

        (a shr 56).putOut()
        (a shr 48).putOut()
        (a shr 40).putOut()
        (a shr 32).putOut()
        (a shr 24).putOut()
        (a shr 16).putOut()
        (a shr  8).putOut()
        (a       ).putOut()
        (b shr 56).putOut()
        (b shr 48).putOut()
        (b shr 40).putOut()
        (b shr 32).putOut()
        (b shr 24).putOut()
        (b shr 16).putOut()
        (b shr  8).putOut()
        (b       ).putOut()
        (c shr 56).putOut()
        (c shr 48).putOut()
        (c shr 40).putOut()
        (c shr 32).putOut()
        (c shr 24).putOut()
        (c shr 16).putOut()
        (c shr  8).putOut()
        (c       ).putOut()
        (d shr 56).putOut()
        (d shr 48).putOut()
        (d shr 40).putOut()
        (d shr 32).putOut()
        (d shr 24).putOut()
        (d shr 16).putOut()
        (d shr  8).putOut()
        (d       ).putOut()
        (e shr 56).putOut()
        (e shr 48).putOut()
        (e shr 40).putOut()
        (e shr 32).putOut()
        (e shr 24).putOut()
        (e shr 16).putOut()
        (e shr  8).putOut()
        (e       ).putOut()
        (f shr 56).putOut()
        (f shr 48).putOut()
        (f shr 40).putOut()
        (f shr 32).putOut()
        (f shr 24).putOut()
        (f shr 16).putOut()
        (f shr  8).putOut()
        (f       ).putOut()
        (g shr 56).putOut()
        (g shr 48).putOut()
        (g shr 40).putOut()
        (g shr 32).putOut()
        (g shr 24).putOut()
        (g shr 16).putOut()
        (g shr  8).putOut()
        (g       ).putOut()
        (h shr 56).putOut()
        (h shr 48).putOut()
        (h shr 40).putOut()
        (h shr 32).putOut()
        (h shr 24).putOut()
        (h shr 16).putOut()
        (h shr  8).putOut()
        (h       ).putOut()

        return out
    }
}
