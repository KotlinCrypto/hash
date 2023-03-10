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

import org.kotlincrypto.hash.sha2.SHA256

@Deprecated(
    message = """
        Sha256 was renamed to SHA256 and moved to the sha2 module
        
        Step 1: Use ReplaceWith feature to update name to SHA256
        Step 2: Replace dependency on `sha2-256` with `sha2`
    """,
    replaceWith = ReplaceWith(
        expression = "SHA256",
        imports = [ "org.kotlincrypto.hash.sha2.SHA256" ]
    )
)
public typealias Sha256 = SHA256
