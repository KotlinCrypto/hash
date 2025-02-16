/*
 * Copyright (c) 2025 KotlinCrypto
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
@file:JvmName("SHA512tKt")
@file:Suppress("FunctionName")

package org.kotlincrypto.hash.sha2

import kotlin.jvm.JvmName

/** @suppress */
@Deprecated("Use SHA512t directly", ReplaceWith("SHA512t(224)", "org.kotlincrypto.hash.sha2.SHA512t"))
public fun SHA512_224(): SHA512t = SHA512t(t = 224)

/** @suppress */
@Deprecated("Use SHA512t directly", ReplaceWith("SHA512t(256)", "org.kotlincrypto.hash.sha2.SHA512t"))
public fun SHA512_256(): SHA512t = SHA512t(t = 256)
