package client;

import client.view.SimulationView;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import client.view.components.GridComponent;
import client.view.components.HyperlaneComponent;
import core.*;
import core.collisionstrategy.CollisionStrategy;
import core.collisionstrategy.QuadTreeCollisionStrategy;
import core.collisionstrategy.SimpleCollisionStrategy;

import java.util.*;

public class SimulationController implements Mediator {
    public static final int SIMULATION_HEIGHT = 600;
    public static final int SIMULATION_WIDTH = 800;

    private final SimulationView simulationView;
    private MementoKeeper mementoKeeper = new MementoKeeper();
    private final SuperController superController;
    private final GalaxySimulation simulation = new GalaxySimulation();
    final List<CelestialBodyComponent> celestialBodies = new ArrayList<>();
    final List<HyperlaneComponent> hyperlanes = new ArrayList<>();

    public SimulationController(SuperController superController){
        this.superController = superController;
        simulationView = new SimulationView(SIMULATION_WIDTH, SIMULATION_HEIGHT);
        simulationView.setMediator(this);
        simulation.setCollisionStrategy(new SimpleCollisionStrategy(SIMULATION_WIDTH, SIMULATION_HEIGHT, this));
    }

    public List<CelestialBody> getCelestialBodies(){
       return simulation.getCelestialBodies();
    }

    public void loadData(String dataUrl){
        mementoKeeper = new MementoKeeper();
        simulation.initializeCelestialBodies(dataUrl);
        buildComponentLists();
        superController.setMainContentCanvas(simulationView);
    }

    public void toggleGrid(){
        simulationView.toggleGrid();
        if(simulation.getCollisionStrategy() instanceof QuadTreeCollisionStrategy){ //TODO: Typecheck
            simulationView.setGrid(new GridComponent(((QuadTreeCollisionStrategy) simulation.getCollisionStrategy()).getQuadTree()));
        }
        rerender();
    }

    public void togglePlanetNames(){
        simulationView.togglePlanetLabels();
    }

    public void setCollisionStrategy(CollisionStrategy strategy){
        simulation.setCollisionStrategy(strategy);
    }

    public MementoKeeper getMementoKeeper(){
        return mementoKeeper;
    }

    public void setDrawablePrimaryEmphasis(CelestialBody celestialBody, Boolean emphasis){
        for(CelestialBodyComponent component : celestialBodies){
            if(component.modelAssociated(celestialBody)){
                if(emphasis){
                    component.setColourOverride("Red");
                } else component.setColourOverride(null);
                return;
            }
        }
    }
    public void setDrawablePrimaryEmphasis(Hyperlane lane, Boolean emphasis){
        for(HyperlaneComponent component : hyperlanes){
            if(component.modelAssociated(lane)){
                if(emphasis){
                    component.setColourOverride("Red");
                } else component.setColourOverride(null);
            }
        }
    }

    public void setDrawableSecondaryEmphasis(CelestialBody celestialBody, Boolean emphasis){
        for(CelestialBodyComponent component : celestialBodies){
            if(component.modelAssociated(celestialBody)){
                component.setGlowing(emphasis);
                return;
            }
        }
    }
    public void setDrawableSecondaryEmphasis(Hyperlane lane, Boolean emphasis){
        for(HyperlaneComponent component : hyperlanes){
            if(component.modelAssociated(lane)){
                component.setGlowing(emphasis);
                return;
            }
        }
    }

    private void buildComponentLists(){
        celestialBodies.clear();
        hyperlanes.clear();
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

    public void addToGalaxy(CelestialBody body){
        CelestialBodyComponent component = new CelestialBodyComponent(body);
        body.addObserver(component);
        celestialBodies.add(component);
        simulation.getCelestialBodies().add(body);
        if(body instanceof Planet){ //todo: Icky typecheck, candidate for refactor
            List<Hyperlane> lanes = ((Planet) body).getHyperlanes();
            for(Hyperlane lane: lanes){
                hyperlanes.add(new HyperlaneComponent(lane));
            }
        }
    }

    public void removeFromGalaxy(CelestialBody body){
        body.prepareForDestruction();
        CelestialBodyComponent celestialBodyToRemove = null;
        final List<HyperlaneComponent> lanesToRemove = new ArrayList<>();

        for(CelestialBodyComponent celestialBodyComponent : celestialBodies){
            if(celestialBodyComponent.modelAssociated(body)){
                celestialBodyToRemove = celestialBodyComponent;

                if(body instanceof Planet){ //TODO: Typecheck
                    for(Hyperlane lane : ((Planet) body).getHyperlanes()){
                        for(HyperlaneComponent laneComponent : hyperlanes){
                            if(laneComponent.modelAssociated(lane)){
                                lanesToRemove.add(laneComponent);
                            }
                        }
                    }
                }
            }
        }

        for(HyperlaneComponent toRemove : lanesToRemove){
            hyperlanes.remove(toRemove);
        }
        celestialBodies.remove(celestialBodyToRemove);
        simulation.getCelestialBodies().remove(body);
    }

    public void updateSimulation(){
        simulation.updateSimulation();
        rerender();
    }
    public void rerender(){
        simulationView.renderSimulation();
    }

    public CollisionStrategy getCollisionStrategy(){
        return simulation.getCollisionStrategy();
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

    public String getName(){
        return "SimulationController";
    }
}
