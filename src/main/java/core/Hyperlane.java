package core;


import java.util.List;
import java.util.Observable;


public class Hyperlane extends Observable implements Destructable, Cloneable {
    private Planet planetA;
    private Planet planetB;
    private String colour = "Blue";

    public Hyperlane(Planet planetA, Planet planetB){
        this.planetA = planetA;
        this.planetB = planetB;
        planetA.addHyperlane(this);
        planetB.addHyperlane(this);
    }

    //Pythagorean
    public float getLength(){
        //AB^2 + BC^2 = AC^2
        boolean planetAIsHighest = planetA.getPositionY() > planetB.getPositionY();

        Planet highest = planetAIsHighest ? planetA : planetB;
        Planet lowest = !planetAIsHighest ? planetA : planetB;

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
                planetA = (Planet) planet;
            } else
            if(planet.name.equals(planetB.name)){
                planetB = (Planet) planet;
            }
        }
        setChanged();
    }

    public Planet getOppositePlanet(Planet thisPlanet){
        if(planetA == thisPlanet) return planetB;
        if(planetB == thisPlanet) return planetA;
        return null;
    }

    public String getColour(){
        return colour;
    }

    public void setColour(String colour){
        this.colour = colour;
        setChanged();
    }

    @Override
    public Hyperlane clone(){
        return new Hyperlane(planetA, planetB);
    }

    public Planet getPlanetA(){
        return planetA;
    }

    public Planet getPlanetB() {
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
