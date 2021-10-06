package core.collisionvisitors;

import core.Asteroid;
import core.CelestialBody;
import core.CelestialBodyBuilder;
import core.CollisionTypes;

import java.util.List;
import java.util.Random;

public class ExplodeVisitor implements CollisionVisitor {
    public List<CelestialBody> simulationList;

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
            asteroid.setVelocity(1f + random.nextFloat(), 1f + random.nextFloat());
            if(random.nextFloat() < 0.5f){
                asteroid.invertVelocityX();
            }
            if(random.nextFloat() < 0.5f){
                asteroid.invertVelocityY();
            }
            float size = celestialBody.getRadius() / (2f + random.nextFloat());
            if(size < 3.5f){
                size = 3.5f;
            }
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
