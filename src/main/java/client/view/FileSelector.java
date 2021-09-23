package client.view;

import client.InterfaceController;
import client.Mediator;
import client.view.components.Component;
import client.view.components.FileSelectButton;
import client.view.components.LoadButton;
import client.view.components.UrlField;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class FileSelector extends HBox implements Component {
    private FileSelectorController mediator;
    private final FileSelectButton fileSelectButton = new FileSelectButton();
    private final LoadButton loadButton = new LoadButton();
    private final UrlField urlField = new UrlField();


    public FileSelector(){
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        BackgroundFill backgroundFill = new BackgroundFill(Color.CORNFLOWERBLUE, null, null);
        Background bg = new Background(backgroundFill);
        setBackground(bg);

        Region whiteSpace = new Region();
        HBox.setHgrow(whiteSpace, Priority.ALWAYS);
        getChildren().addAll(fileSelectButton, urlField, whiteSpace, loadButton);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (FileSelectorController) mediator;
        this.mediator.registerComponent(fileSelectButton);
        this.mediator.registerComponent(loadButton);
        this.mediator.registerComponent(urlField);
    }

    @Override
    public String getName() {
        return "FileSelector";
    }
}
