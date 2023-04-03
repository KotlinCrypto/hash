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
package org.kotlincrypto.endians

public operator fun ByteArray.plus(other: BigEndian): ByteArray {
    val b = ByteArray(size + other.size)
    copyInto(b)
    other.copyInto(b, size)
    return b
}

public operator fun ByteArray.plus(other: LittleEndian): ByteArray {
    val b = ByteArray(size + other.size)
    copyInto(b)
    other.copyInto(b, size)
    return b
}
