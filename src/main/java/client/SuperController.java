package client;

import client.commands.*;
import client.view.KeyConfigBar;
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
    private static final int WINDOW_HEIGHT = 760;
    private static final String WINDOW_TITLE = "Flat Galaxy Simulator 2021";

    private InputHandler inputHandler;
    private Launcher launcher;
    private FileSelector fileSelector;
    private SimulationController simulationController;
    private FileSelectorController fileSelectorController;
    private final AnimationTimerPlus applicationLoop;

    public SuperController(){
        simulationController = new SimulationController(this);
        applicationLoop = new AnimationTimerPlus(true) {
            @Override
            public void handle(long now) {
                if(!super.shouldContinue(now)) return;
                Platform.runLater(() -> simulationController.updateSimulation());
            }
        };
    }

    public boolean isLocalSelected(){
        return true;
    }

    public void loadSimulation(String dataUrl){
        simulationController.loadData(dataUrl);
        simulationController.updateSimulation();
        applicationLoop.ready();
    }

    public AnimationTimerPlus getApplicationLoop(){
        return applicationLoop;
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
        KeyConfigBar keyConfigBar = new KeyConfigBar();
        launcher.Show(primaryStage, keyConfigBar, fileSelector);

        inputHandler = new InputHandler(launcher.getScene(), this);
        KeyConfigBarController keyConfigBarController = new KeyConfigBarController(inputHandler, this);
        keyConfigBarController.registerComponent(keyConfigBar);

    }

    @Override
    public void registerComponent(Component component) {
        switch (component.getName()) {
            default:
                break;
        }
    }

}
