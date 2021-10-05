package core.collisionstates;

import core.CelestialBody;

public class BounceState implements CollisionState{
    private CelestialBody stateContext;
    private int timesBounced = 0;

    public BounceState(CelestialBody stateContext) {
        this.stateContext = stateContext;
    }

    //TODO: fix this after smarter collision detection
    @Override
    public void collide(CelestialBody with) {
        if(timesBounced < 5){
            directionalBounce(with);
            timesBounced++;
        } else {
            stateContext.setCollisionState(new BlinkState(stateContext));
        }
    }

    private void directionalBounce(CelestialBody with){
        boolean sameHeight = stateContext.getCenterY() >= with.getPositionY() && stateContext.getCenterY() <= with.getCenterY() + with.getRadius();
        boolean sameBreadth = stateContext.getCenterX() >= with.getPositionX() && stateContext.getCenterX() <= with.getCenterX() + with.getRadius();

        if(stateContext.getCenterX() + stateContext.getRadius() >= with.getCenterX() - with.getRadius() && sameHeight){
            //stateContext bumps right
            stateContext.invertVelocityX();
        }
        if(stateContext.getPositionX() <= with.getCenterX() + with.getRadius() && sameHeight){
            //stateContext bumps left
            stateContext.invertVelocityX();
        }
        if(stateContext.getPositionY() >= with.getCenterY() + with.getRadius() && sameBreadth){
            //stateContext bumps top
            stateContext.invertVelocityY();
        }
        if(stateContext.getCenterY() + stateContext.getRadius() >= with.getPositionY() && sameBreadth){
            //stateContext bumps bottom
            stateContext.invertVelocityY();
        }
    }
}
