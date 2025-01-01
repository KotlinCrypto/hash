module org.kotlincrypto.hash.blake2 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires org.kotlincrypto.endians;

    exports org.kotlincrypto.hash.blake2;
}
