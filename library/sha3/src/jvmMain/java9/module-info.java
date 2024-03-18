@SuppressWarnings("JavaModuleNaming")
module org.kotlincrypto.hash.sha3 {
    requires kotlin.stdlib;
    requires org.kotlincrypto.core;
    requires org.kotlincrypto.core.digest;
    requires org.kotlincrypto.core.xof;
    requires org.kotlincrypto.endians;
    requires org.kotlincrypto.sponges.keccak;

    exports org.kotlincrypto.hash.sha3;
}
