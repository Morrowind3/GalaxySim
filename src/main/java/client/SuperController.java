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
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class SuperController implements Mediator {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 760;
    private static final String WINDOW_TITLE = "Flat Galaxy Simulator 2021";
    private static final int MEMENTO_INTERVAL_SEC = 5;

    private Launcher launcher;
    private final SimulationController simulationController;
    private FileSelectorController fileSelectorController;
    private ScheduledFuture mementoScheduledFuture;
    private final AnimationTimerPlus applicationLoop;
    private final ScheduledExecutorService executor;
    private final Runnable mementoTimer;

    private boolean saveState = false;

    public SuperController(){
        mementoTimer = () -> saveState = true;

        executor = Executors.newSingleThreadScheduledExecutor();
        startMementoTimer(false);

        simulationController = new SimulationController(this);

        applicationLoop = new AnimationTimerPlus(true) {
            private int saveStateCounter = 0;
            @Override
            public void handle(long now) {
                if(super.shouldPause(now)) return;
                Platform.runLater(simulationController::updateSimulation);
                if(saveState) {
                    simulationController.saveState();
                    saveState = false;
                    saveStateCounter++;
                }
                if(getMementoKeeper().historySize() < saveStateCounter){
                    mementoScheduledFuture.cancel(true);
                    saveStateCounter = getMementoKeeper().historySize();
                    startMementoTimer(true);
                }
            }
        };
    }

    //restarting the timer guarantees the Memento Interval is consistent between rewinds.
    public void startMementoTimer(boolean delay){
        if(delay){
            mementoScheduledFuture = executor.scheduleAtFixedRate(mementoTimer, MEMENTO_INTERVAL_SEC, MEMENTO_INTERVAL_SEC, TimeUnit.SECONDS);
        } else {
            mementoScheduledFuture = executor.scheduleAtFixedRate(mementoTimer, 0, MEMENTO_INTERVAL_SEC, TimeUnit.SECONDS);

        }
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
