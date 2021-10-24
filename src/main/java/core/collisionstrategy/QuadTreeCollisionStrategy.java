package core.collisionstrategy;

import core.CelestialBody;
import java.util.List;

//TODO: Replace all celestial body references with hitbox references.

public class QuadTreeCollisionStrategy extends CollisionStrategy {

    QuadTree quadTree;
    QuadRectangle rootRectangle;

    public QuadTreeCollisionStrategy(int width, int height, List<CelestialBody> galaxyList) {
        super(width, height, galaxyList);
        rootRectangle = new QuadRectangle(0, 0, width, height);
        quadTree = new QuadTree(rootRectangle);
        setGalaxyList(galaxyList);
    }

    //TODO: Insertion isnt going right
    @Override
    public void setGalaxyList(List<CelestialBody> galaxyList){
        this.galaxyList = galaxyList;
        quadTree.rebuildTree(galaxyList);
    }

    public QuadTree getQuadTree(){
        return quadTree;
    }

    @Override
    public void checkCollisions() {
        quadTree.rebuildTree(galaxyList);

        for(Hitbox entity : galaxyList){
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
        QuadTreeCollisionStrategy clone = new QuadTreeCollisionStrategy(width, height, galaxyList);
        clone.quadTree = quadTree.clone();
        clone.intersections = intersections;
        return clone;
    }
}
