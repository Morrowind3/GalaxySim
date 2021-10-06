package client.commands;

import client.FileSelectorController;
import core.SupportedFormats;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.io.File;

public class SelectFileCommand implements EventHandler<Event> {
    private final FileSelectorController controller;

    public SelectFileCommand(FileSelectorController controller){
        this.controller = controller;
    }

    @Override
    public void handle(Event e)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        addAcceptedFormats(fileChooser);
        File selectedFile = fileChooser.showOpenDialog(controller.getStage());
        if (selectedFile != null) {
            controller.setDataUrl(selectedFile.getAbsolutePath());
        }
    }

    private void addAcceptedFormats(FileChooser chooser){
        for (SupportedFormats format : SupportedFormats.values()) {
            String extension = "*." + format.toString();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extension));
        }
    }

}
