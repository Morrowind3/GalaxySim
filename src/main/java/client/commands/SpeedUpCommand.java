package client.commands;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SpeedUpCommand implements EventHandler<Event> {
    @Override
    public void handle(Event event) {
        System.out.print("Speed up!");
    }
}
