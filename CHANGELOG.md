# CHANGELOG

## Version 0.8.0 (2025-09-19)
 - Updates `kotlin` to `2.2.20` [[#118]][118]
 - Updates `kotlincrypto.bitops` to `0.3.0` [[#118]][118]
 - Updates `kotlincrypto.core` to `0.8.0` [[#118]][118]
 - Updates `kotlincrypto.sponges` to `0.5.0` [[#118]][118]
 - Lower supported `KotlinVersion` to `1.8` [[#119]][119]

## Version 0.7.1 (2025-08-25)
 - Updates `kotlin` to `2.2.10` [[#117]][117]
 - Updates `kotlincrypto.bitops` to `0.2.1`
 - Updates `kotlincrypto.core` to `0.7.1`
 - Updates `kotlincrypto.sponges` to `0.4.1`

## Version 0.7.0 (2025-02-25)
 - Updates `kotlin` to `2.1.10` [[#111]][111]
 - Updates `kotlincrypto.bitops` to `0.2.0` [[#111]][111]
 - Updates `kotlincrypto.core` to `0.7.0` [[#111]][111]
 - Updates `kotlincrypto.sponges` to `0.4.0` [[#111]][111]
 - The following now throw `InvalidParameterException` instead of `IllegalArgumentException` on 
   instantiation when a parameter is inappropriate [[#111]][111]:
     - `blake2/BLAKE2b`
     - `blake2/BLAKE2s`
     - `sha2/SHA512t`
     - `sha3/CSHAKE128`
     - `sha3/CSHAKE256`
     - `sha3/ParallelHash128`
     - `sha3/ParallelHash256`
     - `sha3/SHAKE128`
     - `sha3/SHAKE256`
     - `sha3/TuppleHash128`
     - `sha3/TuppleHash256`

## Version 0.6.1 (2025-02-09)
 - Updates `kotlincrypto.bitops` to `0.1.2` [[#104]][104]
 - Updates `kotlincrypto.core` to `0.6.1`
 - Use `Counter.Bit64` instead of `Counter.Bit32` for `SHA384`, `SHA512` and `SHA512t` [[#104]][104]
 - Implements new `Digest.digestInto` API for all implementations [[#108]][108]
 - Adds `dokka` documentation at `https://hash.kotlincrypto.org` [[#109]][109]

## Version 0.6.0 (2025-01-15)
 - Adds `BLAKE2b` and `BLAKE2s` hashing.
 - Updates all implementations to conform to `KotlinCrypto.core` `0.6.0` modifications 
   to `Digest` internal API.
 - All implementations now define a proper return type for `copy` (instead of `Digest`).
 - Replaces usage of `KotlinCrypto.endians` library (deprecated) with `KotlinCrypto.bitops`.
 - Removes all `@Throws` annotations from constructors (it is documented).
 - Deprecates `SHA512_224` and `SHA512_256` top-level functions.
 - Performance improvements to `compressProtected` implementations for `MD5` and `SHA1`.
 - Removes unnecessary usage of a buffer by `ParallelDigest`.
 - `xOf` functions with no parameters for `ParallelHash` and `TupleHash` implementations 
   are now inlined.

## Version 0.5.6 (2024-12-28)
 - Updates `kotlincrypto.sponges` to `0.3.4` [[#74]][74]
     - See [ANNOUNCEMENT #75][75-discussion]

## Version 0.5.5 (2024-12-20)
 - Updates `kotlincrypto.core` to `0.5.5`
 - Updates `kotlincrypto.sponges` to `0.3.3`
 - Fixes `sha3` performance issues for Jvm [[#71]][71]

## Version 0.5.4 (2024-12-19)
 - Updates `kotlincrypto.core` to `0.5.4`
 - Updates `kotlincrypto.sponges` to `0.3.2`
 - Adds benchmarking to repository [[#69]][69]
     - Benchmark comparisons using `core` and `sponges` performance 
       improvements can be viewed in [comment PR #70][70-comment]
     - TL;DR huge gains for `sha3`

## Version 0.5.3 (2024-08-31)
 - Updates `kotlincrypto.core` to `0.5.3`
 - Updates `kotlincrypto.endians` to `0.3.1`
 - Updates `kotlincrypto.sponges` to `0.3.1`
 - Updates `kotlin` to `1.9.24` [[#65]][65]
 - Fixes multiplatform metadata manifest `unique_name` parameter for 
   all source sets to be truly unique. [[#65]][65]
 - Updates jvm `.kotlin_module` with truly unique file name. [[#65]][65]

## Version 0.5.1 (2024-03-18)
- Updates `kotlincrypto.core` to `0.5.1` [[#58]][58]
- Updates `kotlincrypto.endians` to `0.3.0` [[#58]][58]
- Updates `kotlincrypto.sponges` to `0.3.0` [[#58]][58]
- Updates `kotlin` to `1.9.23` [[#58]][58]
 - Add experimental support for `wasmJs` & `wasmWasi` [[#58]][58]
 - Add support for Java9 `JPMS` via Multi-Release jar [[#59]][59]

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
 - See the [ANNOUNCEMENT #3][3-discussion] for more information on `0.3.0` release

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
     - See [[core PR #21]][21-core] for more info

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

[3-discussion]: https://github.com/orgs/KotlinCrypto/discussions/3
[21-core]: https://github.com/KotlinCrypto/core/pull/21
[29]: https://github.com/KotlinCrypto/hash/pull/29
[31]: https://github.com/KotlinCrypto/hash/pull/31
[38]: https://github.com/KotlinCrypto/hash/pull/38
[39]: https://github.com/KotlinCrypto/hash/pull/39
[43]: https://github.com/KotlinCrypto/hash/pull/43
[49]: https://github.com/KotlinCrypto/hash/pull/49
[50]: https://github.com/KotlinCrypto/hash/pull/50
[53]: https://github.com/KotlinCrypto/hash/pull/53
[58]: https://github.com/KotlinCrypto/hash/pull/58
[59]: https://github.com/KotlinCrypto/hash/pull/59
[65]: https://github.com/KotlinCrypto/hash/pull/65
[69]: https://github.com/KotlinCrypto/hash/pull/69
[70-comment]: https://github.com/KotlinCrypto/hash/pull/70#issuecomment-2554683182
[71]: https://github.com/KotlinCrypto/hash/pull/71
[74]: https://github.com/KotlinCrypto/hash/pull/74
[75-discussion]: https://github.com/KotlinCrypto/hash/discussions/75
[104]: https://github.com/KotlinCrypto/hash/pull/104
[108]: https://github.com/KotlinCrypto/hash/pull/108
[109]: https://github.com/KotlinCrypto/hash/pull/109
[111]: https://github.com/KotlinCrypto/hash/pull/111
[117]: https://github.com/KotlinCrypto/hash/pull/117
[118]: https://github.com/KotlinCrypto/hash/pull/118
[119]: https://github.com/KotlinCrypto/hash/pull/119
