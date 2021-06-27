module fxclient {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;
    requires java.desktop;
    requires generator;
    requires api;
    opens com.kamilachyla.fxclient.gui to javafx.graphics;
}