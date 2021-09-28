package client.view;

import client.SuperController;
import client.Mediator;
import client.view.components.Component;
import client.view.components.FileSelectButton;
import client.view.components.LoadButton;
import client.view.components.UrlField;
import javafx.stage.Stage;

public class FileSelectorController implements Mediator {
    private final SuperController superController;
    private FileSelectButton fileSelectButton;
    private LoadButton loadButton;
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
                fileSelectButton = (FileSelectButton)component;
                break;
            case "LoadButton":
                loadButton = (LoadButton)component;
                break;
            case "UrlField":
                urlField = (UrlField)component;
            default:
                break;
        }
    }

}
