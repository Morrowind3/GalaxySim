package core.collisionstrategy;

import core.CelestialBody;

import java.util.List;

public class NullCollisionStrategy extends CollisionStrategy {

    public NullCollisionStrategy(int width, int height, List<CelestialBody> galaxyList) {
        super(0, 0, null);
    }

    @Override
    public void checkCollisions() {
        return;
    }

    @Override
    public CollisionStrategy clone() {
        return this;
    }
}
