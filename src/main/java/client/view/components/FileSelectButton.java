package client.view.components;

import client.Mediator;
import client.view.FileSelectorController;
import core.SupportedFormats;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSelectButton extends Button implements Component{
    private FileSelectorController controller;

    public FileSelectButton(){
        setText("Select File");
        setPrefSize(100,20);

        EventHandler<ActionEvent> openFileSelection = new EventHandler<ActionEvent>() {
            private void addAcceptedFormats(FileChooser chooser){
                for (SupportedFormats format : SupportedFormats.values()) {
                    String extension = "*." + format.toString();
                    chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extension));
                }
            }

            public void handle(ActionEvent e)
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                addAcceptedFormats(fileChooser);
                File selectedFile = fileChooser.showOpenDialog(controller.getStage());
                if (selectedFile != null) {
                    controller.setDataUrl(selectedFile.getAbsolutePath());
                }
            }
        };
        setOnAction(openFileSelection);
    }

    @Override
    public void setMediator(Mediator mediator) {
        controller = (FileSelectorController) mediator;
    }

    @Override
    public String getName() {
        return "FileSelectButton";
    }
}
