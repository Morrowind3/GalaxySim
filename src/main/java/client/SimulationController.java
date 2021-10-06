package client;

import client.SuperController;
import client.Mediator;
import client.view.SimulationView;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import client.view.components.HyperlaneComponent;
import core.*;
import core.exceptions.InvalidDataException;
import core.loader.Loader;
import core.loader.LoaderFactory;

import java.util.*;

public class SimulationController implements Mediator {
    private static final int SIMULATION_HEIGHT = 600;
    private static final int SIMULATION_WIDTH = 800;

    private CollisionStrategy collisionStrategy;
    private final List<CelestialBody> celestialBodyModels = new ArrayList<>();
    private final CelestialBodyBuilder builder = new CelestialBodyBuilder();
    private final SimulationView simulationView;
    private final SuperController superController;

    public SimulationController(SuperController superController){
        this.superController = superController;
        collisionStrategy = new SimpleCollisionStrategy(SIMULATION_WIDTH, SIMULATION_HEIGHT);
        simulationView = new SimulationView(SIMULATION_WIDTH, SIMULATION_HEIGHT);
    }

    public void loadData(String dataUrl){
        LoaderFactory loaderFactory = new LoaderFactory();
        String loaderType;
        if(superController.isLocalSelected()){
            loaderType = "local";
        } else {
            loaderType = "api";
        }
        Loader loader = loaderFactory.create(loaderType);
        try{
            for(Map<String, ?> celestialBody : loader.loadSimData(dataUrl)){
                builder.makeNewCelestialBodyFromMap(celestialBody);
                String[] neighbours = ((String) celestialBody.get("neighbours")).split(",");
                builder.formHyperlanes(neighbours, celestialBodyModels);
                celestialBodyModels.add(builder.returnCelestialBody());
            }
        } catch (InvalidDataException e){
            //Todo: Notify user
            System.out.println(e.getMessage());
            return;
        }
        rebuildComponentLists();

        superController.setMainContentCanvas(simulationView);
    }

    public void rebuildComponentLists(){
        final List<CelestialBodyComponent> celestialBodies = new ArrayList<>();
        final List<HyperlaneComponent> hyperlanes = new ArrayList<>();

        for(CelestialBody model : celestialBodyModels){
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
        List<CelestialBody> markedForDestruction = new ArrayList<>();
        for(CelestialBody model : celestialBodyModels){
            if(model.shouldDestroy()){
                markedForDestruction.add(model);
                continue;
            }
            model.move();
        }
        collisionStrategy.checkCollisions(celestialBodyModels);

        //TODO: States should do this themselves somehow. Visitor?
        if(!markedForDestruction.isEmpty()){
            for(CelestialBody marked : markedForDestruction){
                if(marked.shouldExplode()){
                    Random random = new Random();

                    for(int i = 0; i < 5; ++i){
                        builder.makeNewGenericAsteroid();
                        Asteroid asteroid = (Asteroid) builder.returnCelestialBody();
                        asteroid.setPosition(marked.getPositionX(), marked.getPositionY());
                        asteroid.setVelocity(1f + random.nextFloat(), 1f + random.nextFloat());
                        if(random.nextFloat() < 0.5f){
                            asteroid.invertVelocityX();
                        }
                        if(random.nextFloat() < 0.5f){
                            asteroid.invertVelocityY();
                        }
                        asteroid.setRadius(marked.getRadius() / (1f + random.nextFloat()));
                        celestialBodyModels.add(asteroid);
                    }
                }
                celestialBodyModels.remove(marked);
            }
            rebuildComponentLists();
        }
        simulationView.renderSimulation();
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
