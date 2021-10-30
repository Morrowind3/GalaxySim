package client.commands;

import client.SimulationController;
import core.MementoKeeper;
import javafx.event.Event;
import javafx.event.EventHandler;

public class RewindCommand implements Command{
    private final SimulationController simulationController;

    public RewindCommand(SimulationController simulationController){
        this.simulationController = simulationController;
    }

    @Override
    public void handle(Event event) {
        simulationController.getMementoKeeper().undo();
        simulationController.buildComponentLists();
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.REWIND;
    }
}
