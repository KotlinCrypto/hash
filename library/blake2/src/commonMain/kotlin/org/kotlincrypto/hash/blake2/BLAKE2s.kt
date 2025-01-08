/*
 * Copyright (c) 2025 Matthew Nelson
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
package org.kotlincrypto.hash.blake2

/**
 * BLAKE2s implementation
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 *
 * @see [BLAKE2s_128]
 * @see [BLAKE2s_160]
 * @see [BLAKE2s_224]
 * @see [BLAKE2s_256]
 * */
public class BLAKE2s: BLAKE2Digest {

    /**
     * Primary constructor for creating a new [BLAKE2s] instance
     *
     * @throws [IllegalArgumentException] when:
     *  - [bitStrength] is less than 8
     *  - [bitStrength] is greater than 256
     *  - [bitStrength] is not a factor of 8
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(bitStrength: Int): super(BLOCK_SIZE_S, bitStrength) {
        TODO("Not yet implemented")
    }

    private constructor(other: BLAKE2s): super(other) {
        TODO("Not yet implemented")
    }

    public override fun copy(): BLAKE2s = BLAKE2s(this)

    protected override fun compressProtected(input: ByteArray, offset: Int) {
        TODO("Not yet implemented")
    }

    protected override fun digestProtected(buffer: ByteArray, offset: Int): ByteArray {
        TODO("Not yet implemented")
    }

    protected override fun resetProtected() {
        TODO("Not yet implemented")
    }

    private companion object {
        private val IV = intArrayOf(
            1779033703, -1150833019, 1013904242, -1521486534,
            1359893119, -1694144372,  528734635,  1541459225,
        )
    }
}
