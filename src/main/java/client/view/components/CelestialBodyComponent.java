package client.view.components;

import client.Mediator;
import client.SimulationController;
import core.CelestialBody;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Observable;
import java.util.Observer;


public class CelestialBodyComponent implements Component, Drawable, Observer {
    private SimulationController controller;
    private CelestialBody model;
    private boolean isGlowing;
    private Color strokeColour;

    public CelestialBodyComponent(CelestialBody model){
        this.model = model;
        strokeColour = Color.web("Transparent");
    }

    public void draw(GraphicsContext context){
        context.setFill(Color.web(model.getColour()));
        float diameter = model.getRadius() * 2;

        if(isGlowing){
            DropShadow glow = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, model.getRadius(), 0.6, 0, 0);
            context.setEffect(glow);
        }
        context.fillOval(model.getPositionX(), model.getPositionY(), diameter, diameter);

        context.setStroke(strokeColour);
        context.strokeOval(model.getPositionX(), model.getPositionY(), diameter, diameter);
        context.setEffect(null);
    }

    public void drawLabels(GraphicsContext context){
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.fillText(
                model.getName(),
                model.getCenterX(),
                model.getPositionY() - 10);
    }

    @Override
    public boolean modelAssociated(Object model){
        return(model == this.model);
    }

    @Override
    public void setColourOverride(String colour){
        strokeColour = colour != null ? Color.web(colour) : Color.web("Transparent");
    }

    public void setGlowing(boolean glowing){
        isGlowing = glowing;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.controller = (SimulationController)  mediator;
    }

    @Override
    public String getName() {
        return "CelestialBody";
    }

    @Override
    public void update(Observable o, Object arg) {
        model = (CelestialBody) o;
    }
}
