package client.view;

import client.FileSelectorController;
import client.Mediator;
import client.view.components.*;
import client.view.components.Buttons.LauncherButton;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class FileSelector extends HBox implements Component {
    private FileSelectorController mediator;
    private final LauncherButton fileSelectButton = new LauncherButton("FileSelectButton", "Select File");
    private final LauncherButton loadButton = new LauncherButton("LoadButton", "Start");
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
