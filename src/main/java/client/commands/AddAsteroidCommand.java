package client.commands;

import client.SimulationController;
import core.CelestialBody;
import core.CelestialBodyBuilder;
import javafx.event.Event;

import java.util.Random;

public class AddAsteroidCommand implements Command {
    private final SimulationController mediator;
    private final int maxX;
    private final int maxY;

    public AddAsteroidCommand(SimulationController mediator, int placementRangeX, int placementRangeY){
        this.mediator = mediator;
        maxX = placementRangeX;
        maxY = placementRangeY;
    }
    @Override
    public CommandNames getCommandName() {
        return CommandNames.ADD_ASTEROID;
    }

    @Override
    public void handle(Event event) {
        CelestialBodyBuilder builder = new CelestialBodyBuilder();
        builder.makeNewGenericAsteroid();
        CelestialBody asteroid = builder.returnCelestialBody();

        final Random random = new Random();
        float randomX = random.nextInt(maxX);
        float randomY = random.nextInt(maxY);
        asteroid.setPosition(randomX, randomY);

        mediator.addToGalaxy(asteroid);
    }
}
