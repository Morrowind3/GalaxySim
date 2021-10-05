package core;

import core.collisionstates.CollisionState;
import core.collisionstates.NullCollisionState;

import java.util.Observable;

public abstract class CelestialBody extends Observable implements Destructable {
    private final String name;
    protected Float positionX, positionY;
    protected Float velocityX, velocityY;
    protected Float radius;
    protected String colour;
    protected CollisionState state;
    protected boolean shouldDestroy;
    protected boolean shouldExplode;

    public CelestialBody(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    public void setCollisionState(CollisionState state){
        this.state = state;
    }

    public void setColour(String colourName){
        this.colour = colourName;
    }

    public void onCollision(CelestialBody with){
        state.collide(with);
    }

    @Override
    public void prepareForDestruction(boolean explosive){
        if(explosive){
            shouldExplode = true;
        }
        shouldDestroy = true;
    }

    public boolean shouldDestroy(){
        return shouldDestroy;
    }

    public boolean shouldExplode(){
        return shouldExplode;
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
