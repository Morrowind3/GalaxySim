package core.collisionstrategy;

import client.SimulationController;
import core.CelestialBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TODO: Implement hitbox interface here as well.

public class SimpleCollisionStrategy extends CollisionStrategy implements Cloneable {

    public SimpleCollisionStrategy(int width, int height, SimulationController controller) {
        super(width, height, controller);
    }

    @Override
    public CollisionStrategy clone(){
        return new SimpleCollisionStrategy(width, height, controller);
    }


    @Override
    public void checkCollisions() {
        for(int a = 0; a < controller.getCelestialBodies().size(); ++a){
            checkOutOfBoundsCollision(controller.getCelestialBodies().get(a));
            for(int b = a+1; b < controller.getCelestialBodies().size(); ++b){
                CelestialBody first = controller.getCelestialBodies().get(a);
                CelestialBody second = controller.getCelestialBodies().get(b);

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
