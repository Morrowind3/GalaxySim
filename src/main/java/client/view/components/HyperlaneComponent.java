package client.view.components;

import client.Mediator;
import client.view.SimulationController;
import core.Hyperlane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;


public class HyperlaneComponent implements Component, Drawable  {

    private SimulationController controller;
    private Hyperlane model;

    public HyperlaneComponent(Hyperlane lane){
        model = lane;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.controller = (SimulationController) mediator;
    }

    public void draw(GraphicsContext context){
        float[] start = model.getHyperlaneEndA();
        float[] end = model.getHyperlaneEndB();

        context.setStroke(Paint.valueOf("Blue"));
        context.setLineWidth(2);
        context.strokeLine(start[0], start[1], end[0], end[1]);
    }

    @Override
    public String getName() {
        return "Hyperlane";
    }

}
