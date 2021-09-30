package client.view;

import client.SuperController;
import client.Mediator;
import client.view.components.CelestialBodyComponent;
import client.view.components.Component;
import client.view.components.HyperlaneComponent;
import core.CelestialBody;
import core.CelestialBodyBuilder;
import core.Hyperlane;
import core.Planet;
import core.exceptions.InvalidDataException;
import core.loader.Loader;
import core.loader.LoaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationController implements Mediator {


    private final List<CelestialBody> celestialBodyModels = new ArrayList<>();
    private SimulationView simulationView;
    private SuperController superController;
    private boolean isLoaded = false;


    public SimulationController(SuperController superController){
        this.superController = superController;
    }

    public void loadData(String dataUrl){
        final List<CelestialBodyComponent> celestialBodies = new ArrayList<>();
        final List<HyperlaneComponent> hyperlanes = new ArrayList<>();

        LoaderFactory loaderFactory = new LoaderFactory();
        String loaderType;
        if(superController.isLocalSelected()){
            loaderType = "local";
        } else {
            loaderType = "api";
        }
        Loader loader = loaderFactory.create(loaderType);
        final CelestialBodyBuilder builder = new CelestialBodyBuilder();
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
