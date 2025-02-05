# Module sha2

`Digest` implementations for SHA2 Hashing

Implementations for:
 - SHA-224
 - SHA-256
 - SHA-384
 - SHA-512
 - SHA-512/t

Implementations also available for `Hmac` via [KotlinCrypto/MACs][url-macs].

See [HERE][url-digest-usage] for basic usage example of `Digest`.

```kotlin
fun main() {
    SHA224()
    SHA256()
    SHA384()
    SHA512()

    SHA512t(t = 224) // SHA-512/224
    SHA512t(t = 256) // SHA-512/256
}
```

[url-digest-usage]: https://core.kotlincrypto.org/library/digest/index.html
[url-macs]: https://github.com/KotlinCrypto/MACs
