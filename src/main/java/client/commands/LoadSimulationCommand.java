package client.commands;

import client.FileSelectorController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class LoadSimulationCommand implements EventHandler<Event> {
    private final FileSelectorController controller;

    public LoadSimulationCommand(FileSelectorController controller){
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        controller.loadButtonPressed();
    }
}
