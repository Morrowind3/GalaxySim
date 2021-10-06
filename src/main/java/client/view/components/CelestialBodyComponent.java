package client.view.components;

import client.Mediator;
import client.SimulationController;
import core.CelestialBody;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;


public class CelestialBodyComponent implements Component, Drawable, Observer {

    private SimulationController controller;
    private CelestialBody model;

    public CelestialBodyComponent(CelestialBody model){
        this.model = model;
    }

    public void draw(GraphicsContext context){
        context.setFill(Color.web(model.getColour()));
        float diameter = model.getRadius() * 2;
        context.fillOval(model.getPositionX(), model.getPositionY(), diameter, diameter);
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
