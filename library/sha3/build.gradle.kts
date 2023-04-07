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
    configureShared(publish = true) {
        common {
            sourceSetMain {
                dependencies {
                    api(libs.kotlincrypto.core.digest)
                    // TODO: Move to core repository
                    api(project(":library:xof"))

                    implementation(libs.kotlincrypto.sponges.keccak)
                    implementation(libs.kotlincrypto.endians.endians)
                }
            }
            sourceSetTest {
                dependencies {
                    implementation(project(":tools:testing"))
                }
            }
        }
    }
}
