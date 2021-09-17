package core;

import client.controller.SimulationMediator;
import client.view.scenes.Launcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class GalaxySimulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SimulationMediator simulation = new SimulationMediator();
        simulation.launchApp(primaryStage);
    }
}
