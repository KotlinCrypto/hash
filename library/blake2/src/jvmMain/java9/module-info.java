@SuppressWarnings("module")
module org.kotlincrypto.hash.blake2 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;

    exports org.kotlincrypto.hash.blake2;
}
