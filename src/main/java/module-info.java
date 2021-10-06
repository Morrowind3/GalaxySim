module GalaxySimulator.main {
    requires javafx.controls;

    opens core to javafx.graphics;

    exports core;
    exports core.collisionvisitors;
    opens core.collisionvisitors to javafx.graphics;
}