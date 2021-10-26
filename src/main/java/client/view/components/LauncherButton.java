package client.view.components;

import client.Mediator;
import client.commands.CommandNames;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class LauncherButton extends Button implements Component {
    private final String name;
    private Mediator controller;

    public LauncherButton(String name, String text){
        this.name = name;
        setText(text);
        setPrefSize(100,20);
        setFocusTraversable(false);
    }

    public LauncherButton(String text){
        name = "Unnamed";
        setText(text);
        setFocusTraversable(false);
    }

    public void setCommand(EventHandler<Event> command) {
        setOnAction(command::handle);
    }

    @Override
    public void setMediator(Mediator mediator) {
        controller =  mediator;
    }

    @Override
    public String getName() {
        return name;
    }
}
