package client.view.components;

import client.Mediator;
import client.view.FileSelectorController;
import core.SupportedFormats;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class LoadButton extends Button implements Component {
    private FileSelectorController mediator;

    public LoadButton(){
        setPrefSize(100, 20);
        setText("Start");

        EventHandler<ActionEvent> loadSimulation = e -> mediator.loadButtonPressed();
        setOnAction(loadSimulation);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (FileSelectorController) mediator;
    }

    @Override
    public String getName() {
        return "LoadButton";
    }
}
