package client;

import client.commands.*;
import client.view.KeyConfigBar;
import client.view.components.Component;
import client.view.components.LauncherButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;

import java.util.Optional;

public class KeyConfigBarController implements Mediator {
    private KeyConfigBar keyConfigBar;
    private final InputHandler inputHandler;
    private SuperController superController;

    private TextInputDialog inputDialogue;

    public KeyConfigBarController(InputHandler handler, SuperController superController){
        inputHandler = handler;
        this.superController = superController;

        inputHandler.registerKeyCommand(KeyCode.BACK_SPACE, new RewindCommand());
        inputHandler.registerKeyCommand(KeyCode.RIGHT, new SpeedDownCommand());
        inputHandler.registerKeyCommand(KeyCode.LEFT, new SpeedUpCommand());
        inputHandler.registerKeyCommand(KeyCode.SPACE, new StartPauseCommand(superController));
        inputHandler.registerKeyCommand(KeyCode.C, new SwitchCollisionAlgorithmCommand());
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
            inputHandler.unregisterKeyCommand(key[0]);
            switch(commandName){
                case COLLISION_MODE -> inputHandler.registerKeyCommand(key[0], new SwitchCollisionAlgorithmCommand());
                case REWIND -> inputHandler.registerKeyCommand(key[0], new RewindCommand());
                case SPEED_DOWN -> inputHandler.registerKeyCommand(key[0], new SpeedDownCommand());
                case SPEED_UP -> inputHandler.registerKeyCommand(key[0], new SpeedUpCommand());
                case START_PAUSE -> inputHandler.registerKeyCommand(key[0], new StartPauseCommand(superController));
            }
        });
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "KeyConfigBar":
                keyConfigBar = (KeyConfigBar) component;
                break;
            default:
                break;
        }
    }

}
