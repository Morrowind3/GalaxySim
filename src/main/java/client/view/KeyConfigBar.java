package client.view;

import client.KeyConfigBarController;
import client.Mediator;
import client.commands.Command;
import client.commands.CommandNames;
import client.view.components.Component;
import client.view.components.Buttons.LauncherButton;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class KeyConfigBar extends HBox implements Component {
    private KeyConfigBarController controller;
    private static final Background BACKGROUND = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null));

    public KeyConfigBar(){
        setPadding(new Insets(5, 5, 5, 5));
        setSpacing(5);
        setBackground(BACKGROUND);
    }

    public void formCommandButtons(HashMap<KeyCode, Command> commands){
        AtomicInteger pos = new AtomicInteger();
        final VBox[] buttonColumn = {newButtonColumn()};
        commands.forEach((key, value) -> {
            LauncherButton button = makeCommandButton(pos.getAndIncrement(), value.getCommandName(), key);
            button.setPrefWidth(getWidth()/(commands.size())*2 );
            if(pos.get() % 2 == 1){
                getChildren().add(buttonColumn[0]);
                buttonColumn[0] = newButtonColumn();
            }
            buttonColumn[0].getChildren().add(button);
        });
        getChildren().add(buttonColumn[0]);
    }

    private VBox newButtonColumn(){
        VBox buttonColumn = new VBox();
        buttonColumn.setBackground(BACKGROUND);
        return buttonColumn;
    }

    private LauncherButton makeCommandButton(int pos, CommandNames commandName, KeyCode defaultKey){
        LauncherButton button = new LauncherButton( commandName.getName() + "\n(" + defaultKey.getName() + ")");
        button.setOnMouseClicked( event -> {
            controller.showKeyConfigurationDialogue(button, commandName);
        });
        return button;
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
