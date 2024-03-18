@SuppressWarnings("JavaModuleNaming")
module org.kotlincrypto.hash.sha1 {
    requires kotlin.stdlib;
    requires org.kotlincrypto.core;
    requires org.kotlincrypto.core.digest;

    exports org.kotlincrypto.hash.sha1;
}
