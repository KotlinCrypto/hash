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

import org.kotlincrypto.hash.md.MD5

@Deprecated(
    message = "Md5 was renamed to MD5",
    replaceWith = ReplaceWith(
        expression = "MD5",
        imports = [ "org.kotlincrypto.hash.md.MD5" ]
    )
)
public typealias Md5 = MD5
