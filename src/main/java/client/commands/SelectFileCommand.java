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

        //XML detection is broken on Windows, see https://bugs.openjdk.java.net/browse/JDK-8161668
//        if(!System.getProperty("os.name").contains("win")){
//            addAcceptedFormats(fileChooser);
//        }

        File selectedFile = fileChooser.showOpenDialog(controller.getStage());
        if (selectedFile != null) {
            controller.setDataUrl(selectedFile.getAbsolutePath());
        }
    }

    private void addAcceptedFormats(FileChooser chooser){
        for (SupportedFormats format : SupportedFormats.values()) {
            String extension = format.toString();
            String extensionNoCap = "*." + extension.toLowerCase();
            String extensionAllCap = "*." + extension.toUpperCase();
            String extensionInitialCap = "*." + extensionAllCap.charAt(0) + extensionNoCap.substring(1);

            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionNoCap));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionAllCap));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files", extensionInitialCap));
        }
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SELECT_FILE;
    }
}
