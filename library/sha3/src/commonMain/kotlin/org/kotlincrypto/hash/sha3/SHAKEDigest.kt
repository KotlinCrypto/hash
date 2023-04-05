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
import org.kotlincrypto.endians.LittleEndian.Companion.toLittleEndian
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
    private var isReadingXof: Boolean

    protected constructor(
        N: ByteArray?,
        S: ByteArray?,
        xOfMode: Boolean,
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
    ): super(algorithm, blockSize, digestLength, dsByteFromInput(N, S)) {
        this.xOfMode = xOfMode
        this.isReadingXof = false
        // TODO
    }

    protected constructor(state: DigestState, digest: SHAKEDigest): super(state, digest) {
        this.xOfMode = digest.xOfMode
        this.isReadingXof = digest.isReadingXof
        // TODO
    }

    protected final override fun extract(A: F1600, out: ByteArray, offset: Int, len: Int, bytesRead: Long): ByteArray {
        return if (xOfMode && !isReadingXof) {
            // newReader called digest(). Snipe the extraction
            // and pass it the current state in bytes.
            val newOut = ByteArray(A.size * Long.SIZE_BYTES)
            for (i in 0 until A.size) {
                A[i].toLittleEndian().copyInto(newOut, i * Long.SIZE_BYTES)
            }
            isReadingXof = true
            return newOut
        } else {
            super.extract(A, out, offset, len, bytesRead)
        }
    }

    protected final override fun resetDigest() {
        super.resetDigest()

        // Don't want to ever reset isReadingXof b/c Xof mode
        // uses a copy of the digest in an isolated Reader environment.
        // The copy created for that Xof.Reader will never go back to
        // being updated, nor will it be copied again.
//        isReadingXof = false
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
                    val f1600 = F1600()

                    var b = 0
                    for (i in f1600.indices) {
                        f1600.addData(
                            i,
                            LittleEndian.bytesToLong(A[b++], A[b++], A[b++], A[b++], A[b++], A[b++], A[b++], A[b++])
                        )
                    }

                    f1600
                }

                return object : Reader() {
                    override fun readProtected(out: ByteArray, offset: Int, len: Int, bytesRead: Long) {
                        delegateCopy.extract(state, out, offset, len, bytesRead)
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
