package client;

import client.view.FileSelectorController;
import client.view.SimulationController;
import client.view.SimulationView;
import client.view.components.Component;
import client.view.FileSelector;
import client.view.scenes.Launcher;
import javafx.stage.Stage;

public class InterfaceController implements Mediator {
    private final Launcher launcher = new Launcher();
    private FileSelector fileSelector;
    private SimulationView simView;

    public boolean isLocalSelected(){
        return true;
    }

    public void loadSimulation(String dataUrl){
        SimulationController simController = new SimulationController( dataUrl, this);
        simController.registerComponent(simView);
        launcher.LaunchSimulation(simView);
    }

    public void launchApp(Stage primaryStage){
        primaryStage.setTitle("Flat Galaxy Simulator 2021");
        primaryStage.setWidth(800);
        primaryStage.setHeight(750);

        FileSelectorController controller = new FileSelectorController(primaryStage, this);
        fileSelector.setMediator(controller);
        launcher.Show(primaryStage, fileSelector);
    }

    @Override
    public void registerComponent(Component component) {
        switch (component.getName()) {
            case "FileSelector":
                fileSelector = (FileSelector) component;
                break;
            case "SimulationView":
                simView = (SimulationView) component;
            default:
                break;
        }
    }

}
