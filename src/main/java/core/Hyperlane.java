package core;


public class Hyperlane {
    private Planet planetA;
    private Planet planetB;

    public Hyperlane(Planet planetA, Planet planetB){
        formNewLane(planetA, planetB);
    }

    private void formNewLane(Planet planetA, Planet planetB){
    this.planetA = planetA;
    this.planetB = planetB;
    planetA.addHyperlane(this);
    planetB.addHyperlane(this);
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

}
