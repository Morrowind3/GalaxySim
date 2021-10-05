package core.collisionstates;

import core.CelestialBody;

public interface CollisionState {
    void collide(CelestialBody with);
}
