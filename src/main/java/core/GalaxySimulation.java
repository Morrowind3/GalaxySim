package core;

import core.collisionstrategy.CollisionStrategy;
import core.collisionstrategy.NullCollisionStrategy;
import core.exceptions.InvalidDataException;
import core.loader.Loader;
import core.loader.LoaderFactory;
import java.util.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GalaxySimulation implements MementoOriginator {

    private List<CelestialBody> celestialBodies = new ArrayList<>();
    private final CelestialBodyBuilder builder = new CelestialBodyBuilder();
    private CollisionStrategy collisionStrategy;

    public GalaxySimulation(){
        collisionStrategy = new NullCollisionStrategy(0,0,null);
    }


    public void setCollisionStrategy(CollisionStrategy strategy){
        this.collisionStrategy = strategy;
    }
    public CollisionStrategy getCollisionStrategy(){
        return collisionStrategy;
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

    public void initializeCelestialBodies(String dataUrl){
        celestialBodies.clear();
        LoaderFactory loaderFactory = new LoaderFactory();
        Loader loader = loaderFactory.create(dataUrl);
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
    }

    protected void setGalaxyList(List<CelestialBody> list){
        celestialBodies = list;
    }

    @Override
    public void saveMemento(MementoKeeper keeper) {
        List<CelestialBody> galaxyListDeepCopy = new ArrayList<>();
        for(CelestialBody celestialBody : celestialBodies){
            galaxyListDeepCopy.add(celestialBody.clone());
        }

        CollisionStrategy strategyCopy = collisionStrategy.clone();

        keeper.push(new GalaxyMemento(strategyCopy, galaxyListDeepCopy, this));

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
            for(CelestialBody clone: planetStates){
                if(clone instanceof Planet){
                    for(Hyperlane cloneLane : ((Planet) clone).getHyperlanes()){
                        cloneLane.resyncPlanets(planetStates);
                    }
                }
            }
            simulation.setGalaxyList(planetStates);
            simulation.setCollisionStrategy(collisionStrategy);
        }
    }

}
