package client.view.components;

import client.Mediator;
import javafx.scene.control.TextField;

public class UrlField extends TextField implements Component{

    public UrlField(){
        setFocusTraversable(false);
        setPrefSize(400, 20);
    }


    @Override
    public void setMediator(Mediator mediator) {
        //passive component
    }

    @Override
    public String getName() {
        return "UrlField";
    }
}
