package client.commands;

import javafx.event.Event;
import javafx.event.EventHandler;

public interface Command extends EventHandler<Event> {
    CommandNames getCommandName();
}
