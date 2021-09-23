package client.view;

import client.InterfaceController;
import client.Mediator;
import client.view.components.CelestialBody;
import client.view.components.Component;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class SimulationController implements Mediator {
    private final List<CelestialBody> celestialBodies = new ArrayList<>();
    private SimulationView simulationView;
    private InterfaceController superController;

    public SimulationController(String dataUrl, InterfaceController superController){
        this.superController = superController;
        System.out.println(dataUrl);
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "CelestialBody":
                celestialBodies.add((CelestialBody) component);
                break;
            default:
                break;
        }
    }
}
