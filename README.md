# hash
[![badge-license]][url-license]
[![badge-latest-release]][url-latest-release]

[![badge-kotlin]][url-kotlin]
[![badge-core]][url-core]

![badge-platform-android]
![badge-platform-jvm]
![badge-platform-js]
![badge-platform-js-node]
![badge-platform-linux]
![badge-platform-macos]
![badge-platform-ios]
![badge-platform-tvos]
![badge-platform-watchos]
![badge-platform-wasm]
![badge-platform-windows]
![badge-support-android-native]
![badge-support-apple-silicon]
![badge-support-js-ir]
![badge-support-linux-arm]
![badge-support-linux-mips]

Cryptographic hash functions for Kotlin Multiplatform

If you are looking for `Mac` algorithms (e.g. `HmacSHA256`, `HmacSHA512`, etc), see the [MACs repo][url-macs].

If you are looking for `Encoding` (`Base16` a.k.a. `hex`, `Base32`, `Base64`, etc), see the [encoding repo][url-encoding].

If you are looking for `SecureRandom`, see the [secure-random repo][url-secure-random].

### Usage

```kotlin
fun main() {
    val random = Random.Default.nextBytes(615)

    MD5().apply { update(random) }.digest()

    SHA1().digest(random)
}
```

`SHA-2`

```kotlin
fun main() {
    val random = Random.Default.nextBytes(615)

    SHA224().digest(random)
    
    SHA256().apply { update(random) }.digest(random)
    
    SHA384().digest(random)

    val sha512 = SHA512()
    
    val sha512Bytes = sha512.apply {
        update(random[0])
        update(random[20])
        update(random, 25, 88)
    }.digest() // is automatically reset for re-use when digest() is called
    
    // All digests are copyable
    val sha512Copy = sha512.apply { update(random) }.copy()
    val copyBytes = sha512Copy.digest()
    val resetBytes = sha512.reset().digest()
    
    SHA512_224().digest(random)
    SHA512_256().digest(random)
}
```

<!-- TODO: Uncomment
`SHA-3` `Digest`s

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
    SHAKE256()
    val S = "My Customization".encodeToByteArray()
    CSHAKE128(null, S)
    CSHAKE256(null, S)
}
```

`SHA-3` `XOF`s (i.e. [Extendable-Output Functions][url-fips-202]) 

`XOF`s are very similar to `Digest` and `Mac`, except instead of calling `digest()` 
or `doFinal()` which returns a fixed sized `ByteArray`, their output can be however 
long you wish. 

As such, `KotlinCrypto` takes the approach of making them distinctly separate from 
those while implementing the same interfaces (`Algorithm`, `Copyable`, `Resettable`, 
and `Updatable`). The only thing that is different is how the output is retrieved. 

`Xof`s are read, instead. 

More detail can be found in the documentation [HERE][url-xof].

```kotlin
fun main() {
    SHAKE128.xOf()
    SHAKE256.xOf()
    
    val S = "My Customization".encodeToByteArray()
    CSHAKE128.xOf(null, S)

    val xof = CSHAKE256.xOf(null, S)
    
    xof.update(Random.Default.nextBytes(615))
    xof.reset()
    xof.update(Random.Default.nextBytes(615))
    
    val out1 = ByteArray(1_000)
    xof.use(resetXof = false) { read(out1) }
    
    val out2 = ByteArray(out1.size / 2)
    val out3 = ByteArray(out1.size / 2)
    val reader = xof.reader(resetXof = true)
    reader.use { read(out2, 0, out2.size); read(out3) }
    
    assertContentEquals(out1, out2 + out3)
}
```

-->

### Get Started

The best way to keep `KotlinCrypto` dependencies up to date is by using the 
[version-catalog][url-version-catalog]. Alternatively, you can use the BOM as 
shown below.

<!-- TAG_VERSION -->
<!-- TODO: Add
    // Keccak-224, Keccak-256, Keccak-384, Keccak-512
    // SHA3-224, SHA3-256, SHA3-384, SHA3-512
    // SHAKE128, SHAKE256
    // CSHAKE128, CSHAKE256
    implementation("org.kotlincrypto.hash:sha3")
-->

```kotlin
// build.gradle.kts
dependencies {
    // define the BOM and its version
    implementation(platform("org.kotlincrypto.hash:bom:0.2.1"))

    // define artifacts without version
    
    // MD5
    implementation("org.kotlincrypto.hash:md5")

    // SHA-1
    implementation("org.kotlincrypto.hash:sha1")
    
    // SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256
    implementation("org.kotlincrypto.hash:sha2")
}
```

<!-- TAG_VERSION -->
[badge-latest-release]: https://img.shields.io/badge/latest--release-0.2.1-blue.svg?style=flat
[badge-license]: https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat

<!-- TAG_DEPENDENCIES -->
[badge-kotlin]: https://img.shields.io/badge/kotlin-1.8.10-blue.svg?logo=kotlin
[badge-core]: https://img.shields.io/badge/kotlincrypto.core-0.2.0-blue.svg

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
[url-encoding]: https://github.com/05nelsonm/encoding
[url-macs]: https://github.com/KotlinCrypto/MACs
[url-version-catalog]: https://github.com/KotlinCrypto/version-catalog
[url-secure-random]: https://github.com/KotlinCrypto/secure-random
[url-xof]: https://github.com/KotlinCrypto/core/blob/master/library/xof/src/commonMain/kotlin/org/kotlincrypto/core/Xof.kt
[url-fips-202]: https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
