package core;

import core.collisionstates.CollisionState;

public abstract class CelestialBody {
    private String name;
    private Float positionX, positionY;
    private Float velocityX, velocityY;
    private Float radius;
    private String colour;
    private CollisionState state;

    public CelestialBody(String name, String colour, CollisionState initialState) {
        this.name = name;
        this.colour = colour;
        this.state = initialState;
    }

    public void setPosition(Float positionX, Float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public void setVelocity(Float velocityX, Float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    public void setRadius(float radius){
        this.radius = radius;
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
}
