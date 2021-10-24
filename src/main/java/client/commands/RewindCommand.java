package client.commands;

import core.MementoKeeper;
import javafx.event.Event;
import javafx.event.EventHandler;

public class RewindCommand implements Command{
    private final MementoKeeper mementoKeeper;

    public RewindCommand(MementoKeeper mementoKeeper){
        this.mementoKeeper = mementoKeeper;
    }

    @Override
    public void handle(Event event) {
        mementoKeeper.undo();
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.REWIND;
    }
}
