package client;

import client.commands.*;
import client.view.KeyConfigBar;
import client.view.components.Component;
import client.view.FileSelector;
import client.view.scenes.Launcher;
import core.collisionstrategy.QuadTreeCollisionStrategy;
import core.collisionstrategy.SimpleCollisionStrategy;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class SuperController implements Mediator {
    private static final int WINDOW_WIDTH = 810;
    private static final int WINDOW_HEIGHT = 780;
    private static final String WINDOW_TITLE = "Flat Galaxy Simulator 2021";
    private static final int MEMENTO_INTERVAL_SEC = 5;

    private Launcher launcher;
    private SimulationController simulationController;
    private FileSelectorController fileSelectorController;
    private ScheduledFuture mementoScheduledFuture;
    private final AnimationTimerPlus applicationLoop;
    private final ScheduledExecutorService executor;
    private final Runnable mementoTimer;
    private KeyConfigBar keyConfigBar;
    private InputHandler inputHandler;

    private boolean saveState = false;

    public SuperController(){
        mementoTimer = () -> saveState = true;
        executor = Executors.newSingleThreadScheduledExecutor();
        startMementoTimer(false);
        applicationLoop = new AnimationTimerPlus() {
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
                if(simulationController.getMementoKeeper().historySize() < saveStateCounter){
                    mementoScheduledFuture.cancel(true);
                    saveStateCounter = simulationController.getMementoKeeper().historySize();
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

    public void loadSimulation(String dataUrl){
        simulationController = new SimulationController(this);
        setCommands();
        simulationController.loadData(dataUrl);
        simulationController.updateSimulation();
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
        keyConfigBar = new KeyConfigBar();
        launcher.Show(primaryStage, keyConfigBar, fileSelector);
        inputHandler = new InputHandler(launcher.getScene());
        setCommands();
        keyConfigBar.formCommandButtons(inputHandler.getKeyCommands());

        KeyConfigBarController keyConfigBarController = new KeyConfigBarController(inputHandler);
        keyConfigBarController.registerComponent(keyConfigBar);
    }

    private void setCommands(){
        inputHandler.registerKeyCommand(KeyCode.LEFT, new SpeedDownCommand(applicationLoop));
        inputHandler.registerKeyCommand(KeyCode.BACK_SPACE, new RewindCommand(simulationController));
        inputHandler.registerKeyCommand(KeyCode.RIGHT, new SpeedUpCommand(applicationLoop));
        inputHandler.registerKeyCommand(KeyCode.SPACE, new StartPauseCommand(applicationLoop));
        inputHandler.registerKeyCommand(KeyCode.G, new ShowGridCommand(simulationController));
        inputHandler.registerKeyCommand(KeyCode.L, new ShowPlanetNamesCommand(simulationController));
        inputHandler.registerKeyCommand(KeyCode.ADD, new AddAsteroidCommand(simulationController, SimulationController.SIMULATION_WIDTH,  SimulationController.SIMULATION_HEIGHT ));
        inputHandler.registerKeyCommand(KeyCode.SUBTRACT, new RemoveAsteroidCommand(simulationController));

        final ShortestRouteCommand shortestRouteCommand = new ShortestRouteCommand(simulationController);
        final QuickestRouteCommand quickestRouteCommand =  new QuickestRouteCommand(simulationController);
        inputHandler.registerKeyCommand(KeyCode.S, shortestRouteCommand);
        inputHandler.registerKeyCommand(KeyCode.Q, quickestRouteCommand);

        SwitchCollisionAlgorithmCommand collisionCommand = new SwitchCollisionAlgorithmCommand(simulationController);
        collisionCommand.addAlgorithmChoice(new SimpleCollisionStrategy(SimulationController.SIMULATION_WIDTH, SimulationController.SIMULATION_HEIGHT, simulationController));
        collisionCommand.addAlgorithmChoice(new QuadTreeCollisionStrategy(SimulationController.SIMULATION_WIDTH, SimulationController.SIMULATION_HEIGHT, simulationController));
        inputHandler.registerKeyCommand(KeyCode.C, collisionCommand);
    }

    @Override
    public void registerComponent(Component component) {
        switch (component.getName()) {
            default:
                break;
        }
    }

}
