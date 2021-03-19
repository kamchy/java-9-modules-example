module generator.test {
    requires generator;
    requires org.junit.jupiter.api;
    requires api;
    exports pl.kamilachyla.bggen.generator to org.junit.platform.commons;
}
