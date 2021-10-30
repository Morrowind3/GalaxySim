package core;

import java.util.*;


public class RouteCalculator {
    public static List<Hyperlane> quickestRouteTo(Planet from, Planet to) {
        if(from == null || from == to){ return null; }
        final Set<Planet> settledNodes =  new HashSet<>();
        final Set<Planet> unSettledNodes =  new HashSet<>();
        final Map<Planet, Planet> predecessors = new HashMap<>();
        final Map<Planet, Float> distance = new HashMap<>();
        distance.put(from, 0f);
        unSettledNodes.add(from);

        while (unSettledNodes.size() > 0) {
            Planet minimum = null;
            for (Planet planet : unSettledNodes) {
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

        List<Planet> path = new LinkedList<>();

        Planet step = to;
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

    private static void findMinimalDistances(Planet planet, Map<Planet, Planet> predecessors, Set<Planet> unSettledNodes, Map<Planet, Float> distance) {
        for (Hyperlane lane : planet.getHyperlanes()) {
            Planet adjacentPlanet = lane.getOppositePlanet(planet);
            float newShortestDistance = getShortestDistance(planet, distance) + lane.getLength();
            if (getShortestDistance(adjacentPlanet, distance) > newShortestDistance) {
                distance.put(adjacentPlanet, newShortestDistance);
                predecessors.put(adjacentPlanet, planet);
                unSettledNodes.add(adjacentPlanet);
            }
        }
    }

    private static float getShortestDistance(Planet destination, Map<Planet, Float> distance) {
            Float dis = distance.get(destination);

            if (dis == null) {
                return Float.MAX_VALUE;
            } else {
                return dis;
            }
        }

    public static List<Hyperlane> shortestRouteTo(Planet from, Planet to) {
        if(from == null){ return null; }

        final Queue<List<Planet>> queue = new LinkedList<>();
        final Set<Planet> visited = new HashSet<>();

        List<Planet> pathToTarget = new ArrayList<>();
        pathToTarget.add(from);
        queue.add(pathToTarget);

        while (! queue.isEmpty()) {
            pathToTarget = queue.poll();
            from = pathToTarget.get(pathToTarget.size()-1);

            if(from == to) {
                return consolidateLanes(pathToTarget);
            }
            for(Planet nextPlanet : getNeighbors(from)){
                if(!visited.contains(nextPlanet)) {
                    visited.add(nextPlanet);
                    List<Planet> pathToNextNode = new ArrayList<>(pathToTarget);
                    pathToNextNode.add(nextPlanet);
                    queue.add(pathToNextNode);
                }
            }
        }
        return null;
    }

    private static List<Planet> getNeighbors(Planet planet) {
        List<Planet> neighbours = new ArrayList<>();
        for(Hyperlane lane : planet.getHyperlanes()){
            neighbours.add(lane.getOppositePlanet(planet));
        }
        return neighbours;
    }

    private static List<Hyperlane> consolidateLanes(List<Planet> planetPath) {
        List<Hyperlane> lanes = new ArrayList<>();

        for (int i = 0; i < planetPath.size()-1; ++i) {
            Hyperlane lane = planetPath.get(i).getHyperlaneTo(planetPath.get(i + 1));
            lanes.add(lane);
        }
        return lanes;
    };

}