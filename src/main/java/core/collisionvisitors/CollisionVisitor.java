package core.collisionvisitors;

import core.CelestialBody;
import core.CollisionTypes;

public interface CollisionVisitor{
    void visitCelestialBody(CelestialBody celestialBody);
    CollisionTypes getType();
}
