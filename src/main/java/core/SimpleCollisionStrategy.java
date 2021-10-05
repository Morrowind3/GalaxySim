package core;

import java.util.ArrayList;
import java.util.List;

public class SimpleCollisionStrategy extends CollisionStrategy {


    public SimpleCollisionStrategy(int width, int height) {
        super(width, height);
    }

    @Override
    public void checkCollisions(List<CelestialBody> toCheck) {
        for(int a = 0; a < toCheck.size(); ++a){
            checkOutOfBoundsCollision(toCheck.get(a));
            for(int b = a+1; b < toCheck.size(); ++b){
                CelestialBody first = toCheck.get(a);
                CelestialBody second = toCheck.get(b);

                boolean sameHeight = first.getCenterY() + first.getRadius() >= second.getPositionY() && first.getPositionY() <= second.getCenterY() + second.getRadius();
                boolean sameBreadth = first.getCenterX() + first.getRadius() >= second.getPositionX() && first.getPositionX() <= second.getCenterX() + second.getRadius();
                boolean firstIsIntersecting = intersecting.contains(first);
                boolean secondIsIntersecting = intersecting.contains(second);

                if(sameBreadth && sameHeight && (!firstIsIntersecting || !secondIsIntersecting)){
                    first.onCollision(second);
                    second.onCollision(first);
                    intersecting.add(first);
                    intersecting.add(second);
                } else if (!sameBreadth && !sameHeight && (firstIsIntersecting && secondIsIntersecting)){
                    while(intersecting.remove(first));
                    while(intersecting.remove(second));
                }

            }
        }
    }
}
