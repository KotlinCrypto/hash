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
 * BLAKE2b implementation
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 *
 * @see [BLAKE2b_160]
 * @see [BLAKE2b_256]
 * @see [BLAKE2b_384]
 * @see [BLAKE2b_512]
 * */
public class BLAKE2b: BLAKE2Digest {

    /**
     * Primary constructor for creating a new [BLAKE2b] instance
     *
     * @throws [IllegalArgumentException] when:
     *  - [bitStrength] is less than 8
     *  - [bitStrength] is greater than 512
     *  - [bitStrength] is not a factor of 8
     * */
    @Throws(IllegalArgumentException::class)
    public constructor(bitStrength: Int): super(BLOCK_SIZE_B, bitStrength) {
        TODO("Not yet implemented")
    }

    private constructor(other: BLAKE2b): super(other) {
        TODO("Not yet implemented")
    }

    public override fun copy(): BLAKE2b = BLAKE2b(this)

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
        private val IV = longArrayOf(
            7640891576956012808, -4942790177534073029, 4354685564936845355, -6534734903238641935,
            5840696475078001361, -7276294671716946913, 2270897969802886507,  6620516959819538809,
        )
    }
}
