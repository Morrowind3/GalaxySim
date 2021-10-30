package core;

import core.exceptions.InvalidDataException;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CelestialBodyBuilder {

    private CelestialBody celestialBody;
    private final Random random = new Random();

    public void makeNewCelestialBodyFromMap(Map<String, ?> celestialBodyData) throws InvalidDataException {
        switch (((String) celestialBodyData.get("type")).toLowerCase()){
            case "planet" -> celestialBody = new CelestialBody((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"), CelestialBodyTypes.PLANET);
            case "asteroid" ->  celestialBody = new CelestialBody((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"), CelestialBodyTypes.ASTEROID);
            default ->  celestialBody = new CelestialBody((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"), CelestialBodyTypes.UNKNOWN);
        }

        float posX = (float) celestialBodyData.get("x");
        float posY = (float) celestialBodyData.get("y");
        float velX = (float) celestialBodyData.get("vx");
        float velY = (float) celestialBodyData.get("vy");
        celestialBody.setVelocity(velX, velY);
        celestialBody.setPosition(posX, posY);
        celestialBody.setRadius((float) celestialBodyData.get("radius"));

        String collideEffectName = (String) celestialBodyData.get("oncollision");
        switch(collideEffectName.toLowerCase()){
            case "blink" -> celestialBody.addCollisionType(CollisionTypes.BLINK);
            case "bounce" -> celestialBody.addCollisionType(CollisionTypes.BOUNCE);
            case "disappear" -> celestialBody.addCollisionType(CollisionTypes.DISAPPEAR);
            case "explode" -> celestialBody.addCollisionType(CollisionTypes.EXPLODE);
            case "grow" -> celestialBody.addCollisionType(CollisionTypes.GROW);
        }
    }

    public void makeNewGenericAsteroid(){
        celestialBody = new CelestialBody(null, "Black", CelestialBodyTypes.ASTEROID);
        celestialBody.setRadius(3 + random.nextInt(6));
        celestialBody.setVelocity(1f + random.nextFloat(), 1f + random.nextFloat());
        if(Math.random() < 0.5){
            celestialBody.invertVelocityX();
        }
        if(Math.random() < 0.5){
            celestialBody.invertVelocityY();
        }
        celestialBody.addCollisionType(CollisionTypes.BOUNCE);
    }

    public void formHyperlanes(String[] neighbourNames, List<CelestialBody> existing){
        for(String neighbourName: neighbourNames){
            for(CelestialBody candidate : existing){
                if(candidate.getName().equals(neighbourName)) {
                    new Hyperlane(candidate,  celestialBody);
                }
            }
        }
    }

    public CelestialBody returnCelestialBody(){
        return celestialBody;
    }

}
