package core.collisionstrategy;

public interface Hitbox {


    Float getPositionY();
    Float getPositionX();
    Float getHeight();
    void setHeight(Float h);
    Float getWidth();
    void setWidth(Float w);

    String getId();
}
