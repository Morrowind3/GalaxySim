package client.view.components;

import client.Mediator;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;


public class CelestialBody extends Pane implements Component{

    private static final Circle circle = new Circle();

    public CelestialBody(){
    }

    @Override
    public void setMediator(Mediator mediator) {

    }

    @Override
    public String getName() {
        return null;
    }
}
