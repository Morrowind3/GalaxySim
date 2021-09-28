package client.view;

import client.SuperController;
import client.Mediator;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import core.CelestialBody;
import core.CelestialBodyFactory;
import core.exceptions.InvalidDataException;
import core.loader.Loader;
import core.loader.LoaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationController implements Mediator {
    private final List<CelestialBodyComponent> celestialBodies = new ArrayList<>();
    private final List<CelestialBody> celestialBodyModels = new ArrayList<>();
    private SimulationView simulationView;
    private SuperController superController;
    private boolean isLoaded = false;


    public SimulationController(SuperController superController){
        this.superController = superController;
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
        final CelestialBodyFactory builder = new CelestialBodyFactory();
        try{
            for(Map<String, ?> celestialBody : loader.loadSimData(dataUrl)){
                celestialBodyModels.add(builder.makeCelestialBodyFromMap(celestialBody));
            }
        } catch (InvalidDataException e){
            //Todo: Notify user
            System.out.println(e.getMessage());
            return;
        }

        for(CelestialBody model : celestialBodyModels){
            CelestialBodyComponent component = new CelestialBodyComponent(model);
            model.addObserver(component);
            celestialBodies.add(component);
        }
        superController.setMainContentCanvas(simulationView);

        isLoaded = true;
    }

    public Boolean isLoaded(){
        return isLoaded;
    }

    public String getName(){
        return "SimulationController";
    }

    public void updateSimulation(){
        for(CelestialBody model : celestialBodyModels){
            model.move();
        }
        simulationView.renderSimulation();
    }

    public List<CelestialBodyComponent> getCelestialBodies(){
        return celestialBodies;
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "SimulationView" -> simulationView = (SimulationView) component;
            default -> {
            }
        }
    }
}
