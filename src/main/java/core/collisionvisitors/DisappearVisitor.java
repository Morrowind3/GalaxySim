package core.collisionvisitors;

import client.SimulationController;
import core.CelestialBody;
import core.CollisionTypes;

import java.util.List;

public class DisappearVisitor implements CollisionVisitor {
    private final SimulationController controller;

    public DisappearVisitor(SimulationController controller){
        this.controller = controller;
    }

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        controller.removeFromGalaxy(celestialBody);
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.DISAPPEAR;
    }
}
