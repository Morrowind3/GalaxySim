package client.commands;

import client.FileSelectorController;
import core.SupportedFormats;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Locale;

public class SelectFileCommand implements  Command {
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
            String extensionNoCap = "*." + format.toString().toLowerCase();
            String extensionAllCap = "*." + format.toString();
            String extensionInitialCap = "*." + format.toString().charAt(0) + format.toString().toLowerCase().substring(1);

            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionNoCap));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionAllCap));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionInitialCap.toUpperCase()));
        }
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SELECT_FILE;
    }
}
