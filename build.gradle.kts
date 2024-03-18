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
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.multiplatform) apply(false)
    alias(libs.plugins.android.library) apply(false)
    alias(libs.plugins.binaryCompat)
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
            maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }

}

plugins.withType<YarnPlugin> {
    the<YarnRootExtension>().lockFileDirectory = rootDir.resolve(".kotlin-js-store")
}

plugins.withType<NodeJsRootPlugin> {
    the<NodeJsRootExtension>().apply {
        nodeVersion = "21.0.0-v8-canary202309167e82ab1fa2"
        nodeDownloadBaseUrl = "https://nodejs.org/download/v8-canary"
    }

    tasks.withType<KotlinNpmInstallTask>().configureEach {
        args.add("--ignore-engines")
    }
}

apiValidation {
    if (findProperty("CHECK_PUBLICATION") != null) {
        ignoredProjects.add("check-publication")
    } else {
        nonPublicMarkers.add("org.kotlincrypto.core.InternalKotlinCryptoApi")

        ignoredProjects.add("testing")
    }
}
