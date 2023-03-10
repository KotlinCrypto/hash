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

package org.kotlincrypto.hash

import org.kotlincrypto.core.Digest
import org.kotlincrypto.core.internal.DigestState

/**
 * SHA-512/224
 * */
public fun SHA512_224(): Sha512t = Sha512t(224)

/**
 * SHA-512/256
 * */
public fun SHA512_256(): Sha512t = Sha512t(256)

/**
 * SHA-512/t implementation
 * */
public class Sha512t: Bit64Digest {

    private var isInitialized: Boolean

    /**
     * Primary constructor for creating a new [Sha512t] instance
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

    private constructor(state: DigestState, sha512t: Sha512t): super(state, sha512t) {
        isInitialized = true
    }

    protected override fun copy(state: DigestState): Digest = Sha512t(state, this)

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

        try {
            out[ 0] = (a shr 56).toByte()
            out[ 1] = (a shr 48).toByte()
            out[ 2] = (a shr 40).toByte()
            out[ 3] = (a shr 32).toByte()
            out[ 4] = (a shr 24).toByte()
            out[ 5] = (a shr 16).toByte()
            out[ 6] = (a shr  8).toByte()
            out[ 7] = (a       ).toByte()
            out[ 8] = (b shr 56).toByte()
            out[ 9] = (b shr 48).toByte()
            out[10] = (b shr 40).toByte()
            out[11] = (b shr 32).toByte()
            out[12] = (b shr 24).toByte()
            out[13] = (b shr 16).toByte()
            out[14] = (b shr  8).toByte()
            out[15] = (b       ).toByte()
            out[16] = (c shr 56).toByte()
            out[17] = (c shr 48).toByte()
            out[18] = (c shr 40).toByte()
            out[19] = (c shr 32).toByte()
            out[20] = (c shr 24).toByte()
            out[21] = (c shr 16).toByte()
            out[22] = (c shr  8).toByte()
            out[23] = (c       ).toByte()
            out[24] = (d shr 56).toByte()
            out[25] = (d shr 48).toByte()
            out[26] = (d shr 40).toByte()
            out[27] = (d shr 32).toByte()
            out[28] = (d shr 24).toByte()
            out[29] = (d shr 16).toByte()
            out[30] = (d shr  8).toByte()
            out[31] = (d       ).toByte()
            out[32] = (e shr 56).toByte()
            out[33] = (e shr 48).toByte()
            out[34] = (e shr 40).toByte()
            out[35] = (e shr 32).toByte()
            out[36] = (e shr 24).toByte()
            out[37] = (e shr 16).toByte()
            out[38] = (e shr  8).toByte()
            out[39] = (e       ).toByte()
            out[40] = (f shr 56).toByte()
            out[41] = (f shr 48).toByte()
            out[42] = (f shr 40).toByte()
            out[43] = (f shr 32).toByte()
            out[44] = (f shr 24).toByte()
            out[45] = (f shr 16).toByte()
            out[46] = (f shr  8).toByte()
            out[47] = (f       ).toByte()
            out[48] = (g shr 56).toByte()
            out[49] = (g shr 48).toByte()
            out[50] = (g shr 40).toByte()
            out[51] = (g shr 32).toByte()
            out[52] = (g shr 24).toByte()
            out[53] = (g shr 16).toByte()
            out[54] = (g shr  8).toByte()
            out[55] = (g       ).toByte()
            out[56] = (h shr 56).toByte()
            out[57] = (h shr 48).toByte()
            out[58] = (h shr 40).toByte()
            out[59] = (h shr 32).toByte()
            out[60] = (h shr 24).toByte()
            out[61] = (h shr 16).toByte()
            out[62] = (h shr  8).toByte()
            out[63] = (h       ).toByte()
        } catch (_: IndexOutOfBoundsException) {
            // ignore
        }

        return out
    }
}
