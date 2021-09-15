package client.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FileSelector extends HBox  {

    public FileSelector(){
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        BackgroundFill backgroundFill = new BackgroundFill(Color.CORNFLOWERBLUE, null, null);
        Background bg = new Background(backgroundFill);
        setBackground(bg);

        Button buttonFile = new Button("Select file");
        buttonFile.setPrefSize(100, 20);

        TextField urlField = new TextField();
        urlField.setPrefSize(400, 20);

        Region whiteSpace = new Region();
        HBox.setHgrow(whiteSpace, Priority.ALWAYS);

        Button buttonStart = new Button("Start");
        buttonStart.setPrefSize(100, 20);

        getChildren().addAll(buttonFile, urlField, whiteSpace, buttonStart);
    }

}
