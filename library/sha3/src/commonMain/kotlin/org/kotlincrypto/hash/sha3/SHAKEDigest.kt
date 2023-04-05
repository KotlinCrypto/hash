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
package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.*
import org.kotlincrypto.core.internal.DigestState
import org.kotlincrypto.endians.LittleEndian
import org.kotlincrypto.keccak.F1600
import kotlin.jvm.JvmStatic

/**
 * Core abstraction for:
 *  - CSHAKE128
 *  - CSHAKE256
 *  - SHAKE128
 *  - SHAKE256
 *
 * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf#3%20cSHAKE
 * https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
 * */
public sealed class SHAKEDigest: KeccakDigest {

    private val xOfMode: Boolean

    protected constructor(
        N: ByteArray?,
        S: ByteArray?,
        xOfMode: Boolean,
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
    ): super(algorithm, blockSize, digestLength, dsByteFromInput(N, S)) {
        this.xOfMode = xOfMode

        // TODO
    }

    protected constructor(state: DigestState, digest: SHAKEDigest): super(state, digest) {
        this.xOfMode = digest.xOfMode

        // TODO
    }

    protected final override fun out(A: F1600, outLength: Int): ByteArray {
        return if (xOfMode) {
            // newReader called digest(). Snipe the output
            // and pass it the current state in bytes.
            super.out(A, A.size * Long.SIZE_BYTES)
        } else {
            super.out(A, outLength)
        }
    }

    protected final override fun resetDigest() {
        super.resetDigest()
        // TODO
    }

    @OptIn(InternalKotlinCryptoApi::class)
    public sealed class SHAKEXofFactory<A: SHAKEDigest>: XofFactory<A>() {

        protected inner class SHAKEXof(delegate: A) : XofFactory<A>.BaseXof(delegate) {
            protected override fun newReader(delegateCopy: A): Xof<A>.Reader {

                // Calling digest() will flush the copy's buffered input and
                // apply padding and final permutation(s). Because it is in
                // xOfMode, the returned bytes will be the entire contents
                // of its final state such that it can be rebuilt here in
                // order to be used for variable output length reads.
                val state: F1600 = delegateCopy.digest().let { A ->
                    val s = F1600()

                    var b = 0
                    for (i in s.indices) {
                        val d = LittleEndian.bytesToLong(A[b++], A[b++], A[b++], A[b++], A[b++], A[b++], A[b++], A[b++])
                        s.addData(i, d)
                    }

                    s
                }

                return object : Reader() {
                    protected override fun readProtected(out: ByteArray, offset: Int, len: Int): Int {
                        TODO("Not yet implemented")
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            public override fun copy(): Xof<A> = SHAKEXof(delegate.copy() as A)
        }
    }

    protected companion object {
        internal const val SHAKE: String = "SHAKE"
        internal const val CSHAKE: String = "C$SHAKE"

        private const val PAD_SHAKE: Byte = 0x1f
        private const val PAD_CSHAKE: Byte = 0x04

        /**
         * Given that [N] and [S] are both null and/or empty, CSHAKE is
         * functionally equivalent to SHAKE and thus the [dsByte] must
         * reflect so.
         * */
        @JvmStatic
        private fun dsByteFromInput(N: ByteArray?, S: ByteArray?): Byte {
            return if (N?.isNotEmpty() == true || S?.isNotEmpty() == true) PAD_CSHAKE else PAD_SHAKE
        }
    }
}
