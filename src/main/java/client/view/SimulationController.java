package client.view;

import client.InterfaceController;
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
    private SimulationView simulationView;
    private InterfaceController superController;

    public SimulationController(String dataUrl, InterfaceController superController){
        this.superController = superController;

        LoaderFactory loaderFactory = new LoaderFactory();
        String loaderType;
        if(superController.isLocalSelected()){
            loaderType = "local";
        } else {
            loaderType = "api";
        }
        Loader loader = loaderFactory.create(loaderType);
        final List<CelestialBody> celestialBodyModels = new ArrayList<>();
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
            celestialBodies.add(component);
        }
    }

    public List<CelestialBodyComponent> getCelestialBodies(){
        return celestialBodies;
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "SimView" -> simulationView = (SimulationView) component;
            default -> {
            }
        }
    }
}
