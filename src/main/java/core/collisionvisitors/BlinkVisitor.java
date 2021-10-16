package core.collisionvisitors;

import core.CelestialBody;
import core.CollisionTypes;

import java.util.*;

public class BlinkVisitor implements CollisionVisitor {
    private final Random random = new Random();
    private final HashMap<CelestialBody, String> originalColours = new HashMap<>();

    @Override
    public void visitCelestialBody(CelestialBody celestialBody) {
        if(!originalColours.containsKey(celestialBody)){
            originalColours.put(celestialBody, celestialBody.getColour());
        }
        Thread blink = new Thread(() -> {
            int nextInt = random.nextInt(0xffffff + 1);
            String randomColourCode = String.format("#%06x", nextInt);
            celestialBody.setColour(randomColourCode);
            try {
                Thread.sleep( 500 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                celestialBody.setColour(originalColours.get(celestialBody));
            }
        });
        blink.start();
    }

    @Override
    public CollisionTypes getType() {
        return CollisionTypes.BLINK;
    }
}
