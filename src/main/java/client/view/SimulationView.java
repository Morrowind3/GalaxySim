package client.view;

import client.Mediator;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import client.view.components.Drawable;
import client.view.components.HyperlaneComponent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;


public class SimulationView extends Canvas implements Component {

    private SimulationController controller;
    private List<CelestialBodyComponent> celestialBodies;
    private List<HyperlaneComponent> hyperlanes;

    @Override
    public void setMediator(Mediator mediator) {
        controller = (SimulationController) mediator;
    }

    public SimulationView(int height, int width){
        setWidth(height);
        setHeight(width);
    }

    public void renderSimulation(){
        GraphicsContext context = getGraphicsContext2D();

        context.clearRect(0, 0, getWidth(), getHeight());
        for (Drawable lane : hyperlanes) {
            lane.draw(context);
        }
        for (Drawable celestialBody : celestialBodies) {
            celestialBody.draw(context);
        }

    }

    public void setCelestialBodyComponents(List<CelestialBodyComponent> celestialBodies){
        this.celestialBodies = celestialBodies;
    }
    public void setHyperlaneComponents(List<HyperlaneComponent> hyperlanes){
        this.hyperlanes = hyperlanes;
    }


    @Override
    public String getName() {
        return "SimulationView";
    }

}
