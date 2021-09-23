package client.view;

import client.Mediator;
import client.view.components.Component;
import javafx.scene.canvas.Canvas;

public class SimulationView extends Canvas implements Component {
    private Mediator controller;

    @Override
    public void setMediator(Mediator mediator) {
        controller = mediator;
    }

    @Override
    public String getName() {
        return "SimulationView";
    }
}
