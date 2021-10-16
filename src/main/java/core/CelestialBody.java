package core;

import core.collisionvisitors.Visited;
import core.collisionvisitors.CollisionVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class CelestialBody extends Observable implements Destructable, Visited, Cloneable {
    protected final String name;
    protected Float positionX, positionY;
    protected Float velocityX, velocityY;
    protected Float radius;
    protected String colour;
    protected List<CollisionTypes> collisionTypes;

    public CelestialBody(String name, String colour) {
        this.name = name;
        this.colour = colour;
        collisionTypes = new ArrayList<>();
    }

    public abstract CelestialBody clone();

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
        this.colour = colourName;
        setChanged();
    }

    @Override
    public void prepareForDestruction(){
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
}
