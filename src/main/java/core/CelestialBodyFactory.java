package core;

import core.collisionstates.*;
import core.exceptions.InvalidDataException;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

import java.util.Locale;
import java.util.Map;

public class CelestialBodyFactory {

    public CelestialBody makeCelestialBodyFromMap(Map<String, ?> celestialBodyData) throws InvalidDataException {
        String type = (String) celestialBodyData.get("type");
        String collideEffectName = (String) celestialBodyData.get("oncollision");
        CollisionState initialState;

        switch(collideEffectName.toLowerCase()){
            case "blink" -> initialState = new BlinkState();
            case "bounce" -> initialState = new BounceState();
            case "disappear" -> initialState = new DisappearState();
            case "explode" -> initialState = new ExplodeState();
            case "grow" -> initialState = new GrowState();
            default -> initialState = null;
        }
        CelestialBody celestialBody;
        switch (type.toLowerCase()) {
            case "asteroid" -> celestialBody = new Asteroid((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"), initialState);
            case "planet" -> celestialBody = new Planet((String) celestialBodyData.get("name"), (String) celestialBodyData.get("color"), initialState);
            default -> throw new InvalidDataException();
        }
        float posX = (float) celestialBodyData.get("x");
        float posY = (float) celestialBodyData.get("y");
        float velX = (float) celestialBodyData.get("vx");
        float velY = (float) celestialBodyData.get("vy");
        celestialBody.setVelocity(velX, velY);
        celestialBody.setPosition(posX, posY);
        celestialBody.setRadius((float) celestialBodyData.get("radius"));

        return celestialBody;
    }


}
