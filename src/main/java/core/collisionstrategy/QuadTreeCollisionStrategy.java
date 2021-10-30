package core.collisionstrategy;

import client.SimulationController;
import core.CelestialBody;
import java.util.List;

//TODO: Replace all celestial body references with hitbox references.

public class QuadTreeCollisionStrategy extends CollisionStrategy {

    QuadTree quadTree;
    QuadRectangle rootRectangle;

    public QuadTreeCollisionStrategy(int width, int height, SimulationController controller) {
        super(width, height, controller);
        rootRectangle = new QuadRectangle(0, 0, width, height);
        quadTree = new QuadTree(rootRectangle);
    }



    public QuadTree getQuadTree(){
        return quadTree;
    }

    @Override
    public void checkCollisions() {
        quadTree.rebuildTree(controller.getCelestialBodies());

        for(Hitbox entity : controller.getCelestialBodies()){
            checkOutOfBoundsCollision((CelestialBody) entity);
        }

        List<Hitbox> all = quadTree.getQuadEntities(rootRectangle);
        for (Hitbox thisEntity : all) {

            List<QuadRectangle> entityQuads = quadTree.getIntersectedQuads(thisEntity);
            for (QuadRectangle quad : entityQuads) {
                List<Hitbox> nearbyEntities = quadTree.getQuadEntities(quad);
                for (Hitbox nearby : nearbyEntities) {
                    if (nearby.getId().equals(thisEntity.getId())) continue;
                    boolean collision = areColliding((CelestialBody) nearby, (CelestialBody) thisEntity);

                    boolean firstIsIntersecting = intersections.get(thisEntity) == nearby;
                    boolean secondIsIntersecting = intersections.get(nearby) == thisEntity;

                    if (collision && (!firstIsIntersecting && !secondIsIntersecting)) {
                        collide((CelestialBody) thisEntity, (CelestialBody) nearby);
                        intersections.put(thisEntity, nearby);
                    } else if (!collision && (firstIsIntersecting && secondIsIntersecting)) {
                        intersections.remove(thisEntity);
                        intersections.remove(nearby);
                    }
                }
            }
        }
    }

    @Override
    public CollisionStrategy clone() {
        QuadTreeCollisionStrategy clone = new QuadTreeCollisionStrategy(width, height, controller);
        clone.quadTree = quadTree.clone();
        clone.intersections = intersections;
        return clone;
    }
}
