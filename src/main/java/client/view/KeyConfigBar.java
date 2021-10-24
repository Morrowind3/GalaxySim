package client.view;

import client.KeyConfigBarController;
import client.Mediator;
import client.commands.CommandNames;
import client.view.components.Component;
import client.view.components.LauncherButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Optional;

public class KeyConfigBar extends HBox implements Component {
    private KeyConfigBarController controller;
    private final LauncherButton[] configurableButtons = new LauncherButton[7];

    public KeyConfigBar(){
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(5);
        BackgroundFill backgroundFill = new BackgroundFill(Color.CORNFLOWERBLUE, null, null);
        Background bg = new Background(backgroundFill);
        setBackground(bg);

        Label label = new Label("Configure \n Keys:");
        makeCommandButton(0, CommandNames.REWIND, KeyCode.BACK_SPACE);
        makeCommandButton(1, CommandNames.SPEED_UP, KeyCode.RIGHT);
        makeCommandButton(2, CommandNames.SPEED_DOWN, KeyCode.LEFT);
        makeCommandButton(3, CommandNames.START_PAUSE, KeyCode.SPACE);
        makeCommandButton(4, CommandNames.COLLISION_MODE, KeyCode.C);
        makeCommandButton(5, CommandNames.SHOW_GRID, KeyCode.G);
        makeCommandButton(6, CommandNames.SHOW_PLANET_NAMES, KeyCode.L);


        Region whiteSpace = new Region();
        HBox.setHgrow(whiteSpace, Priority.ALWAYS);
        getChildren().add(label);
        getChildren().addAll(configurableButtons);
    }

    private void makeCommandButton(int pos, CommandNames commandName, KeyCode defaultKey){
        configurableButtons[pos] = new LauncherButton( commandName.getName() + "\n(" + defaultKey.getName() + ")");
        configurableButtons[pos].setOnMouseClicked( event -> {
            controller.showKeyConfigurationDialogue(configurableButtons[pos], commandName);
        });
    }

    @Override
    public void setMediator(Mediator mediator) {
        controller = (KeyConfigBarController) mediator;
    }

    @Override
    public String getName() {
        return "KeyConfigBar";
    }
}
