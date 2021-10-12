package client;

import client.view.KeyConfigBar;
import client.view.components.Component;
import client.view.FileSelector;
import client.view.scenes.Launcher;
import core.MementoKeeper;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SuperController implements Mediator {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 760;
    private static final String WINDOW_TITLE = "Flat Galaxy Simulator 2021";

    private Launcher launcher;
    private final SimulationController simulationController;
    private FileSelectorController fileSelectorController;
    private final AnimationTimerPlus applicationLoop;

    private boolean saveState = false;

    public SuperController(){
        Runnable mementoTimer = () -> saveState = true;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(mementoTimer, 0, 5, TimeUnit.SECONDS);

        simulationController = new SimulationController(this);
        applicationLoop = new AnimationTimerPlus(true) {
            @Override
            public void handle(long now) {
                if(super.shouldPause(now)) return;
                Platform.runLater(simulationController::updateSimulation);
                if(saveState) {
                    simulationController.saveState();
                    saveState = false;
                }
            }
        };
    }

    public MementoKeeper getMementoKeeper(){
        return simulationController.getMementoKeeper();
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
        FileSelector fileSelector = new FileSelector();
        fileSelector.setMediator(fileSelectorController);

        launcher = new Launcher();
        KeyConfigBar keyConfigBar = new KeyConfigBar();
        launcher.Show(primaryStage, keyConfigBar, fileSelector);

        InputHandler inputHandler = new InputHandler(launcher.getScene(), this);
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
