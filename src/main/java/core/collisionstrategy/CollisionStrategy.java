package core.collisionstrategy;

import core.CelestialBody;
import core.collisionvisitors.*;

import java.util.HashMap;
import java.util.List;

public abstract class CollisionStrategy implements Cloneable {
    protected final int width;
    protected final int height;

    private final GrowVisitor growVisitor = new GrowVisitor();
    private DisappearVisitor disappearVisitor;
    private ExplodeVisitor explodeVisitor;
    private final BlinkVisitor blinkVisitor = new BlinkVisitor();
    private final BounceVisitor bounceVisitor = new BounceVisitor();


    protected List<CelestialBody> galaxyList;
    protected HashMap<Hitbox, Hitbox> intersections = new HashMap<>();

    public CollisionStrategy(int width, int height, List<CelestialBody> galaxyList){
        this.galaxyList = galaxyList;
        this.width = width;
        this.height = height;
        explodeVisitor = new ExplodeVisitor(galaxyList);
        disappearVisitor = new DisappearVisitor(galaxyList);
    }

    public void setGalaxyList(List<CelestialBody> galaxyList){
        this.galaxyList = galaxyList;
        explodeVisitor = new ExplodeVisitor(galaxyList);
        disappearVisitor = new DisappearVisitor(galaxyList);
    }

    abstract public void checkCollisions();
    abstract public CollisionStrategy clone();

    protected boolean areColliding(CelestialBody a, CelestialBody b){
//        boolean sameHeight = a.getPositionY() <= b.getCenterY() + b.getRadius() && a.getPositionY() + a.getRadius() >= b.getPositionY();
//        boolean sameBreadth = a.getPositionX() <= b.getCenterX() + b.getRadius() && a.getCenterX() + a.getRadius() >= b.getPositionX();

        boolean sameHeight = a.getCenterY() + a.getRadius() >= b.getPositionY() && a.getPositionY() <= b.getCenterY() + b.getRadius();
        boolean sameBreadth = a.getCenterX() + a.getRadius() >= b.getPositionX() && a.getPositionX() <= b.getCenterX() + b.getRadius();

        return sameBreadth && sameHeight;
    }

    protected void collide(CelestialBody collided, CelestialBody collidedWith){
        bounceVisitor.setCollidedWith(collidedWith);
        collided.accept(bounceVisitor);
        collided.accept(blinkVisitor);
        collided.accept(explodeVisitor);
        collided.accept(disappearVisitor);
        collided.accept(growVisitor);
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
