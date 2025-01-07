@SuppressWarnings("module")
module org.kotlincrypto.hash.sha1 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires org.kotlincrypto.bitops.bits;

    exports org.kotlincrypto.hash.sha1;
}
