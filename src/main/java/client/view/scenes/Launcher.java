package client.view.scenes;

import client.view.FileSelector;
import client.view.KeyConfigBar;
import core.SupportedFormats;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Launcher{
    private final BorderPane borderPane = new BorderPane();
    private Scene launcherScene;

    public Scene getScene(){
        return launcherScene;
    }

    public void setCenterCanvas(Canvas simulationView){
        borderPane.setCenter(simulationView);
    }

    public void Show(Stage stage, KeyConfigBar keyConfigBar, FileSelector selector){
        final StringBuilder welcomeStringBuilder = new StringBuilder("Welcome to the Flat Galaxy Simulator! \nPlease provide a valid resource for planets.\n")
                .append("\nThe following formats are supported:");
        for (SupportedFormats format : SupportedFormats.values()) {
            welcomeStringBuilder.append("\n -").append(format.toString());
        }

        final Text welcomeMessage = new Text();
        welcomeMessage.setText(welcomeStringBuilder.toString());
        welcomeMessage.setFont(Font.font ("Verdana", 20));

        borderPane.setTop(keyConfigBar);
        borderPane.setCenter(welcomeMessage);
        borderPane.setBottom(selector);

        launcherScene = new Scene(borderPane);
        stage.setScene(launcherScene);
        stage.show();
    }
}
