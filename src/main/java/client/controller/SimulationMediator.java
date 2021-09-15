package client.controller;

import client.view.scenes.Launcher;
import javafx.stage.Stage;

public class SimulationMediator {

    private Stage primaryStage;

    public SimulationMediator(Stage primaryStage){
        primaryStage.setTitle("Flat Galaxy Simulator 2021");
        primaryStage.setWidth(750);
        primaryStage.setHeight(750);

        this.primaryStage = primaryStage;
    }

    public void showLauncher(){
        Launcher launcher = new Launcher();
        launcher.Show(primaryStage);
    }

}
