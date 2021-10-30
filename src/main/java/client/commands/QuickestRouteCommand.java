package client.commands;

import client.SimulationController;
import core.Hyperlane;
import core.RouteCalculator;
import javafx.event.Event;

public class QuickestRouteCommand extends RouteCommand implements Command {
    public QuickestRouteCommand(SimulationController simulationController) {
        super(simulationController);
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.QUICKEST_ROUTE;
    }

    @Override
    public void handle(Event event) {
        active = !active;
        if(!active){
            markRoute();
            return;
        }

        setPointsBiggest();
        route = RouteCalculator.quickestRouteTo(pointA, pointB);

        markRoute();
    }

    @Override
    public void markRoute(){
        for(Hyperlane lane : route){
            simulationController.setDrawableSecondaryEmphasis(lane, active);
            simulationController.setDrawableSecondaryEmphasis(lane.getPlanetB(), active);
            simulationController.setDrawableSecondaryEmphasis(lane.getPlanetA(),active);
        }
        simulationController.rerender();
    }

}
