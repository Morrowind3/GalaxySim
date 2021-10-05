package core;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionStrategy {
    private final int width;
    private final int height;
    protected List<CelestialBody> intersecting;

    public CollisionStrategy(int width, int height){
        this.width = width;
        this.height = height;
        intersecting = new ArrayList<>();
    }

    abstract public void checkCollisions(List<CelestialBody> toCheck);

    protected void checkOutOfBoundsCollision(CelestialBody celestialBody){
            if(celestialBody.getCenterX() + celestialBody.getRadius() >= width || celestialBody.getCenterX() - celestialBody.getRadius() <= 0){
                celestialBody.invertVelocityX();
            } else
            if(celestialBody.getCenterY() + celestialBody.getRadius() >= height || celestialBody.getCenterY() - celestialBody.getRadius() <= 0){
                celestialBody.invertVelocityY();
            }
    }
}
