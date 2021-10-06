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

    protected void checkOutOfBoundsCollision(CelestialBody celestialBody){
            if(celestialBody.getCenterX() + celestialBody.getRadius() >= width || celestialBody.getCenterX() - celestialBody.getRadius() <= 0){
                celestialBody.invertVelocityX();
            } else
            if(celestialBody.getCenterY() + celestialBody.getRadius() >= height || celestialBody.getCenterY() - celestialBody.getRadius() <= 0){
                celestialBody.invertVelocityY();
            }
    }
}
