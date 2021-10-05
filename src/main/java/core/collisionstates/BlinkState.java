package core.collisionstates;

import core.CelestialBody;

import java.util.Random;

public class BlinkState implements CollisionState {
    private final CelestialBody stateContext;
    private final String originalColour;
    private final Random random;
    public BlinkState(CelestialBody stateContext){
        this.stateContext = stateContext;
        originalColour = stateContext.getColour();
        random = new Random();
    }

    @Override
    public void collide(CelestialBody with) {
        Thread blink = new Thread(() -> {
            if(stateContext.getColour() != originalColour){
                return;
            }
            int nextInt = random.nextInt(0xffffff + 1);
            String randomColourCode = String.format("#%06x", nextInt);
            stateContext.setColour(randomColourCode);
            try {
                Thread.sleep( 500 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stateContext.setColour(originalColour);
            }
        });
        blink.start();
    }
}
