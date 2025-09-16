/*
 * Copyright (c) 2023 KotlinCrypto
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
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnPlugin
import org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootExtension

plugins {
    alias(libs.plugins.android.library) apply(false)
    alias(libs.plugins.benchmark) apply(false)
    alias(libs.plugins.binary.compat)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform) apply(false)
}

allprojects {
    findProperty("GROUP")?.let { group = it }
    findProperty("VERSION_NAME")?.let { version = it }
    findProperty("POM_DESCRIPTION")?.let { description = it.toString() }

    repositories {
        mavenCentral()

        if (version.toString().endsWith("-SNAPSHOT")) {
            // Only allow snapshot dependencies for non-release versions.
            // This would cause a build failure if attempting to make a release
            // while depending on a -SNAPSHOT version (such as core).
            maven("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}

@Suppress("PropertyName")
val CHECK_PUBLICATION = findProperty("CHECK_PUBLICATION") != null

plugins.withType<YarnPlugin> {
    the<YarnRootExtension>().apply {
        lockFileDirectory = rootDir.resolve(".kotlin-js-store").resolve("js")
        if (CHECK_PUBLICATION) yarnLockMismatchReport = YarnLockMismatchReport.NONE
    }
}

plugins.withType<WasmYarnPlugin> {
    the<WasmYarnRootExtension>().apply {
        lockFileDirectory = rootDir.resolve(".kotlin-js-store").resolve("wasm")
        if (CHECK_PUBLICATION) yarnLockMismatchReport = YarnLockMismatchReport.NONE
    }
}

apiValidation {
    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib.enabled = findProperty("KMP_TARGETS") == null

    if (CHECK_PUBLICATION) {
        ignoredProjects.add("check-publication")
    } else {
        nonPublicMarkers.add("org.kotlincrypto.core.InternalKotlinCryptoApi")
        ignoredProjects.add("benchmarks")
        ignoredProjects.add("testing")
    }
}
