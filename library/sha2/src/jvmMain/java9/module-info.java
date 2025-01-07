@SuppressWarnings("module")
module org.kotlincrypto.hash.sha2 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires org.kotlincrypto.bitops.bits;

    exports org.kotlincrypto.hash.sha2;
}
