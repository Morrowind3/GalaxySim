package client.commands;

import client.SimulationController;
import core.CelestialBody;
import core.CelestialBodyTypes;
import javafx.event.Event;

public class RemoveAsteroidCommand implements Command {
    private final SimulationController mediator;
    public RemoveAsteroidCommand(SimulationController mediator){
        this.mediator = mediator;
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.REMOVE_ASTEROID;
    }

    @Override
    public void handle(Event event) {
        CelestialBody toRemove = null;
        for(CelestialBody celestialBody : mediator.getCelestialBodies()){
            if(celestialBody.getType() == CelestialBodyTypes.ASTEROID){
                toRemove = celestialBody;
                break;
            }
        }
        if(toRemove != null){
            mediator.removeFromGalaxy(toRemove);
        }
    }
}
