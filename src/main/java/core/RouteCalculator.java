package core;

import java.util.*;


public class RouteCalculator {
    public static List<Hyperlane> quickestRouteTo(CelestialBody from, CelestialBody to) {
        if(from == null || from == to){ return null; }
        final Set<CelestialBody> settledNodes =  new HashSet<>();
        final Set<CelestialBody> unSettledNodes =  new HashSet<>();
        final Map<CelestialBody, CelestialBody> predecessors = new HashMap<>();
        final Map<CelestialBody, Float> distance = new HashMap<>();
        distance.put(from, 0f);
        unSettledNodes.add(from);

        while (unSettledNodes.size() > 0) {
            CelestialBody minimum = null;
            for (CelestialBody planet : unSettledNodes) {
                if (minimum == null) {
                    minimum = planet;
                } else {
                    if (getShortestDistance(planet, distance) < getShortestDistance(minimum, distance)) {
                        minimum = planet;
                    }
                }
            }
            settledNodes.add(minimum);
            unSettledNodes.remove(minimum);
            findMinimalDistances(minimum, predecessors, unSettledNodes, distance);
        }

        final List<CelestialBody> path = new LinkedList<>();
        CelestialBody step = to;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return consolidateLanes(path);
    }

    private static void findMinimalDistances(CelestialBody planet, Map<CelestialBody, CelestialBody> predecessors, Set<CelestialBody> unSettledNodes, Map<CelestialBody, Float> distance) {
        for (Hyperlane lane : planet.getHyperlanes()) {
            CelestialBody adjacentPlanet = lane.getOppositePlanet(planet);
            float newShortestDistance = getShortestDistance(planet, distance) + lane.getLength();
            if (getShortestDistance(adjacentPlanet, distance) > newShortestDistance) {
                distance.put(adjacentPlanet, newShortestDistance);
                predecessors.put(adjacentPlanet, planet);
                unSettledNodes.add(adjacentPlanet);
            }
        }
    }

    private static float getShortestDistance(CelestialBody destination, Map<CelestialBody, Float> distance) {
            Float dis = distance.get(destination);

            if (dis == null) {
                return Float.MAX_VALUE;
            } else {
                return dis;
            }
        }

    public static List<Hyperlane> shortestRouteTo(CelestialBody from, CelestialBody to) {
        if(from == null){ return null; }

        final Queue<List<CelestialBody>> queue = new LinkedList<>();
        final Set<CelestialBody> visited = new HashSet<>();

        List<CelestialBody> pathToTarget = new ArrayList<>();
        pathToTarget.add(from);
        queue.add(pathToTarget);

        while (! queue.isEmpty()) {
            pathToTarget = queue.poll();
            from = pathToTarget.get(pathToTarget.size()-1);

            if(from == to) {
                return consolidateLanes(pathToTarget);
            }
            for(CelestialBody nextPlanet : getNeighbors(from)){
                if(!visited.contains(nextPlanet)) {
                    visited.add(nextPlanet);
                    List<CelestialBody> pathToNextNode = new ArrayList<>(pathToTarget);
                    pathToNextNode.add(nextPlanet);
                    queue.add(pathToNextNode);
                }
            }
        }
        return null;
    }

    private static List<CelestialBody> getNeighbors(CelestialBody planet) {
        List<CelestialBody> neighbours = new ArrayList<>();
        for(Hyperlane lane : planet.getHyperlanes()){
            neighbours.add(lane.getOppositePlanet(planet));
        }
        return neighbours;
    }

    private static List<Hyperlane> consolidateLanes(List<CelestialBody> planetPath) {
        List<Hyperlane> lanes = new ArrayList<>();

        for (int i = 0; i < planetPath.size()-1; ++i) {
            Hyperlane lane = planetPath.get(i).getHyperlaneTo(planetPath.get(i + 1));
            lanes.add(lane);
        }
        return lanes;
    };

}