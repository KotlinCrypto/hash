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
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.configuration)
}

kmpConfiguration {
    configure {
        jvm {
            target {
                withJava()
            }

            kotlinJvmTarget = JavaVersion.VERSION_1_8
            compileSourceCompatibility = JavaVersion.VERSION_1_8
            compileTargetCompatibility = JavaVersion.VERSION_1_8
        }

        js()
//        wasm()
        wasmNativeAll()

        androidNativeAll()

        iosAll()
        macosAll()
        tvosAll()
        watchosAll()

        linuxAll()
        mingwAll()

        common {
            pluginIds(libs.plugins.publish.get().pluginId)

            sourceSetMain {
                dependencies {
                    api(libs.kotlincrypto.core.digest)
                }
            }
            sourceSetTest {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(project(":tools:testing"))
                }
            }
        }

        kotlin {
            explicitApi()

            if (!version.toString().endsWith("-SNAPSHOT")) {
                extensions.configure<SigningExtension>("signing") {
                    useGpgCmd()
                }
            }
        }
    }
}
