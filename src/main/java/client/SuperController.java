package client;

import client.view.FileSelectorController;
import client.view.SimulationController;
import client.view.SimulationView;
import client.view.components.Component;
import client.view.FileSelector;
import client.view.scenes.Launcher;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class SuperController implements Mediator {
    private Launcher launcher;
    private FileSelector fileSelector;
    private SimulationController simulationController;
    private FileSelectorController fileSelectorController;

    public boolean isLocalSelected(){
        return true;
    }

    public void loadSimulation(String dataUrl){
        simulationController.loadData(dataUrl);

        AnimationTimer task = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Platform.runLater(() -> simulationController.updateSimulation());
            }
        };
        task.start();
    }

    public void setMainContentCanvas(Canvas mainContent){
        launcher.setCenterCanvas(mainContent);
    }

    public void launchApp(Stage primaryStage){
        primaryStage.setTitle("Flat Galaxy Simulator 2021");
        primaryStage.setWidth(800);
        primaryStage.setHeight(680);

        fileSelectorController = new FileSelectorController(primaryStage, this);
        fileSelector.setMediator(fileSelectorController);

        launcher = new Launcher();
        launcher.Show(primaryStage, fileSelector);
    }


    public void registerSubcontroller(Mediator mediator){
        switch(mediator.getName()){
            case "SimulationController":
                simulationController = (SimulationController) mediator;
        }

    }

    public String getName(){
        return "SuperViewController";
    }

    @Override
    public void registerComponent(Component component) {
        switch (component.getName()) {
            case "FileSelector":
                fileSelector = (FileSelector) component;
                break;
            default:
                break;
        }
    }

}
