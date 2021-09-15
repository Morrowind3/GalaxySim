module GalaxySimulator.main {
    requires javafx.controls;

    opens core to javafx.graphics;

    exports core;
}