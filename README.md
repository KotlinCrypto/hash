# hash
[![badge-license]][url-license]
[![badge-latest-release]][url-latest-release]

[![badge-kotlin]][url-kotlin]
[![badge-bitops]][url-bitops]
[![badge-core]][url-core]
[![badge-sponges]][url-sponges]

![badge-platform-android]
![badge-platform-jvm]
![badge-platform-js]
![badge-platform-js-node]
![badge-platform-wasm]
![badge-platform-linux]
![badge-platform-macos]
![badge-platform-ios]
![badge-platform-tvos]
![badge-platform-watchos]
![badge-platform-windows]
![badge-support-android-native]
![badge-support-linux-arm]

Cryptographic hash functions for Kotlin Multiplatform

### Modules

 - [md](library/md/README.md)
 - [sha1](library/sha1/README.md)
 - [sha2](library/sha2/README.md)
 - [sha3](library/sha3/README.md)
 - [blake2](library/blake2/README.md)

### API Docs

 - [https://hash.kotlincrypto.org][url-docs]

### Get Started

The best way to keep `KotlinCrypto` dependencies up to date is by using the 
[version-catalog][url-version-catalog]. Alternatively, you can use the BOM as 
shown below.

<!-- TAG_VERSION -->

```kotlin
// build.gradle.kts
dependencies {
    // define the BOM and its version
    implementation(project.dependencies.platform("org.kotlincrypto.hash:bom:0.8.0"))

    // define artifacts without version
    
    // MD5
    implementation("org.kotlincrypto.hash:md")

    // SHA-1
    implementation("org.kotlincrypto.hash:sha1")
    
    // SHA-224, SHA-256, SHA-384, SHA-512
    // SHA-512/t, SHA-512/224, SHA-512/256
    implementation("org.kotlincrypto.hash:sha2")

    // Keccak-224, Keccak-256, Keccak-384, Keccak-512
    // SHA3-224, SHA3-256, SHA3-384, SHA3-512
    // SHAKE128, SHAKE256
    // CSHAKE128, CSHAKE256
    // ParallelHash128, ParallelHash256
    // TupleHash128, TupleHash256
    implementation("org.kotlincrypto.hash:sha3")

    // BLAKE2b, BLAKE2s
    implementation("org.kotlincrypto.hash:blake2")
}
```

<!-- TAG_VERSION -->
[badge-latest-release]: https://img.shields.io/badge/latest--release-0.8.0-blue.svg?style=flat
[badge-license]: https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat

<!-- TAG_DEPENDENCIES -->
[badge-kotlin]: https://img.shields.io/badge/kotlin-2.2.20-blue.svg?logo=kotlin
[badge-bitops]: https://img.shields.io/badge/kotlincrypto.bitops-0.3.0-blue.svg
[badge-core]: https://img.shields.io/badge/kotlincrypto.core-0.8.0-blue.svg
[badge-sponges]: https://img.shields.io/badge/kotlincrypto.sponges-0.5.0-blue.svg

<!-- TAG_PLATFORMS -->
[badge-platform-android]: http://img.shields.io/badge/-android-6EDB8D.svg?style=flat
[badge-platform-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat
[badge-platform-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat
[badge-platform-js-node]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat
[badge-platform-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat
[badge-platform-macos]: http://img.shields.io/badge/-macos-111111.svg?style=flat
[badge-platform-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat
[badge-platform-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat
[badge-platform-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat
[badge-platform-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat
[badge-platform-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat
[badge-support-android-native]: http://img.shields.io/badge/support-[AndroidNative]-6EDB8D.svg?style=flat
[badge-support-linux-arm]: http://img.shields.io/badge/support-[LinuxArm]-2D3F6C.svg?style=flat

[url-latest-release]: https://github.com/KotlinCrypto/hash/releases/latest
[url-license]: https://www.apache.org/licenses/LICENSE-2.0.txt
[url-kotlin]: https://kotlinlang.org
[url-bitops]: https://github.com/KotlinCrypto/bitops
[url-core]: https://github.com/KotlinCrypto/core
[url-sponges]: https://github.com/KotlinCrypto/sponges
[url-version-catalog]: https://github.com/KotlinCrypto/version-catalog
[url-docs]: https://hash.kotlincrypto.org
