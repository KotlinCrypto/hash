# CHANGELOG

## Version 0.4.0 (2023-11-30)
 - Updates `kotlincrypto.core` to `0.4.0` [[#53]][53]
 - Updates `kotlincrypto.endians` to `0.2.0` [[#53]][53]
 - Updates `kotlincrypto.sponges` to `0.2.0` [[#53]][53]
 - Updates `kotlin` to `1.9.21` [[#53]][53]
 - Drops support for the following deprecated targets:
     - `iosArm32`
     - `watchosX86`
     - `linuxArm32Hfp`
     - `linuxMips32`
     - `linuxMipsel32`
     - `mingwX86`
     - `wasm32`

## Version 0.3.0 (2023-06-28)
 - Fixes JPMS split packages [[#49]][49]
     - **API BREAKING CHANGES**
     - `org.kotlincrypto.hash.Sha1` typealias was removed
 - The MavenCentral dependency `org.kotlincrypto.hash:md5` is now deprecated, 
   in favor of `org.kotlincrypto.hash:md`
     - `md5` dependency now simply provides the `md` dependency and 
       will continue to be published until the next major version release.
     - This was done in order to minimize breakage while still fixing 
       the underlying JPMS issues.
 - The following MavenCentral dependencies (previously deprecated) have 
   been removed from publication [[#50]][50]
     - `org.kotlincrypto.hash:sha2-256`
     - `org.kotlincrypto.hash:sha2-512`
 - See the [ANNOUNCEMENT][discussion-3] for more information on `0.3.0` release

## Version 0.2.7 (2023-06-09)
 - Updates `kotlincrypto.core` to `0.2.7`

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

[discussion-3]: https://github.com/orgs/KotlinCrypto/discussions/3
[core-21]: https://github.com/KotlinCrypto/core/pull/21
[29]: https://github.com/KotlinCrypto/hash/pull/29
[31]: https://github.com/KotlinCrypto/hash/pull/31
[38]: https://github.com/KotlinCrypto/hash/pull/38
[39]: https://github.com/KotlinCrypto/hash/pull/39
[43]: https://github.com/KotlinCrypto/hash/pull/43
[49]: https://github.com/KotlinCrypto/hash/pull/49
[50]: https://github.com/KotlinCrypto/hash/pull/50
[53]: https://github.com/KotlinCrypto/hash/pull/53
