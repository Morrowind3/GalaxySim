package core.collisionstates;

import core.CelestialBody;

public class ExplodeState implements  CollisionState {
    private final CelestialBody stateContext;
    public ExplodeState(CelestialBody stateContext) {
        this.stateContext = stateContext;
    }
    @Override
    public void collide(CelestialBody with) {
        stateContext.prepareForDestruction(true);
    }
}
