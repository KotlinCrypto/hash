/*
 * Copyright (c) 2023 Toxicity
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
plugins {
    id("configuration")
}

repositories { google() }

kmpConfiguration {
    configure {
        androidLibrary {
            kotlinJvmTarget = JavaVersion.VERSION_11
            compileSourceCompatibility = JavaVersion.VERSION_11
            compileTargetCompatibility = JavaVersion.VERSION_11

            android {
                namespace = "org.kotlincrypto.hash"
                compileSdk = 34

                defaultConfig {
                    minSdk = 14

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            sourceSetTestInstrumented {
                kotlin.srcDir("src/androidInstrumentedTest/blake2")
                kotlin.srcDir("src/androidInstrumentedTest/md")
                kotlin.srcDir("src/androidInstrumentedTest/sha1")
                kotlin.srcDir("src/androidInstrumentedTest/sha2")
                kotlin.srcDir("src/androidInstrumentedTest/sha3")

                dependencies {
                    implementation(libs.androidx.test.runner)
                    implementation(kotlin("test"))

                    implementation(project(":library:blake2"))
                    implementation(project(":library:md"))
                    implementation(project(":library:sha1"))
                    implementation(project(":library:sha2"))
                    implementation(project(":library:sha3"))
                    implementation(project(":tools:testing"))
                }
            }
        }
    }
}
