package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleCollisionStrategy extends CollisionStrategy implements Cloneable {
    //used to track currently intersecting celestialBodies to avoid repeated collisions
    protected final HashMap<CelestialBody, CelestialBody> intersections;

    public SimpleCollisionStrategy(int width, int height, List<CelestialBody> galaxyList) {
        super(width, height, galaxyList);
        intersections = new HashMap<>();
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

                boolean sameHeight = first.getCenterY() + first.getRadius() >= second.getPositionY() && first.getPositionY() <= second.getCenterY() + second.getRadius();
                boolean sameBreadth = first.getCenterX() + first.getRadius() >= second.getPositionX() && first.getPositionX() <= second.getCenterX() + second.getRadius();
                boolean firstIsIntersecting = intersections.get(first) == second;
                boolean secondIsIntersecting = intersections.get(second) == first;

                if(sameBreadth && sameHeight && (!firstIsIntersecting && !secondIsIntersecting)){
                    collide(first, second);
                    collide(second, first);
                    intersections.put(first, second);
                } else if (!sameBreadth && !sameHeight && (firstIsIntersecting && secondIsIntersecting)){
                    intersections.remove(first);
                    intersections.remove(second);
                }

            }
        }
    }
}
