package client.commands;

import client.Mediator;
import client.SimulationController;
import javafx.event.Event;

public class ShowGridCommand implements Command {

    private final SimulationController controller;

    public ShowGridCommand(SimulationController controller){
        this.controller = controller;
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SHOW_GRID;
    }

    @Override
    public void handle(Event event) {
        controller.toggleGrid();
        controller.rerender();
    }
}
