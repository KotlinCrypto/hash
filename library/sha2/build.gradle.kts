/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
plugins {
    id("configuration")
    id("bom-include")
}

kmpConfiguration {
    configureShared(java9ModuleName = "org.kotlincrypto.hash.sha2", publish = true) {
        common {
            sourceSetMain {
                dependencies {
                    api(libs.kotlincrypto.core.digest)
                }
            }
            sourceSetTest {
                dependencies {
                    implementation(project(":tools:testing"))
                }
            }
        }

        kotlin {
            with(sourceSets) {
                val nonJsSources = listOf("jvm", "native", "wasmJs", "wasmWasi").mapNotNull {
                    findByName(it + "Main")
                }
                if (nonJsSources.isEmpty()) return@kotlin
                val nonJsMain = maybeCreate("nonJsMain").apply {
                    dependsOn(getByName("commonMain"))
                }
                nonJsSources.forEach { it.dependsOn(nonJsMain) }
            }
        }
    }
}
