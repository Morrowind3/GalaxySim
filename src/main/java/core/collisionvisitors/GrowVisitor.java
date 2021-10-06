package core.collisionvisitors;

import core.CelestialBody;
import core.CollisionTypes;

public class GrowVisitor implements CollisionVisitor {

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        if(celestialBody.getRadius() > 19){
            celestialBody.removeCollisionType(getType());
            celestialBody.addCollisionType(CollisionTypes.EXPLODE);
        } else {
            celestialBody.setRadius(celestialBody.getRadius() + 1);
        }
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.GROW;
    }
}
