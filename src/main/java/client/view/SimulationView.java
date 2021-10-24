package client.view;

import client.Mediator;
import client.SimulationController;
import client.view.components.*;
import core.collisionstrategy.CollisionStrategy;
import core.collisionstrategy.QuadTreeCollisionStrategy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;


public class SimulationView extends Canvas implements Component {

    private SimulationController controller;
    private List<CelestialBodyComponent> celestialBodies;
    private List<HyperlaneComponent> hyperlanes;
    private GridComponent grid;
    private boolean gridShown = false;
    private boolean planetLabelsShown = false;


    @Override
    public void setMediator(Mediator mediator) {
        controller = (SimulationController) mediator;
    }

    public SimulationView(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    public void setGrid(GridComponent grid){
        this.grid = grid;
    }

    public void toggleGrid(){
        gridShown = !gridShown;
        if(!gridShown){
            grid = null;
        }
    }
    public void togglePlanetLabels(){
        planetLabelsShown = !planetLabelsShown;
    }


    public void renderSimulation(){
        GraphicsContext context = getGraphicsContext2D();

        context.clearRect(0, 0, getWidth(), getHeight());
        for (Drawable lane : hyperlanes) {
            lane.draw(context);
        }
        for (CelestialBodyComponent celestialBody : celestialBodies) {
            celestialBody.draw(context);
            if(planetLabelsShown){
                celestialBody.drawLabels(context);
            }
        }
        if(gridShown && grid != null){
            grid.draw(context);
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
