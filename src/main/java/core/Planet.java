package core;

import core.collisionstates.CollisionState;

import java.util.ArrayList;
import java.util.List;

public class Planet extends CelestialBody {

    private final List<Hyperlane> hyperlanes;

    public Planet(String name, String colour, CollisionState initialState) {
        super(name, colour, initialState);
        hyperlanes = new ArrayList<>();
    }

    public void addHyperlane(Hyperlane hyperlane) {
        if (hyperlanes.contains(hyperlane)) return;
        hyperlanes.add(hyperlane);
    }
    public List<Hyperlane> getHyperlanes(){
        return hyperlanes;
    }
}
