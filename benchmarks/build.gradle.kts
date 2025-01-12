/*
 * Copyright (c) 2024 Matthew Nelson
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
import io.matthewnelson.kmp.configuration.extension.container.target.KmpTarget
import kotlinx.benchmark.gradle.BenchmarksExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    id("configuration")
}

kmpConfiguration {
    val benchmarks by lazy { extensions.getByType<BenchmarksExtension>() }

    @OptIn(ExperimentalWasmDsl::class)
    configure {
        fun <T: KotlinTarget> KmpTarget<T>.register() {
            target { benchmarks.targets.register(name) }
        }

        jvm {
            register()
            sourceSetMain {
                dependencies {
                    implementation(libs.bouncy.castle)
                }
            }
        }

        js { target { browser(); nodejs() }; register() }
        wasmJs { target { browser(); nodejs() }; register() }

        val nativeHost = "nativeHost"
        when (HostManager.host) {
            is KonanTarget.LINUX_X64 -> linuxX64(nativeHost) { register() }
            is KonanTarget.LINUX_ARM64 -> linuxArm64(nativeHost) { register() }
            is KonanTarget.MACOS_X64 -> macosX64(nativeHost) { register() }
            is KonanTarget.MACOS_ARM64 -> macosArm64(nativeHost) { register() }
            is KonanTarget.MINGW_X64 -> mingwX64(nativeHost) { register() }
            else -> {}
        }

        common {
            pluginIds(libs.plugins.benchmark.get().pluginId)

            sourceSetMain {
                dependencies {
                    implementation(libs.benchmark.runtime)
                    implementation(libs.kotlincrypto.bitops.bits)
                    implementation(libs.kotlincrypto.bitops.endian)
                    implementation(libs.kotlincrypto.sponges.keccak)
                    implementation(project(":library:blake2"))
                    implementation(project(":library:md"))
                    implementation(project(":library:sha1"))
                    implementation(project(":library:sha2"))
                    implementation(project(":library:sha3"))
                }
            }
        }
    }
}
