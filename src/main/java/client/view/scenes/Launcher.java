package client.view.scenes;

import client.view.components.FileSelector;
import core.SupportedFormat;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Launcher {

    public void Show(Stage stage){
        StringBuilder welcomeStringBuilder = new StringBuilder("Welcome to the Flat Galaxy Simulator! \nPlease provide a valid resource for planets.\n")
                .append("\nThe following formats are supported:");

        for (SupportedFormat format : SupportedFormat.values()) {
            welcomeStringBuilder.append("\n -").append(format);
        }

        Text welcomeMessage = new Text();
        welcomeMessage.setText(welcomeStringBuilder.toString());
        welcomeMessage.setFont(Font.font ("Verdana", 20));

        final BorderPane borderPane = new BorderPane();
        final FileSelector fileSelector = new FileSelector();

        borderPane.setBottom(fileSelector);
        borderPane.setCenter(welcomeMessage);

        Scene launcherScene = new Scene(borderPane);
        stage.setScene(launcherScene);
        stage.show();
    }

}
