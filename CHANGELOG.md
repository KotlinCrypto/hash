# CHANGELOG

## Version 0.2.6 (2023-06-08)
 - Updates `kotlincrypto.core` to `0.2.6`

## Version 0.2.5 (2023-06-07)
 - Updates `kotlincrypto.core` to `0.2.5` [[#43]][43]
 - Updates `kotlin` to `1.8.21` [[#43]][43]

## Version 0.2.4 (2023-04-16)
 - Updates `kotlincrypto.core` to `0.2.4`
 - Implements `SHA3` derived functions [[#38]][38]
     - Adds `ParallelHash128`
     - Adds `ParallelHash256`
     - Adds `TupleHash128`
     - Adds `TupleHash256`
 - Adds `outputLength` constructor arguments for `SHA3` `XOF` 
   `Digest` implementations [[#39]][39]

## Version 0.2.3 (2023-04-08)
 - Updates `kotlincrypto.core` to `0.2.3` [[#31]][31]

## Version 0.2.2 (2023-04-07)
 - Implements `SHA3` [[#29]][29]
     - Adds `Keccak-224`
     - Adds `Keccak-256`
     - Adds `Keccak-384`
     - Adds `Keccak-512`
     - Adds `SHA3-224`
     - Adds `SHA3-256`
     - Adds `SHA3-384`
     - Adds `SHA3-512`
     - Adds `SHAKE128`
     - Adds `SHAKE256`
     - Adds `CSHAKE128`
     - Adds `CSHAKE256`
 - Updates `kotlincrypto.core` to `0.2.2`

## Version 0.2.1 (2023-03-28)
 - Updates `kotlincrypto.core` to `0.2.0`
     - Updates the `Digest.compress` function with API change
     - Consumers of `hash` lib are not affected (internal changes only)
     - See [[core #21]][core-21] for more info

## Version 0.2.0 (2023-03-12)
 - Adds `SHA-224`
 - Adds `SHA-384`
 - Adds `SHA-512/t`
 - Combines all `SHA2` algorithms into single `sha2` module
 - Renames `Md5` -> `MD5`
 - Renames `Sha1` -> `SHA1`
 - Renames `Sha256` -> `SHA256`
     - Only in the now deprecated `sha2-256` module
 - Renames `Sha512` -> `SHA512`
     - Only in the now deprecated `sha2-512` module

## Version 0.1.2 (2023-03-06)
 - Updates `kotlincrypto.core` to `0.1.1`
     - Fixes `Digest.update` miscalculation when `offset` parameter is provided

## Version 0.1.1 (2023-03-05)
 - Fixes `copy` visibility for `Digest` implementations
 - Adds a `BOM` publication

## Version 0.1.0 (2023-03-04)
 - Initial Release

[core-21]: https://github.com/KotlinCrypto/core/pull/21
[29]: https://github.com/KotlinCrypto/hash/pull/29
[31]: https://github.com/KotlinCrypto/hash/pull/31
[38]: https://github.com/KotlinCrypto/hash/pull/38
[39]: https://github.com/KotlinCrypto/hash/pull/39
[43]: https://github.com/KotlinCrypto/hash/pull/43
