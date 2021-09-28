package client.view;

import client.Mediator;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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

        context.clearRect(0, 0, getWidth(), getHeight());
        for (CelestialBodyComponent celestialBody : controller.getCelestialBodies()) {
            celestialBody.draw(context);
        }
    }


    @Override
    public String getName() {
        return "SimulationView";
    }

}
