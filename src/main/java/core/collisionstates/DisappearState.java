package core.collisionstates;

import core.CelestialBody;

public class DisappearState implements  CollisionState {
    private final CelestialBody stateContext;
    public DisappearState(CelestialBody stateContext) {
        this.stateContext = stateContext;
    }
    @Override
    public void collide(CelestialBody with) {
        stateContext.prepareForDestruction();
    }
}
