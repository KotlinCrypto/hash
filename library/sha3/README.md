# Module sha3

`Digest` and `Xof` implementations for SHA3 Hashing

Implementations for:
 - Keccak-224
 - Keccak-256
 - Keccak-384
 - Keccak-512
 - SHA3-224
 - SHA3-256
 - SHA3-384
 - SHA3-512
 - SHAKE128
 - SHAKE256
 - CSHAKE128
 - CSHAKE256
 - ParallelHash128
 - ParallelHash256
 - TupleHash128
 - TupleHash256

Implementations also available for `Hmac` and `KMAC` via [KotlinCrypto/MACs][url-macs].

See [HERE][url-digest-usage] for basic usage example of `Digest`.

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

    // Return 640 bytes instead of the default
    // whenever digest() is called.
    SHAKE256(outputLength = 640)

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

See [HERE][url-xof-usage] for basic usage example of `Xof` (i.e. [Extendable-Output Functions][url-pub-xof]).

```kotlin
fun main() {
    val xof: Xof<SHAKE128> = SHAKE128.xOf()
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

[url-digest-usage]: https://core.kotlincrypto.org/library/digest/index.html
[url-xof-usage]: https://core.kotlincrypto.org/library/xof/index.html
[url-macs]: https://github.com/KotlinCrypto/MACs
[url-pub-xof]: https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.202.pdf
