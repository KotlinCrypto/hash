# CHANGELOG

## Version 0.2.0 (2023-03-12)
 - Adds `SHA-224` algorithm
 - Adds `SHA-384` algorithm
 - Adds `SHA-512/t` algorithm
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
