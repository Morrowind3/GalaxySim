package client.commands;

import client.SimulationController;
import core.Asteroid;
import core.CelestialBody;
import javafx.event.Event;

import java.util.List;

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
        Asteroid toRemove = null;
        for(CelestialBody celestialBody : mediator.getCelestialBodies()){
            if(celestialBody instanceof Asteroid)
            {
                toRemove = (Asteroid) celestialBody;
                break;
            }
        }
        if(toRemove != null){
            mediator.removeFromGalaxy(toRemove);
        }
    }
}
