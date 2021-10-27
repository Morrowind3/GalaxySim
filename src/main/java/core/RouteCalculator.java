package core;

import java.util.*;

public class RouteCalculator {
    private final Queue<Planet> queue = new LinkedList<>();
    private final Set<Planet> visited = new HashSet<>();
    private Set<Hyperlane> pathLanes = new HashSet<>();

    private void clearData(){
        queue.clear();
        visited.clear();
        pathLanes.clear();
    }

    public Set<Hyperlane> shortestRouteTo(Planet from, Planet to) {
        clearData();
        queue.add(from);
        while(!queue.isEmpty()){
            Planet planet = queue.poll();
            traverseHyperlanes(planet, to, new HashSet<>());
            visited.add(planet);
        }

        return pathLanes;
    }

    //    public List<Hyperlane> cheapestRouteTo(Planet from, Planet to){
//
//
//    }

    private void traverseHyperlanes(Planet from, Planet to, Set<Hyperlane> lanesVisited){
        System.out.println("Visiting planet "  + from.getName());

        if(from.equals(to)){
            if(pathLanes.size() == 0 || pathLanes.size() > lanesVisited.size()){
                pathLanes = lanesVisited;
            }
            return;

        }
        for(Hyperlane lane: from.getHyperlanes()){
            if(lanesVisited.contains(lane)) continue;
            Planet other = lane.getOppositePlanet(from);
            if(!visited.contains(other) && !queue.contains(other)){
                queue.add(other);
            }
            lanesVisited.add(lane);
            traverseHyperlanes(other, to, lanesVisited);
        }
    }

    private void traverseHyperlanes(Planet planet){
        for(Hyperlane lane: planet.getHyperlanes()){
            Planet other = lane.getOppositePlanet(planet);
            if(!visited.contains(other) && !queue.contains(other)){
                queue.add(other);
            }


        }
    }



}