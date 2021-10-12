package client;

import client.SuperController;
import client.Mediator;
import client.view.SimulationView;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import client.view.components.HyperlaneComponent;
import core.*;

import java.util.*;

public class SimulationController implements Mediator {
    private static final int SIMULATION_HEIGHT = 600;
    private static final int SIMULATION_WIDTH = 800;

    private final SimulationView simulationView;
    private final MementoKeeper mementoKeeper = new MementoKeeper();
    private final SuperController superController;
    private final GalaxySimulation simulation = new GalaxySimulation();

    public SimulationController(SuperController superController){
        this.superController = superController;
        simulationView = new SimulationView(SIMULATION_WIDTH, SIMULATION_HEIGHT);
        simulation.setCollisionStrategy(new SimpleCollisionStrategy(SIMULATION_WIDTH, SIMULATION_HEIGHT, simulation.getCelestialBodies()));
    }

    public void loadData(String dataUrl){
        if(simulation.isStarted()){
            return;
        }
        simulation.initializeCelestialBodies(dataUrl, superController.isLocalSelected());
        rebuildComponentLists();
        superController.setMainContentCanvas(simulationView);
    }

    public MementoKeeper getMementoKeeper(){
        return mementoKeeper;
    }

    public void rebuildComponentLists(){
        final List<CelestialBodyComponent> celestialBodies = new ArrayList<>();
        final List<HyperlaneComponent> hyperlanes = new ArrayList<>();

        for(CelestialBody model : simulation.getCelestialBodies()){
            CelestialBodyComponent component = new CelestialBodyComponent(model);
            model.addObserver(component);
            celestialBodies.add(component);
            if(model instanceof Planet){ //todo: Icky typecheck, candidate for refactor
                List<Hyperlane> lanes = ((Planet) model).getHyperlanes();
                for(Hyperlane lane: lanes){
                    hyperlanes.add(new HyperlaneComponent(lane));
                }
            }
        }
        simulationView.setCelestialBodyComponents(celestialBodies);
        simulationView.setHyperlaneComponents(hyperlanes);
    }

    public String getName(){
        return "SimulationController";
    }

    public void updateSimulation(){
        simulation.updateSimulation();
        rebuildComponentLists();
        simulationView.renderSimulation();
    }

    public void saveState(){
        simulation.saveMemento(mementoKeeper);
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            default -> {
            }
        }
    }
}
