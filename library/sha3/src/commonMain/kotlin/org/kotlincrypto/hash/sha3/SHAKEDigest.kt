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

import org.kotlincrypto.core.internal.DigestState
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

    protected constructor(
        N: ByteArray?,
        S: ByteArray?,
        algorithm: String,
        blockSize: Int,
        digestLength: Int,
    ): super(algorithm, blockSize, digestLength, dsByteFromInput(N, S)) {
        // TODO
    }

    protected constructor(state: DigestState, digest: SHAKEDigest): super(state, digest) {
        // TODO
    }

    protected final override fun resetDigest() {
        super.resetDigest()
        // TODO
    }

    // TODO: XOF (Extendable-Output Functions)
    //  https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
    //  https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-185.pdf

    internal companion object {
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
