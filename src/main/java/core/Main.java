package core;

import client.SuperController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        final SuperController viewController  = new SuperController();
        viewController.launchApp(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
