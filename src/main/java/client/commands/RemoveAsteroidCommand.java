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
        final Asteroid[] toRemove = new Asteroid[1];
        for(CelestialBody celestialBody : mediator.getCelestialBodies()){
            if(celestialBody instanceof Asteroid)
            {
                toRemove[0] = (Asteroid) celestialBody;
                break;
            }
        }
        mediator.getCelestialBodies().remove(toRemove[0]);
    }
}
