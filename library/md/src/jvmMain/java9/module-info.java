module org.kotlincrypto.hash.md {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires org.kotlincrypto.bitops.bits;

    exports org.kotlincrypto.hash.md;
}
