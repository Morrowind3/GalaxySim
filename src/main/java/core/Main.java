package core;

import client.SuperController;
import client.view.FileSelector;
import client.view.SimulationController;
import client.view.SimulationView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        final SuperController viewController  = new SuperController();
        viewController.registerComponent(new FileSelector());
        final SimulationController simController = new SimulationController(viewController);
        simController.registerComponent(new SimulationView());
        viewController.registerSubcontroller(simController);
        viewController.launchApp(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
