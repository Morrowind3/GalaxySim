package client.commands;

import javafx.event.Event;

public class QuickestrouteCommand implements Command{
    @Override
    public CommandNames getCommandName() {
        return CommandNames.QUICKEST_ROUTE;
    }

    @Override
    public void handle(Event event) {

    }
}
