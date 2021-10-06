package client;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Scene scene;
    private final SuperController controller;
    private final HashMap<KeyCode, EventHandler<Event>> keyCommands = new HashMap<>();

    public InputHandler(Scene scene, SuperController controller){
        this.controller = controller;
        this.scene = scene;
    }

    public void registerKeyCommand(KeyCode key, EventHandler<Event> command){
        keyCommands.put(key, command);
        refreshKeyListeners();
    };
    public void unregisterKeyCommand(KeyCode key){
        keyCommands.remove(key);
        refreshKeyListeners();
    };
    private void refreshKeyListeners(){
        scene.setOnKeyPressed(e -> {
            for(Map.Entry<KeyCode, EventHandler<Event>> entry : keyCommands.entrySet()) {
                KeyCode keyPressed = entry.getKey();
                EventHandler<Event> command = entry.getValue();
                if (e.getCode() == keyPressed) {
                    command.handle(e);
                }
            }
        });
    }
}
