package core;

import core.collisionstates.CollisionState;

public class Planet extends CelestialBody {

    public Planet(String name, String colour, CollisionState initialState) {
        super(name, colour, initialState);
    }
}
