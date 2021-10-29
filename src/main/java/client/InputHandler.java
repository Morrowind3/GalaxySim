package client;

import client.commands.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Scene scene;
    private final HashMap<KeyCode, Command> keyCommands = new HashMap<>();

    public InputHandler(Scene scene){
        this.scene = scene;
    }

    public void registerKeyCommand(KeyCode key, Command command){
        keyCommands.put(key, command);
        refreshKeyListeners();
    };

    public void registerKeyCommand(KeyCode key, CommandNames commandName){
        KeyCode oldKey = null;
        Command toSet = null;
        for(Map.Entry<KeyCode, Command> entry : keyCommands.entrySet()) {
            Command command = entry.getValue();

            if(command.getCommandName() == commandName){
                oldKey = entry.getKey();
                toSet = command;
                break;
            }
        }
        keyCommands.put(key, toSet);
        keyCommands.remove(oldKey);

        refreshKeyListeners();
    };

    public HashMap<KeyCode, Command> getKeyCommands(){
        return keyCommands;
    }

    private void refreshKeyListeners(){
        scene.setOnKeyPressed(e -> {
            for(Map.Entry<KeyCode, Command> entry : keyCommands.entrySet()) {
                KeyCode keyPressed = entry.getKey();
                EventHandler<Event> command = entry.getValue();
                if (e.getCode() == keyPressed) {
                    command.handle(e);
                    return;
                }
            }
        });
    }
}
