package client.commands;

import client.SimulationController;
import core.CelestialBody;
import core.Hyperlane;
import core.Planet;
import core.RouteCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class RouteCommand implements Command{
    protected boolean active = false;
    protected final SimulationController simulationController;
    protected Planet pointA;
    protected Planet pointB;
    protected List<Hyperlane> route = new ArrayList<>();


    public RouteCommand(SimulationController simulationController){
        this.simulationController = simulationController;
    }

    protected void setPointsBiggest(){
        final List<CelestialBody> celestialBodies = simulationController.getCelestialBodies();

        for(CelestialBody celestialBody : celestialBodies){
            if(!(celestialBody instanceof Planet)) continue; //todo: typecheck
            Planet planet = (Planet) celestialBody;
            if(isBigger(planet, pointA)){
                pointA = planet;
            } else
            if(isBigger(planet, pointB)){
                pointB = planet;
            }
        }
    }

    private boolean isBigger(Planet compareFrom, Planet compareTo){
            return compareTo == null || compareFrom.getRadius() > compareTo.getRadius();
    }

    abstract void markRoute();

}
