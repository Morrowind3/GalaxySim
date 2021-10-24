package client.commands;

import client.FileSelectorController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class LoadSimulationCommand implements Command {
    private final FileSelectorController controller;

    public LoadSimulationCommand(FileSelectorController controller){
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        controller.loadButtonPressed();
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.LOAD_SIMULATION;
    }
}
