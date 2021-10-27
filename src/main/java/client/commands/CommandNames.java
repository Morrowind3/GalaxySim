package client.commands;

public enum CommandNames {
    LOAD_SIMULATION("Load simulation"), REWIND("Rewind"), SELECT_FILE("File Selection"), SPEED_DOWN("Speed Down"),
    SPEED_UP("Speed Up"), START_PAUSE("Start/Pause"), COLLISION_MODE("Collision Mode"), SHOW_GRID("Show Quadtree"),
    SHOW_PLANET_NAMES("Planet names"), ADD_ASTEROID("Add asteroid"), REMOVE_ASTEROID("Remove asteroid"),
    SHORTEST_ROUTE("Shortest route"), QUICKEST_ROUTE("Quickest route");

    private String name;

    CommandNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
