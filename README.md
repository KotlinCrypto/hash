# hash
[![badge-license]][url-license]
[![badge-latest-release]][url-latest-release]

[![badge-kotlin]][url-kotlin]
[![badge-core]][url-core]
[![badge-endians]][url-endians]
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
![badge-support-apple-silicon]
![badge-support-js-ir]
![badge-support-linux-arm]

Cryptographic hash functions for Kotlin Multiplatform

Utilized by [KotlinCrypto/MACs][url-macs]

### Usage

See [HERE][url-core-usage] for basic usage example for `Digest`.

```kotlin
fun main() {
    // Digests that may be needed for backward compatibility but 
    // should no longer be utilized because they have been broken.
    MD5()
    SHA1()
}
```

`SHA2 Digests`

```kotlin
fun main() {
    SHA224()
    SHA256()
    SHA384()
    SHA512()

    SHA512_224()
    SHA512_256()
    SHA512t(504)
}
```

`SHA3 Digests`

```kotlin
fun main() {
    Keccak224()
    Keccak256()
    Keccak384()
    Keccak512()
    SHA3_224()
    SHA3_256()
    SHA3_384()
    SHA3_512()

    SHAKE128()
    SHAKE256(outputLength = 640) // returns 640 bytes instead of the default when digest() is invoked
    
    // NIST.SP.800-185 derived functions
    val S = "My Customization".encodeToByteArray()
    CSHAKE128(null, S, outputLength = 128)
    CSHAKE256(null, S)
    ParallelHash128(null, B = 123)
    ParallelHash256(S, B = 456, outputLength = 123)
    TupleHash128(S, outputLength = 320)
    TupleHash256(null)
}
```

`SHA3 XOFs` (i.e. [Extendable-Output Functions][url-pub-xof])

See [HERE][url-core-usage] for details on what `XOFs` are, and a basic usage example for `Xof`.

```kotlin
fun main() {
    SHAKE128.xOf()
    SHAKE256.xOf()

    // NIST.SP.800-185 derived functions
    val S = "My Customization".encodeToByteArray()
    CSHAKE128.xOf(null, S)
    CSHAKE256.xOf(null, S)
    ParallelHash128.xOf(S, B = 123)
    ParallelHash256.xOf(B = 654)
    TupleHash128.xOf(S)
    TupleHash256.xOf()
}
```

### Get Started

The best way to keep `KotlinCrypto` dependencies up to date is by using the 
[version-catalog][url-version-catalog]. Alternatively, you can use the BOM as 
shown below.

<!-- TAG_VERSION -->

```kotlin
// build.gradle.kts
dependencies {
    // define the BOM and its version
    implementation(platform("org.kotlincrypto.hash:bom:0.5.4"))

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
}
```

<!-- TAG_VERSION -->
[badge-latest-release]: https://img.shields.io/badge/latest--release-0.5.4-blue.svg?style=flat
[badge-license]: https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat

<!-- TAG_DEPENDENCIES -->
[badge-kotlin]: https://img.shields.io/badge/kotlin-1.9.24-blue.svg?logo=kotlin
[badge-core]: https://img.shields.io/badge/kotlincrypto.core-0.5.4-blue.svg
[badge-endians]: https://img.shields.io/badge/kotlincrypto.endians-0.3.1-blue.svg
[badge-sponges]: https://img.shields.io/badge/kotlincrypto.sponges-0.3.2-blue.svg

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
[badge-support-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat
[badge-support-js-ir]: https://img.shields.io/badge/support-[js--IR]-AAC4E0.svg?style=flat
[badge-support-linux-arm]: http://img.shields.io/badge/support-[LinuxArm]-2D3F6C.svg?style=flat
[badge-support-linux-mips]: http://img.shields.io/badge/support-[LinuxMIPS]-2D3F6C.svg?style=flat

[url-latest-release]: https://github.com/KotlinCrypto/hash/releases/latest
[url-license]: https://www.apache.org/licenses/LICENSE-2.0.txt
[url-kotlin]: https://kotlinlang.org
[url-core]: https://github.com/KotlinCrypto/core
[url-core-usage]: https://github.com/KotlinCrypto/core#usage
[url-endians]: https://github.com/KotlinCrypto/endians
[url-sponges]: https://github.com/KotlinCrypto/sponges
[url-macs]: https://github.com/KotlinCrypto/MACs
[url-version-catalog]: https://github.com/KotlinCrypto/version-catalog
[url-pub-xof]: https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
