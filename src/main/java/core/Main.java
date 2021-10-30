package core;

import client.SuperController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        final SuperController viewController  = new SuperController();
        viewController.launchApp(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
