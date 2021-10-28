module GalaxySimulator.main {
    requires javafx.controls;
    requires java.xml;

    opens core to javafx.graphics;

    exports core;
    exports core.collisionvisitors;
    opens core.collisionvisitors to javafx.graphics;
}