package client.commands;

public enum CommandNames {
    LOAD_SIMULATION("Load simulation"), REWIND("Rewind"), SELECT_FILE("File Selection"), SPEED_DOWN("Speed Down"),
    SPEED_UP("Speed Up"), START_PAUSE("Start/Pause"), COLLISION_MODE("Collision Mode"), SHOW_GRID("Show Quadtree grid"),
    SHOW_PLANET_NAMES("Show planet names");

    private String name;

    CommandNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
