rootProject.name = "hash"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

includeBuild("build-logic")

@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings

if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
} else {
    listOf(
        "md5",
        "sha1",
        "sha2:sha2-256",
        "sha2:sha2-512",
    ).forEach { name ->
        include(":library:$name")
    }

    include(":bom")
    include(":tools:testing")
}
