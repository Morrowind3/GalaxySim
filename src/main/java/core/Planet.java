package core;

import java.util.ArrayList;
import java.util.List;

public class Planet extends CelestialBody {

    private final List<Hyperlane> hyperlanes;

    public Planet(String name, String colour) {
        super(name, colour);
        hyperlanes = new ArrayList<>();
    }

    public void prepareForDestruction(boolean explosive){
        for(Hyperlane lane: hyperlanes){
            lane.prepareForDestruction(explosive);;
        }
        super.prepareForDestruction(explosive);
    }

    public void addHyperlane(Hyperlane hyperlane) {
        if (hyperlanes.contains(hyperlane)) return;
        hyperlanes.add(hyperlane);
    }
    public List<Hyperlane> getHyperlanes(){
        return hyperlanes;
    }
}

