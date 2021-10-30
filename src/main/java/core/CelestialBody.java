package core;

import core.collisionstrategy.Hitbox;
import core.collisionvisitors.Visited;
import core.collisionvisitors.CollisionVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CelestialBody extends Observable implements Destructable, Visited, Cloneable, Hitbox {
    private List<Hyperlane> hyperlanes;
    protected final String name;
    protected Float positionX, positionY;
    protected Float velocityX, velocityY;
    protected Float radius;
    protected String colour;
    private final CelestialBodyTypes type;

    protected List<CollisionTypes> collisionTypes;

    public CelestialBody(String name, String colour, CelestialBodyTypes type) {
        this.name = name;
        this.colour = colour;
        collisionTypes = new ArrayList<>();
        hyperlanes = new ArrayList<>();
        this.type = type;
    }

    @Override
    public CelestialBody clone(){
        CelestialBody clone = new CelestialBody(name, colour, type);
        clone.setPosition(positionX, positionY);
        clone.setVelocity(velocityX, velocityY);
        clone.radius = radius;
        clone.hyperlanes = new ArrayList<>(hyperlanes);
        clone.collisionTypes = collisionTypes;

        return clone;
    }

    @Override
    public void accept(CollisionVisitor visitor){
        for(CollisionTypes type: collisionTypes){
            if(visitor.getType() == type){
                visitor.visitCelestialBody(this);
            }
        }
    }

    public void addCollisionType(CollisionTypes collisionType){
        collisionTypes.add(collisionType);
    }
    public void removeCollisionType(CollisionTypes collisionType){
        collisionTypes.remove(collisionType);
    }

    public void setColour(String colourName){
        colour = colourName;
        setChanged();
    }

    @Override
    public void prepareForDestruction(){
        for(Hyperlane lane: hyperlanes){
            lane.prepareForDestruction();;
        }
    }

    public Hyperlane getHyperlaneTo(CelestialBody other){
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


    public void invertVelocityY(){
        velocityY *= -1;
    }

    public void invertVelocityX(){
        velocityX *= -1;
    }
    public void move(){
        setPosition(positionX + velocityX, positionY + velocityY);
        notifyObservers();
    }

    public void setPosition(Float positionX, Float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        setChanged();
    }
    public void setVelocity(Float velocityX, Float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    public void setRadius(float radius){
        this.radius = radius;
        setChanged();
    }

    public String getName(){
        return name;
    }

    public String getColour() {
        return colour;
    }

    public float getRadius() {
        return radius;
    }

    public Float getPositionX() {
        return positionX;
    }

    public Float getPositionY() {
        return positionY;
    }

    public Float getCenterX() {
        return positionX + getRadius();
    }
    public Float getCenterY() {
        return positionY + getRadius();
    }

    @Override
    public Float getHeight(){
        return getRadius()*2;
    }
    @Override
    public Float getWidth(){
        return getRadius()*2;
    }

    public CelestialBodyTypes getType(){
        return type;
    }
}
