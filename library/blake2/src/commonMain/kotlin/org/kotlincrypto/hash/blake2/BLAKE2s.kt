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
@file:Suppress("FunctionName")

package org.kotlincrypto.hash.blake2

import org.kotlincrypto.core.InternalKotlinCryptoApi
import kotlin.jvm.JvmStatic

/**
 * BLAKE2s implementation
 *
 * https://datatracker.ietf.org/doc/rfc7693/
 * */
public class BLAKE2s: BLAKE2Digest.Bit32 {

    /**
     * Creates a new [BLAKE2s] instance.
     *
     * @param [bitStrength] The number of bits returned when [digest] is invoked.
     *
     * @throws [IllegalArgumentException] when:
     *  - [bitStrength] is less than 8
     *  - [bitStrength] is greater than 256
     *  - [bitStrength] is not a factor of 8
     * */
    public constructor(
        bitStrength: Int,
    ): this(bitStrength, null)

    /**
     * Creates a new [BLAKE2s] instance.
     *
     * @param [bitStrength] The number of bits returned when [digest] is invoked.
     * @param [personalization] A user selected customization bit string to define a variant
     *   of the function. When no customization is desired, [personalization] must be set to a
     *   null value. Must be 8 bytes in length, or null.
     *
     * @throws [IllegalArgumentException] when:
     *  - [bitStrength] is less than 8
     *  - [bitStrength] is greater than 256
     *  - [bitStrength] is not a factor of 8
     *  - [personalization] is non-null and not exactly 8 bytes in length
     * */
    public constructor(
        bitStrength: Int,
        personalization: ByteArray?,
    ): this(
        bitStrength = bitStrength,
        keyLength = 0,
        salt = null,
        personalization = personalization,
    )

    private constructor(other: BLAKE2s): super(other)

    public override fun copy(): BLAKE2s = BLAKE2s(this)

    private constructor(
        bitStrength: Int,
        keyLength: Int,
        salt: ByteArray?,
        personalization: ByteArray?,
        variant: String = "s",
        isLastNode: Boolean = false,
        fanOut: Int = 1,
        depth: Int = 1,
        leafLength: Int = 0,
        nodeOffset: Long = 0L,
        nodeDepth: Int = 0,
        innerLength: Int = 0,
        digestLength: Int = bitStrength / Byte.SIZE_BITS,
    ): super(
        variant = variant,
        bitStrength = bitStrength,
        isLastNode = isLastNode,
        keyLength = keyLength,
        fanOut = fanOut,
        depth = depth,
        leafLength = leafLength,
        nodeOffset = nodeOffset,
        nodeDepth = nodeDepth,
        innerLength = innerLength,
        salt = salt,
        personalization = personalization,
        digestLength = digestLength,
    )


    /** @suppress */
    public companion object: KeyedHashFactory<BLAKE2s>() {

        /**
         * See https://github.com/KotlinCrypto/MACs
         * */
        @JvmStatic
        @InternalKotlinCryptoApi
        @Throws(IllegalArgumentException::class)
        override fun keyedHashInstance(
            bitStrength: Int,
            keyLength: Int,
            salt: ByteArray?,
            personalization: ByteArray?,
        ): BLAKE2s = BLAKE2s(
            bitStrength = bitStrength,
            keyLength = keyLength,
            salt = salt,
            personalization = personalization,
        )
    }
}
