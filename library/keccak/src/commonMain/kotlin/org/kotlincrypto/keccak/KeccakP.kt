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

import kotlin.jvm.JvmOverloads

/**
 * Implementation of Keccak Permutation over the provided [State].
 *
 * @see [State]
 * */
@JvmOverloads
@Throws(IllegalArgumentException::class)
public fun <N: Number> KeccakP(state: State<N, *>, numRounds: Byte = state.roundCount) {
    require(numRounds <= state.roundCount) { "numRounds cannot exceed ${state.roundCount}" }
    if (numRounds < 1) return

    state.withContext {
        val A = state

        var a00 = A[ 0]; var a01 = A[ 1]; var a02 = A[ 2]; var a03 = A[ 3]; var a04 = A[ 4]
        var a05 = A[ 5]; var a06 = A[ 6]; var a07 = A[ 7]; var a08 = A[ 8]; var a09 = A[ 9]
        var a10 = A[10]; var a11 = A[11]; var a12 = A[12]; var a13 = A[13]; var a14 = A[14]
        var a15 = A[15]; var a16 = A[16]; var a17 = A[17]; var a18 = A[18]; var a19 = A[19]
        var a20 = A[20]; var a21 = A[21]; var a22 = A[22]; var a23 = A[23]; var a24 = A[24]

        for (rc in (state.roundCount - numRounds) until state.roundCount) {
            // Theta
            var c0 = xor(xor(xor(xor(a00, a05), a10), a15), a20)
            var c1 = xor(xor(xor(xor(a01, a06), a11), a16), a21)
            val c2 = xor(xor(xor(xor(a02, a07), a12), a17), a22)
            val c3 = xor(xor(xor(xor(a03, a08), a13), a18), a23)
            val c4 = xor(xor(xor(xor(a04, a09), a14), a19), a24)

            val d1 = xor(rotateLeft(c1, 1), c4)
            val d2 = xor(rotateLeft(c2, 1), c0)
            val d3 = xor(rotateLeft(c3, 1), c1)
            val d4 = xor(rotateLeft(c4, 1), c2)
            val d0 = xor(rotateLeft(c0, 1), c3)

            a00 = xor(a00, d1)
            a01 = xor(a01, d2)
            a02 = xor(a02, d3)
            a03 = xor(a03, d4)
            a04 = xor(a04, d0)
            a05 = xor(a05, d1)
            a06 = xor(a06, d2)
            a07 = xor(a07, d3)
            a08 = xor(a08, d4)
            a09 = xor(a09, d0)
            a10 = xor(a10, d1)
            a11 = xor(a11, d2)
            a12 = xor(a12, d3)
            a13 = xor(a13, d4)
            a14 = xor(a14, d0)
            a15 = xor(a15, d1)
            a16 = xor(a16, d2)
            a17 = xor(a17, d3)
            a18 = xor(a18, d4)
            a19 = xor(a19, d0)
            a20 = xor(a20, d1)
            a21 = xor(a21, d2)
            a22 = xor(a22, d3)
            a23 = xor(a23, d4)
            a24 = xor(a24, d0)

            // Rho Pi
            c1  = rotateLeft(a01,  1)
            a01 = rotateLeft(a06, 44)
            a06 = rotateLeft(a09, 20)
            a09 = rotateLeft(a22, 61)
            a22 = rotateLeft(a14, 39)
            a14 = rotateLeft(a20, 18)
            a20 = rotateLeft(a02, 62)
            a02 = rotateLeft(a12, 43)
            a12 = rotateLeft(a13, 25)
            a13 = rotateLeft(a19,  8)
            a19 = rotateLeft(a23, 56)
            a23 = rotateLeft(a15, 41)
            a15 = rotateLeft(a04, 27)
            a04 = rotateLeft(a24, 14)
            a24 = rotateLeft(a21,  2)
            a21 = rotateLeft(a08, 55)
            a08 = rotateLeft(a16, 45)
            a16 = rotateLeft(a05, 36)
            a05 = rotateLeft(a03, 28)
            a03 = rotateLeft(a18, 21)
            a18 = rotateLeft(a17, 15)
            a17 = rotateLeft(a11, 10)
            a11 = rotateLeft(a07,  6)
            a07 = rotateLeft(a10,  3)
            a10 = c1

            // Chi
            c0  = xor(a00, and(inv(a01), a02))
            c1  = xor(a01, and(inv(a02), a03))
            a02 = xor(a02, and(inv(a03), a04))
            a03 = xor(a03, and(inv(a04), a00))
            a04 = xor(a04, and(inv(a00), a01))
            a00 = c0
            a01 = c1

            c0  = xor(a05, and(inv(a06), a07))
            c1  = xor(a06, and(inv(a07), a08))
            a07 = xor(a07, and(inv(a08), a09))
            a08 = xor(a08, and(inv(a09), a05))
            a09 = xor(a09, and(inv(a05), a06))
            a05 = c0
            a06 = c1

            c0  = xor(a10, and(inv(a11), a12))
            c1  = xor(a11, and(inv(a12), a13))
            a12 = xor(a12, and(inv(a13), a14))
            a13 = xor(a13, and(inv(a14), a10))
            a14 = xor(a14, and(inv(a10), a11))
            a10 = c0
            a11 = c1

            c0  = xor(a15, and(inv(a16), a17))
            c1  = xor(a16, and(inv(a17), a18))
            a17 = xor(a17, and(inv(a18), a19))
            a18 = xor(a18, and(inv(a19), a15))
            a19 = xor(a19, and(inv(a15), a16))
            a15 = c0
            a16 = c1

            c0  = xor(a20, and(inv(a21), a22))
            c1  = xor(a21, and(inv(a22), a23))
            a22 = xor(a22, and(inv(a23), a24))
            a23 = xor(a23, and(inv(a24), a20))
            a24 = xor(a24, and(inv(a20), a21))
            a20 = c0
            a21 = c1

            // Iota
            a00 = xor(a00, RC(rc))
        }

        A[ 0] = a00; A[ 1] = a01; A[ 2] = a02; A[ 3] = a03; A[ 4] = a04
        A[ 5] = a05; A[ 6] = a06; A[ 7] = a07; A[ 8] = a08; A[ 9] = a09
        A[10] = a10; A[11] = a11; A[12] = a12; A[13] = a13; A[14] = a14
        A[15] = a15; A[16] = a16; A[17] = a17; A[18] = a18; A[19] = a19
        A[20] = a20; A[21] = a21; A[22] = a22; A[23] = a23; A[24] = a24
    }
}
