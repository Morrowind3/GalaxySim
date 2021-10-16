package core;

import java.util.ArrayList;

public class Asteroid extends CelestialBody {

    public Asteroid(String name, String colour) {
        super(name, colour);
    }

    @Override
    public CelestialBody clone() {
        Asteroid clone = new Asteroid(name, colour);
        clone.setPosition(positionX, positionY);
        clone.setVelocity(velocityX, velocityY);
        clone.radius = radius;
        clone.collisionTypes = collisionTypes;
        return clone;
    }
}
