package core;

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
        return clone;
    }


}
