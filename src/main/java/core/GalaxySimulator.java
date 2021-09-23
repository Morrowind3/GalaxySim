package core;

import client.InterfaceController;
import client.view.FileSelector;
import client.view.SimulationView;
import javafx.application.Application;
import javafx.stage.Stage;

public class GalaxySimulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        InterfaceController interfaceController = new InterfaceController();
        interfaceController.registerComponent(new FileSelector());
        interfaceController.registerComponent(new SimulationView());
        interfaceController.launchApp(primaryStage);
    }
}
