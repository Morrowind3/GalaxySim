package core.collisionstrategy;

import core.CelestialBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TODO: Implement hitbox interface here as well.

public class SimpleCollisionStrategy extends CollisionStrategy implements Cloneable {
    //used to track currently intersecting celestialBodies to avoid repeated collisions

    public SimpleCollisionStrategy(int width, int height, List<CelestialBody> galaxyList) {
        super(width, height, galaxyList);
    }

    @Override
    public CollisionStrategy clone(){
        return new SimpleCollisionStrategy(width, height, galaxyList);
    }


    @Override
    public void checkCollisions() {
        for(int a = 0; a < galaxyList.size(); ++a){
            checkOutOfBoundsCollision(galaxyList.get(a));
            for(int b = a+1; b < galaxyList.size(); ++b){
                CelestialBody first = galaxyList.get(a);
                CelestialBody second = galaxyList.get(b);

                boolean colliding = areColliding(first, second);

                boolean firstIsIntersecting = intersections.get(first) == second;
                boolean secondIsIntersecting = intersections.get(second) == first;

                if(colliding && (!firstIsIntersecting && !secondIsIntersecting)){
                    collide(first, second);
                    collide(second, first);
                    intersections.put(first, second);
                } else if (!colliding && (firstIsIntersecting && secondIsIntersecting)){
                    intersections.remove(first);
                    intersections.remove(second);
                }

            }
        }
    }
}
