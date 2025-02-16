rootProject.name = "hash"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

includeBuild("build-logic")

@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings

if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
} else {
    listOf(
        "blake2",
        "md",
        "sha1",
        "sha2",
        "sha3",
    ).forEach { name ->
        include(":library:$name")
    }

//    include(":benchmarks")
    include(":bom")
    include(":tools:testing")
    include(":test-android")
}
