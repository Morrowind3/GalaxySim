package client.controller;

import client.view.scenes.Launcher;
import core.SupportedFormat;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SimulationMediator {


    public List<String> getInputTypes() {
        List<String> formats = new ArrayList<>();
        for (SupportedFormat format : SupportedFormat.values()) {
            formats.add(format.toString());
        }
        return formats;
    }

    public void launchApp(Stage primaryStage){
        primaryStage.setTitle("Flat Galaxy Simulator 2021");
        primaryStage.setWidth(750);
        primaryStage.setHeight(750);

        Launcher launcher = new Launcher();
        launcher.Show(primaryStage);
    }

}
