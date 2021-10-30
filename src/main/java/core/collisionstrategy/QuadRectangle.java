package core.collisionstrategy;

public class QuadRectangle implements Hitbox {
    private final float x, y, width, height;

    public QuadRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean contains(Hitbox entity){
        boolean crossedBottom = entity.getPositionY() <= y + height;
        boolean crossedLeft = entity.getPositionX() + entity.getWidth() >= x;
        boolean crossedTop = entity.getPositionY() + entity.getHeight() >= y;
        boolean crossedRight = entity.getPositionX() <= x+width;

        return crossedBottom && crossedLeft && crossedRight && crossedTop;
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
    public Float getWidth() {
        return width;
    }

}
