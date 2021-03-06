package core;


import java.util.List;
import java.util.Observable;


public class Hyperlane extends Observable implements Destructable, Cloneable {
    private CelestialBody planetA;
    private CelestialBody planetB;

    public Hyperlane(CelestialBody planetA, CelestialBody planetB){
        this.planetA = planetA;
        this.planetB = planetB;
        planetA.addHyperlane(this);
        planetB.addHyperlane(this);
    }

    //Pythagorean
    public float getLength(){
        //AB^2 + BC^2 = AC^2
        boolean planetAIsHighest = planetA.getPositionY() > planetB.getPositionY();

        CelestialBody highest = planetAIsHighest ? planetA : planetB;
        CelestialBody lowest = !planetAIsHighest ? planetA : planetB;

        float highX = highest.getCenterX();
        float highY = highest.getCenterY();
        float lowX = lowest.getCenterX();
        float lowY = lowest.getCenterY();


        //TODO: I don't think this matters.
        boolean triangleInversed = highest.getCenterX() < lowest.getCenterX();
        float cX = triangleInversed ? lowest.getCenterX() : highest.getCenterX();
        float cY = triangleInversed ? highest.getCenterY() : lowest.getCenterY();

        float a = Math.abs(lowX-cX);
        float b = Math.abs(highY-cY);
        float c = (float) Math.sqrt((a*a) + (b*b));

        return c;
    }


    public void resyncPlanets(List<CelestialBody> galaxyList){
        for(CelestialBody planet : galaxyList){
            if(planet.name == null) continue;
            if(planet.name.equals(planetA.name)){
                planetA = planet;
            } else
            if(planet.name.equals(planetB.name)){
                planetB = planet;
            }
        }
        setChanged();
    }

    public CelestialBody getOppositePlanet(CelestialBody thisPlanet){
        if(planetA == thisPlanet) return planetB;
        if(planetB == thisPlanet) return planetA;
        return null;
    }

    @Override
    public Hyperlane clone(){
        return new Hyperlane(planetA, planetB);
    }

    public CelestialBody getPlanetA(){
        return planetA;
    }

    public CelestialBody getPlanetB() {
        return planetB;
    }

    public float[] getHyperlaneEndA(){
        float[] coords = new float[2];
        coords[0] = planetA.getCenterX();
        coords[1] = planetA.getCenterY();
        return coords;
    }
    public float[] getHyperlaneEndB(){
        float[] coords = new float[2];
        coords[0] = planetB.getCenterX();
        coords[1] = planetB.getCenterY();

        return coords;
    }

    @Override
    public void prepareForDestruction() {
        planetA.getHyperlanes().remove(this);
        planetB.getHyperlanes().remove(this);
    }
}
