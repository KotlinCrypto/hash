# Module blake2

`Digest` implementations for BLAKE2 Hashing

Implementations for:
 - BLAKE2b
 - BLAKE2s

Implementations also available as `Mac` via [KotlinCrypto/MACs][url-macs] for keyed hashing.

See [HERE][url-digest-usage] for basic usage example of `Digest`.

```kotlin
fun main() {
    BLAKE2b(512)
    BLAKE2s(256)
}
```

[url-digest-usage]: https://core.kotlincrypto.org/library/digest/index.html
[url-macs]: https://github.com/KotlinCrypto/MACs
