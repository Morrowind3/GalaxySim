package client.commands;

import client.SimulationController;
import javafx.event.Event;

public class ShowPlanetNamesCommand implements Command {
    private final SimulationController controller;

    public ShowPlanetNamesCommand(SimulationController controller){
        this.controller = controller;
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SHOW_PLANET_NAMES;
    }

    @Override
    public void handle(Event event) {
        controller.togglePlanetNames();
        controller.rerender();
    }
}
