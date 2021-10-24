package core;

import core.collisionstrategy.Hitbox;
import core.collisionvisitors.Visited;
import core.collisionvisitors.CollisionVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

public abstract class CelestialBody extends Observable implements Destructable, Visited, Cloneable, Hitbox {
    private final String id;
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

        this.id = UUID.randomUUID().toString();
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

    @Override
    public Float getHeight(){
        return getRadius()*2;
    }
    @Override
    public void setHeight(Float h){
        radius = h/2;
    }
    @Override
    public Float getWidth(){
        return getRadius()*2;
    }
    @Override
    public void setWidth(Float w){
        radius = w/2;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || o.getClass() != getClass()) return false;
        CelestialBody compare = (CelestialBody) o;
        return name.equals(compare.name)
                && radius.equals(compare.radius) && positionX.equals(compare.positionX)
                && positionY.equals(compare.positionY) && velocityX.equals(compare.velocityX)
                && velocityY.equals(compare.velocityY);
    }

    @Override
    public String getId(){
        return id;
    }
}
