package core;

import core.exceptions.InvalidDataException;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CelestialBodyBuilder {

    private CelestialBody celestialBody;
    private String type;
    private final Random random = new Random();

    public void makeNewCelestialBodyFromMap(Map<String, ?> celestialBodyData) throws InvalidDataException {
        type = (String) celestialBodyData.get("type");

        switch (type.toLowerCase()) {
            case "asteroid" -> celestialBody = new Asteroid((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"));
            case "planet" -> celestialBody = new Planet((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"));
            default -> throw new InvalidDataException();
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
        String name = "Asteroid-" + generateRandomId();
        celestialBody = new Asteroid(name, "Black");
        celestialBody.setRadius(5);
        celestialBody.addCollisionType(CollisionTypes.BOUNCE);
    }

    public void formHyperlanes(String[] neighbourNames, List<CelestialBody> existing){
        if(!type.equalsIgnoreCase("planet")) return;

        for(String neighbourName: neighbourNames){
            for(CelestialBody candidate : existing){
                if(candidate instanceof Planet && candidate.getName().equals(neighbourName)) { //TODO: Refactoring candidate. Icky type checking.
                    new Hyperlane((Planet) candidate, (Planet) celestialBody);
                }
            }
        }
    }

    public CelestialBody returnCelestialBody(){
        return celestialBody;
    }

    private String generateRandomId(){
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 4;

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            return generatedString;
        }
}
