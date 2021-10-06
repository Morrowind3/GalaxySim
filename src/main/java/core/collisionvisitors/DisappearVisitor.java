package core.collisionvisitors;

import core.CelestialBody;
import core.CollisionTypes;

import java.util.List;

public class DisappearVisitor implements CollisionVisitor {
    public List<CelestialBody> simulationList;

    public DisappearVisitor(List<CelestialBody> list){
        simulationList = list;
    }

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        simulationList.remove(celestialBody);
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.DISAPPEAR;
    }
}
