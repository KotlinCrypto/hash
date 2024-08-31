@SuppressWarnings("module")
module org.kotlincrypto.hash.sha2 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;

    exports org.kotlincrypto.hash.sha2;
}
