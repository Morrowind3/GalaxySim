package client.commands;

import client.Mediator;
import client.SimulationController;
import core.CelestialBody;
import core.Hyperlane;
import core.Planet;
import core.RouteCalculator;
import javafx.event.Event;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class ShortestRouteCommand extends RouteCommand implements Command{
    public ShortestRouteCommand(SimulationController simulationController){
        super(simulationController);
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SHORTEST_ROUTE;
    }

    @Override
    public void handle(Event event) {
        active = !active;
        if(!active){
            markRoute();
            return;
        }

        setPointsBiggest();
        route = RouteCalculator.shortestRouteTo(pointA, pointB);

        markRoute();
    }

    @Override
    public void markRoute(){
        for(Hyperlane lane : route){
            simulationController.setDrawablePrimaryEmphasis(lane, active);
            simulationController.setDrawablePrimaryEmphasis(lane.getPlanetB(), active);
            simulationController.setDrawablePrimaryEmphasis(lane.getPlanetA(),active);
        }
        simulationController.rerender();
    }

}
