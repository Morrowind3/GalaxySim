package client.view.components;

import client.Mediator;
import client.SimulationController;
import core.CelestialBody;
import core.Hyperlane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;


public class HyperlaneComponent implements Component, Drawable, Observer {
    private static final int LINE_WIDTH = 2;
    private static final DropShadow GLOW = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, LINE_WIDTH+3, 0.8, 0, 0);
    private static final Color COLOUR = Color.DARKVIOLET;

    private SimulationController controller;
    private Hyperlane model;
    private boolean isGlowing;
    private Color colourOverride;

    public HyperlaneComponent(Hyperlane lane){
        model = lane;
        colourOverride = COLOUR;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.controller = (SimulationController) mediator;
    }

    public void draw(GraphicsContext context){
        float[] start = model.getHyperlaneEndA();
        float[] end = model.getHyperlaneEndB();

        context.setStroke(colourOverride);
        context.setLineWidth(LINE_WIDTH);
        if(isGlowing){
            context.setEffect(GLOW);
        }
        context.strokeLine(start[0], start[1], end[0], end[1]);
        context.setEffect(null);
    }

    public void setGlowing(boolean glowing){
        isGlowing = glowing;
    }

    @Override
    public boolean modelAssociated(Object model){
        return(model == this.model);
    }

    @Override
    public void setColourOverride(String colour) {
        colourOverride = (colour == null) ? COLOUR : Color.web(colour);
    }

    @Override
    public String getName() {
        return "Hyperlane";
    }

    @Override
    public void update(Observable o, Object arg) {
        model = (Hyperlane) o;
    }
}
