package core.collisionstates;

import core.CelestialBody;

public class GrowState implements  CollisionState {
    private CelestialBody stateContext;
    public GrowState(CelestialBody stateContext) {
        this.stateContext = stateContext;
    }
    @Override
    public void collide(CelestialBody with) {
        if(stateContext.getRadius() > 19){
            stateContext.setCollisionState(new ExplodeState(stateContext));
        } else {
            stateContext.setRadius(stateContext.getRadius() + 1);
        }
    }
}
