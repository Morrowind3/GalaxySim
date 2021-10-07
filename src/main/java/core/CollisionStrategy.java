package core;

import core.collisionvisitors.*;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionStrategy {
    private final int width;
    private final int height;

    private final CollisionVisitor[] reusableVisitors = new CollisionVisitor[4];
    private final BounceVisitor bounceVisitor = new BounceVisitor();

    protected final List<CelestialBody> galaxyList;


    public CollisionStrategy(int width, int height, List<CelestialBody> galaxyList){
        this.width = width;
        this.height = height;
        this.galaxyList = galaxyList;
        reusableVisitors[0] = new GrowVisitor();
        reusableVisitors[1] = new DisappearVisitor(galaxyList);
        reusableVisitors[2] = new ExplodeVisitor(galaxyList);
        reusableVisitors[3] = new BlinkVisitor();
    }

    abstract public void checkCollisions();

    protected void collide(CelestialBody collided, CelestialBody collidedWith){
        for(CollisionVisitor visitor: reusableVisitors){
            collided.accept(visitor);
        }
        bounceVisitor.setCollidedWith(collidedWith);
        collided.accept(bounceVisitor);
    }

    protected void checkOutOfBoundsCollision(CelestialBody celestialBody) {
        float correctionDistanceRight = (Math.abs(celestialBody.getCenterX() + celestialBody.getRadius()) % width) + celestialBody.getRadius()*1.8f;
        float correctionDistanceLeft = Math.abs(celestialBody.getPositionX()) % width;
        float correctionDistanceTop = Math.abs(celestialBody.getPositionY()) % height;
        float correctionDistanceBottom = (Math.abs(celestialBody.getCenterY() + celestialBody.getRadius()) % height) + celestialBody.getRadius()*1.8f;

        if (celestialBody.getCenterX() + celestialBody.getRadius() >= width) {
            celestialBody.setPosition(width-correctionDistanceRight, celestialBody.getPositionY());
            celestialBody.invertVelocityX();
        } else
        if (celestialBody.getPositionX() <= 0) {
            celestialBody.setPosition(correctionDistanceLeft, celestialBody.getPositionY());
            celestialBody.invertVelocityX();
        } else
        if (celestialBody.getCenterY() + celestialBody.getRadius() >= height) {
            celestialBody.setPosition(celestialBody.getPositionX(), height-correctionDistanceBottom );
            celestialBody.invertVelocityY();
        } else
        if (celestialBody.getPositionY() <= 0) {
            celestialBody.setPosition(celestialBody.getPositionX(), correctionDistanceTop);
            celestialBody.invertVelocityY();
        }
    }
}
