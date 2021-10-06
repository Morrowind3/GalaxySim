package client;

import client.commands.*;
import client.view.components.Component;
import client.view.FileSelector;
import client.view.scenes.Launcher;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class SuperController implements Mediator {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 680;


    private static final String WINDOW_TITLE = "Flat Galaxy Simulator 2021";
    private long simulationSpeed = 30_000_000; //roughly 30FPS by default;

    private InputHandler inputHandler;
    private Launcher launcher;
    private FileSelector fileSelector;
    private SimulationController simulationController;
    private FileSelectorController fileSelectorController;

    public SuperController(){
        simulationController = new SimulationController(this);
    }

    public boolean isLocalSelected(){
        return true;
    }

    public void loadSimulation(String dataUrl){
        simulationController.loadData(dataUrl);

        AnimationTimer task = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                if (now - lastUpdate <= simulationSpeed) {
                    return;
                }
                lastUpdate = now;
                Platform.runLater(() -> simulationController.updateSimulation());
            }
        };
        task.start();
    }

    public void setMainContentCanvas(Canvas mainContent){
        launcher.setCenterCanvas(mainContent);
    }

    public void launchApp(Stage primaryStage){
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        fileSelectorController = new FileSelectorController(primaryStage, this);
        fileSelector = new FileSelector();
        fileSelector.setMediator(fileSelectorController);

        launcher = new Launcher();


        launcher.Show(primaryStage, fileSelector);
        inputHandler = new InputHandler(launcher.getScene(), this);
        inputHandler.registerKeyCommand(KeyCode.BACK_SPACE, new RewindCommand());
        inputHandler.registerKeyCommand(KeyCode.RIGHT, new SpeedDownCommand());
        inputHandler.registerKeyCommand(KeyCode.LEFT, new SpeedUpCommand());
        inputHandler.registerKeyCommand(KeyCode.SPACE, new StartPauseCommand(this));
        inputHandler.registerKeyCommand(KeyCode.C, new SwitchCollisionAlgorithmCommand());
    }

    public String getName(){
        return "SuperViewController";
    }

    @Override
    public void registerComponent(Component component) {
        switch (component.getName()) {
            default:
                break;
        }
    }

}
