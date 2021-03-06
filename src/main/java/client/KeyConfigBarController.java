package client;

import client.commands.*;
import client.view.KeyConfigBar;
import client.view.components.Component;
import client.view.components.Buttons.LauncherButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;

import java.util.Optional;

public class KeyConfigBarController implements Mediator {
    private final InputHandler inputHandler;

    private TextInputDialog inputDialogue;

    public KeyConfigBarController(InputHandler handler){
        inputHandler = handler;
    }

    public void showKeyConfigurationDialogue(LauncherButton pressed, CommandNames commandName){
        final KeyCode[] key = new KeyCode[1];
        inputDialogue = new TextInputDialog();
        inputDialogue.setTitle("Keybind prompt");
        inputDialogue.setHeaderText("Press the key you want to bind to the " + commandName.getName() +  "  action.");

        inputDialogue.getEditor().setOnKeyPressed(e -> {
            key[0] = e.getCode();
            inputDialogue.getEditor().setText(" " + commandName.getName() + " set to " + e.getCode().getName());
            inputDialogue.getEditor().deleteText(0,1);
        });

        Optional<String> result = inputDialogue.showAndWait();
        result.ifPresent(e -> {
            pressed.setText(commandName.getName() + "\n(" + key[0].getName() + ")");
            inputHandler.registerKeyCommand(key[0], commandName);
        });
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
    }

}
