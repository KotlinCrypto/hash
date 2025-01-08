module org.kotlincrypto.hash.md {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires org.kotlincrypto.bitops.bits;
    requires org.kotlincrypto.bitops.endian;

    exports org.kotlincrypto.hash.md;
}
