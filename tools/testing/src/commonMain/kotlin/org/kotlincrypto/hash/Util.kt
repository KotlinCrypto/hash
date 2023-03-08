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
package org.kotlincrypto.hash

import io.matthewnelson.encoding.core.Decoder.Companion.decodeToByteArray

object Util {
    // big-endian >> little endian via https://www.save-editor.com/tools/wse_hex.html#littleendian
    // output.littleEndianToLong
    //
    // e.g.
    // 0x6a09e667f3bcc908 >> 08C9BCF367E6096A >> 7640891576956012808L
    // 0xbb67ae8584caa73b >> 3BA7CA8485AE67BB >> -4942790177534073029L

    private fun littleEndianToInt(bs: ByteArray, off: Int): Int {
        var offs = off
        var n = bs[offs].toInt() and 0xff
        n = n or (bs[++offs].toInt() and 0xff shl 8)
        n = n or (bs[++offs].toInt() and 0xff shl 16)
        n = n or (bs[++offs].toInt() shl 24)
        return n
    }

    private fun littleEndianToLong(bs: ByteArray, off: Int): Long {
        val lo: Int = littleEndianToInt(bs, off)
        val hi: Int = littleEndianToInt(bs, off + 4)
        return (hi.toLong() and 0xffffffffL) shl 32 or (lo.toLong() and 0xffffffffL)
    }

    fun String.littleEndianToLong(): Long {
        val bytes = decodeToByteArray(TestData.base16)
        return littleEndianToLong(bytes, 0)
    }
}
