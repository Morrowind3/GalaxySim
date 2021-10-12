package core;

import client.SimulationController;
import core.exceptions.InvalidDataException;
import core.loader.Loader;
import core.loader.LoaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GalaxySimulation implements MementoOriginator {

    private List<CelestialBody> celestialBodies = new ArrayList<>();
    private final CelestialBodyBuilder builder = new CelestialBodyBuilder();
    private CollisionStrategy collisionStrategy;
    private boolean started = false;

    public GalaxySimulation(){
        collisionStrategy = new NullCollisionStrategy(0,0,null);
    }

    public boolean isStarted(){
        return started;
    }

    public void setCollisionStrategy(CollisionStrategy strategy){
        this.collisionStrategy = strategy;
    }

    public List<CelestialBody> getCelestialBodies(){
        return celestialBodies;
    }

    public void updateSimulation(){
        for(CelestialBody model : celestialBodies){
            model.move();
        }
        collisionStrategy.checkCollisions();
    }

    public void initializeCelestialBodies(String dataUrl, boolean fromLocalFile){
        celestialBodies.clear();
        LoaderFactory loaderFactory = new LoaderFactory();
        String loaderType ;
        if(fromLocalFile){
            loaderType = "local";
        } else {
            loaderType = "api";
        }
        Loader loader = loaderFactory.create(loaderType);
        try{
            for(Map<String, ?> celestialBody : loader.loadSimData(dataUrl)){
                builder.makeNewCelestialBodyFromMap(celestialBody);
                String[] neighbours = ((String) celestialBody.get("neighbours")).split(",");
                builder.formHyperlanes(neighbours, celestialBodies);
                celestialBodies.add(builder.returnCelestialBody());
            }
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
            return;
        }
        started = true;
    }

    protected void setGalaxyList(List<CelestialBody> list){

        celestialBodies = list;
    }

    @Override
    public void saveMemento(MementoKeeper keeper) {
        List<CelestialBody> deepCopy = new ArrayList<>();
        for(CelestialBody celestialBody : celestialBodies) {
            deepCopy.add(celestialBody.clone());
        }
        keeper.push(new GalaxyMemento(collisionStrategy, deepCopy, this));
    }


    private class GalaxyMemento implements Memento {
        private final List<CelestialBody> planetStates;
        private final CollisionStrategy collisionStrategy;
        private final GalaxySimulation simulation;

        public GalaxyMemento(CollisionStrategy strat, List<CelestialBody> bodies, GalaxySimulation sim) {
            planetStates = bodies;
            collisionStrategy = strat;
            this.simulation = sim;
        }

        public void restore() {
            simulation.setCollisionStrategy(collisionStrategy);
            simulation.setGalaxyList(planetStates);
        }
    }

}
