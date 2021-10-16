package core;


import java.util.List;

public class Hyperlane implements Destructable, Cloneable {
    private Planet planetA;
    private Planet planetB;

    public Hyperlane(Planet planetA, Planet planetB){
        this.planetA = planetA;
        this.planetB = planetB;
        planetA.addHyperlane(this);
        planetB.addHyperlane(this);
    }

    public void resyncPlanets(List<CelestialBody> galaxyList){
        for(CelestialBody planet : galaxyList){
            if(planet.name.equals(planetA.name)){
                planetA = (Planet) planet;
            } else
            if(planet.name.equals(planetB.name)){
                planetB = (Planet) planet;
            }
        }
    }

    @Override
    public Hyperlane clone(){
        return new Hyperlane(planetA, planetB);
    }

    public boolean containsPlanet(Planet planet){
        return (planetA == planet || planetB == planet);
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
