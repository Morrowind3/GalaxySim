package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Planet extends CelestialBody {

    private List<Hyperlane> hyperlanes;

    public Planet(String name, String colour) {
        super(name, colour);
        hyperlanes = new ArrayList<>();
    }

    @Override
    public Planet clone(){
        Planet clone = new Planet(name, colour);
        clone.setPosition(positionX, positionY);
        clone.setVelocity(velocityX, velocityY);
        clone.radius = radius;
        clone.hyperlanes = new ArrayList<>(hyperlanes);
        clone.collisionTypes = collisionTypes;

        return clone;
    }

    @Override
    public void prepareForDestruction(){
        for(Hyperlane lane: hyperlanes){
            lane.prepareForDestruction();;
        }
        super.prepareForDestruction();
    }

    public Hyperlane getHyperlaneTo(Planet other){
        for(Hyperlane lane : hyperlanes){
            if(lane.getOppositePlanet(this) == other){
                return lane;
            }
        }
        return null;
    }

    public void addHyperlane(Hyperlane hyperlane) {
        if (hyperlanes.contains(hyperlane)) return;
        hyperlanes.add(hyperlane);
    }

    public void clearHyperlanes(){
        if(hyperlanes != null){
            hyperlanes.clear();
        }
    }
    public List<Hyperlane> getHyperlanes(){
        return hyperlanes;
    }
}

