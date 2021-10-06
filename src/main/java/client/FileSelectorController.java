package client;

import client.SuperController;
import client.Mediator;
import client.commands.LoadSimulationCommand;
import client.commands.SelectFileCommand;
import client.view.components.*;
import javafx.stage.Stage;

public class FileSelectorController implements Mediator {
    private final SuperController superController;
    private LauncherButton fileSelectButton;
    private LauncherButton loadButton;
    private UrlField urlField;

    private final Stage stage;

    public FileSelectorController(Stage stage, SuperController superController){
        this.stage = stage;
        this.superController = superController;
    }

    public void setDataUrl(String url){
        urlField.setText(url);
    }

    public void loadButtonPressed(){
        superController.loadSimulation(urlField.getText());
    }

    public Stage getStage(){
        return stage;
    }

    public String getName(){
        return "FileSelectorController";
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "FileSelectButton":
                fileSelectButton = (LauncherButton) component;
                fileSelectButton.setCommand(new SelectFileCommand(this));
                break;
            case "LoadButton":
                loadButton = (LauncherButton) component;
                loadButton.setCommand(new LoadSimulationCommand(this));
                break;
            case "UrlField":
                urlField = (UrlField) component;
            default:
                break;
        }
    }

}
