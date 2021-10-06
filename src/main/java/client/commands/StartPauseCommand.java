package client.commands;

import client.SuperController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class StartPauseCommand implements EventHandler<Event>  {
    private final SuperController controller;

    public StartPauseCommand(SuperController controller){
        this.controller = controller;
    }
    @Override
    public void handle(Event event) {
        System.out.println("Start pause command");
    }
}
