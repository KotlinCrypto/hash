@SuppressWarnings("JavaModuleNaming")
module org.kotlincrypto.hash.sha1 {
    requires kotlin.stdlib;
    requires transitive org.kotlincrypto.core.digest;

    exports org.kotlincrypto.hash.sha1;
}
