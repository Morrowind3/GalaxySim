package core;

import core.collisionstates.CollisionState;

import java.util.Observable;

public abstract class CelestialBody extends Observable {
    protected String name;
    protected Float positionX, positionY;
    protected Float velocityX, velocityY;
    protected Float radius;
    protected String colour;
    protected CollisionState state;

    public CelestialBody(String name, String colour, CollisionState initialState) {
        this.name = name;
        this.colour = colour;
        this.state = initialState;
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
