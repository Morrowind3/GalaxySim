package core.collisionvisitors;

import core.CelestialBody;
import core.CollisionTypes;

import java.util.HashMap;

public class BounceVisitor implements CollisionVisitor {
    private final HashMap<CelestialBody, Integer> bouncyBoiBounceCounter = new HashMap<>();
    private CelestialBody collidedWith;

    public void setCollidedWith(CelestialBody collidedWith){
        this.collidedWith = collidedWith;
    }

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        if(bouncyBoiBounceCounter.get(celestialBody) == null){
            bouncyBoiBounceCounter.put(celestialBody, 1);
        }
        else if(bouncyBoiBounceCounter.get(celestialBody) < 5){
            directionalBounce(celestialBody, collidedWith);
            bouncyBoiBounceCounter.replace(celestialBody, bouncyBoiBounceCounter.get(celestialBody)+1);
        } else {
            celestialBody.removeCollisionType(getType());
            celestialBody.addCollisionType(CollisionTypes.BLINK);
            bouncyBoiBounceCounter.remove(celestialBody);
        }
    }

    private void directionalBounce(CelestialBody thisBody, CelestialBody with){
        boolean sameHeight = thisBody.getCenterY() >= with.getPositionY() && thisBody.getCenterY() <= with.getCenterY() + with.getRadius();
        boolean sameBreadth = thisBody.getCenterX() >= with.getPositionX() && thisBody.getCenterX() <= with.getCenterX() + with.getRadius();

        if(thisBody.getCenterX() + thisBody.getRadius() >= with.getCenterX() - with.getRadius() && sameHeight){
            //stateContext bumps right
            thisBody.invertVelocityX();
        }
        if(thisBody.getPositionX() <= with.getCenterX() + with.getRadius() && sameHeight){
            //stateContext bumps left
            thisBody.invertVelocityX();
        }
        if(thisBody.getPositionY() >= with.getCenterY() + with.getRadius() && sameBreadth){
            //stateContext bumps top
            thisBody.invertVelocityY();
        }
        if(thisBody.getCenterY() + thisBody.getRadius() >= with.getPositionY() && sameBreadth){
            //stateContext bumps bottom
            thisBody.invertVelocityY();
        }
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.BOUNCE;
    }


}
