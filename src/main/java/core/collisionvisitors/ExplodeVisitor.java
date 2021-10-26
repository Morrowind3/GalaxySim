package core.collisionvisitors;

import core.Asteroid;
import core.CelestialBody;
import core.CelestialBodyBuilder;
import core.CollisionTypes;

import java.util.List;
import java.util.Random;

public class ExplodeVisitor implements CollisionVisitor {
    private final List<CelestialBody> simulationList;

    private final Random random = new Random();
    private final CelestialBodyBuilder builder = new CelestialBodyBuilder();

    public ExplodeVisitor(List<CelestialBody> list){
        simulationList = list;
    }

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        for(int i = 0; i < 5; ++i){
            builder.makeNewGenericAsteroid();
            Asteroid asteroid = (Asteroid) builder.returnCelestialBody();
            asteroid.setPosition(celestialBody.getPositionX(), celestialBody.getPositionY());

            float size = celestialBody.getRadius() / (2f + random.nextFloat()) + random.nextFloat();
            asteroid.setRadius(size);
            simulationList.add(asteroid);
        }
        celestialBody.prepareForDestruction();
        simulationList.remove(celestialBody);
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.EXPLODE;
    }
}
