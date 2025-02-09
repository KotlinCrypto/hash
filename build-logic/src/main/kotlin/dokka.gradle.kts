/*
 * Copyright (c) 2025 Matthew Nelson
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
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import java.net.URI

plugins {
    id("org.jetbrains.dokka")
}

tasks.withType<DokkaTaskPartial>().configureEach {
    suppressInheritedMembers = true

    dokkaSourceSets.configureEach {
        includes.from("README.md")
        noStdlibLink = true

        externalDocumentationLink {
            url = URI("https://core.kotlincrypto.org/").toURL()
        }
        externalDocumentationLink {
            url = URI("https://error.kotlincrypto.org/").toURL()
        }

        sourceLink {
            localDirectory = rootDir
            remoteUrl = URI("https://github.com/KotlinCrypto/hash/tree/master").toURL()
            remoteLineSuffix = "#L"
        }

        documentedVisibilities.set(setOf(
            Visibility.PUBLIC,
        ))
    }
}
