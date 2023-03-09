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
        "sha2:sha2-digest-32",
        "sha2:sha2-digest-long",
        "sha2:sha2-224",
        "sha2:sha2-256",
        "sha2:sha2-384",
        "sha2:sha2-512",
        "sha2:sha2-512t",
    ).forEach { name ->
        include(":library:$name")
    }

    include(":bom")
    include(":tools:testing")
}
