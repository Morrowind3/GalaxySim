package core.collisionvisitors;

import client.SimulationController;
import core.Asteroid;
import core.CelestialBody;
import core.CelestialBodyBuilder;
import core.CollisionTypes;

import java.util.List;
import java.util.Random;

public class ExplodeVisitor implements CollisionVisitor {
    private final SimulationController controller;
    private final Random random = new Random();
    private final CelestialBodyBuilder builder = new CelestialBodyBuilder();

    public ExplodeVisitor(SimulationController controller){
        this.controller = controller;
    }

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        for(int i = 0; i < 5; ++i){
            builder.makeNewGenericAsteroid();
            Asteroid asteroid = (Asteroid) builder.returnCelestialBody();
            asteroid.setPosition(celestialBody.getPositionX(), celestialBody.getPositionY());

            float size = celestialBody.getRadius() / (2.5f + random.nextFloat()) + random.nextFloat();
            asteroid.setRadius(size);
            controller.addToGalaxy(asteroid);
        }
        controller.removeFromGalaxy(celestialBody);
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.EXPLODE;
    }
}
