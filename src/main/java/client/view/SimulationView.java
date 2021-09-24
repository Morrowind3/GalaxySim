package client.view;

import client.Mediator;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class SimulationView extends Canvas implements Component {
    private SimulationController controller;

    @Override
    public void setMediator(Mediator mediator) {
        controller = (SimulationController) mediator;
        setWidth(800);
        setHeight(600);
    }

    public void renderSimulation(){
        GraphicsContext context = getGraphicsContext2D();
        for(CelestialBodyComponent celestialBody : controller.getCelestialBodies()){
            celestialBody.draw(context);
        }
    }

    @Override
    public String getName() {
        return "SimulationView";
    }
}
