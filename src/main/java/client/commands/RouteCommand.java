package client.commands;

import client.SimulationController;
import core.CelestialBody;
import core.CelestialBodyTypes;
import core.Hyperlane;

import java.util.ArrayList;
import java.util.List;

public abstract class RouteCommand implements Command{
    protected boolean active = false;
    protected final SimulationController simulationController;
    protected CelestialBody pointA;
    protected CelestialBody pointB;
    protected List<Hyperlane> route = new ArrayList<>();


    public RouteCommand(SimulationController simulationController){
        this.simulationController = simulationController;
    }

    protected void setPointsBiggest(){
        pointA = null;
        pointB = null;
        List<CelestialBody> celestialBodies = simulationController.getCelestialBodies();

        for(CelestialBody celestialBody : celestialBodies){
            if(celestialBody.getType() != CelestialBodyTypes.PLANET) continue;
            if(isBigger(celestialBody, pointA)){
                pointA = celestialBody;
            } else
            if(isBigger(celestialBody, pointB)){
                pointB = celestialBody;
            }
        }
    }

    private boolean isBigger(CelestialBody compareFrom, CelestialBody compareTo){
            return compareTo == null || compareFrom.getRadius() > compareTo.getRadius();
    }

    abstract void markRoute();

}
