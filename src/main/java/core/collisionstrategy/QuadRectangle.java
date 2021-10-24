package core.collisionstrategy;

import java.util.UUID;

public class QuadRectangle implements Hitbox {
    private float x, y, width, height;
    private String id;

    public QuadRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID().toString();
    }

    boolean contains(Hitbox entity){
        boolean crossedBottom = entity.getPositionY() <= y + height;
        boolean crossedLeft = entity.getPositionX() + entity.getWidth() >= x;
        boolean crossedTop = entity.getPositionY() + entity.getHeight() >= y;
        boolean crossedRight = entity.getPositionX() <= x+width;

        return crossedBottom && crossedLeft && crossedRight && crossedTop;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " w: " + width + " h: " + height;
    }

    @Override
    public Float getPositionY() {
        return y;
    }

    @Override
    public Float getPositionX() {
        return x;
    }

    @Override
    public Float getHeight() {
        return height;
    }

    @Override
    public void setHeight(Float h) {
        height = h;
    }

    @Override
    public Float getWidth() {
        return width;
    }

    @Override
    public void setWidth(Float w) {
        width = w;
    }

    @Override
    public String getId() {
        return id;
    }
}
