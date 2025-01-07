@SuppressWarnings("module")
module org.kotlincrypto.hash.sha3 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires transitive org.kotlincrypto.core.xof;
    requires org.kotlincrypto.bitops.endian;
    requires org.kotlincrypto.sponges.keccak;

    exports org.kotlincrypto.hash.sha3;
}
