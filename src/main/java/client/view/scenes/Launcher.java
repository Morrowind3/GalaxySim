package client.view.scenes;

import client.view.FileSelector;
import client.view.SimulationController;
import client.view.SimulationView;
import client.view.components.Component;
import core.SupportedFormats;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    final BorderPane borderPane = new BorderPane();

    public void LaunchSimulation(SimulationView simulationView){
        borderPane.setCenter(simulationView);
    }

    public void Show(Stage stage, FileSelector selector){
        StringBuilder welcomeStringBuilder = new StringBuilder("Welcome to the Flat Galaxy Simulator! \nPlease provide a valid resource for planets.\n")
                .append("\nThe following formats are supported:");
        for (SupportedFormats format : SupportedFormats.values()) {
            welcomeStringBuilder.append("\n -").append(format.toString());
        }

        Text welcomeMessage = new Text();
        welcomeMessage.setText(welcomeStringBuilder.toString());
        welcomeMessage.setFont(Font.font ("Verdana", 20));

        borderPane.setBottom(selector);
        borderPane.setCenter(welcomeMessage);

        Scene launcherScene = new Scene(borderPane);
        stage.setScene(launcherScene);
        stage.show();
    }

}
