package core;

import java.util.*;

public class RouteCalculator {

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