@SuppressWarnings("JavaModuleNaming")
module org.kotlincrypto.hash.sha3 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;
    requires transitive org.kotlincrypto.core.xof;
    requires org.kotlincrypto.endians;
    requires org.kotlincrypto.sponges.keccak;

    exports org.kotlincrypto.hash.sha3;
}
