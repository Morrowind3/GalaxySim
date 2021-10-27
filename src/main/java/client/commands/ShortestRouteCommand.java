package client.commands;

import client.Mediator;
import client.SimulationController;
import core.CelestialBody;
import core.Hyperlane;
import core.Planet;
import core.RouteCalculator;
import javafx.event.Event;

import java.util.List;
import java.util.Set;

public class ShortestRouteCommand implements Command {


    private final SimulationController simulationController;
    private boolean active = false;
    private final RouteCalculator routeCalculator = new RouteCalculator();
    private static final String ROUTE_COLOUR = "Red";
    private final String originalLaneColour;
    private List<Hyperlane> route;

    public ShortestRouteCommand(SimulationController simulationController){
        this.simulationController = simulationController;
        Hyperlane dummyLane = new Hyperlane(new Planet(null, null), new Planet(null, null));
        originalLaneColour = dummyLane.getColour();
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SHORTEST_ROUTE;
    }

    @Override
    public void handle(Event event) {
        active = !active;
        if(!active){
            markRoute(route);
            return;
        }

        List<CelestialBody> celestialBodies = simulationController.getCelestialBodies();
        Planet biggest = null;
        Planet secondBiggest = null;

        for(CelestialBody celestialBody : celestialBodies){
           if(!(celestialBody instanceof Planet)) continue; //todo: typecheck
            Planet planet = (Planet) celestialBody;
            if(isBigger(planet, biggest)){
                biggest = planet;
            } else
            if(isBigger(planet, secondBiggest)){
                secondBiggest = planet;
            }
        }

        route = routeCalculator.shortestRouteTo(biggest, secondBiggest);
        markRoute(route);

    }

    private void markRoute(List<Hyperlane> route){
        final String laneColour = active ? ROUTE_COLOUR : originalLaneColour;
        final String planetOutline = active ? ROUTE_COLOUR : "Transparent";

        for(Hyperlane lane : route){
            lane.setColour(laneColour);
            lane.getPlanetA().setBorderColour(planetOutline);
            lane.getPlanetB().setBorderColour(planetOutline);
        }
        simulationController.rerender();
    }

    private boolean isBigger(Planet compareFrom, Planet compareTo){
        return compareTo == null || compareFrom.getRadius() > compareTo.getRadius();
    }
}
