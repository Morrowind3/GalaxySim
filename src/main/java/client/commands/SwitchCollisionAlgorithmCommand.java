package client.commands;

import client.SimulationController;
import core.collisionstrategy.CollisionStrategy;
import javafx.event.Event;

import java.util.ArrayList;
import java.util.List;

public class SwitchCollisionAlgorithmCommand implements Command {

    private final List<CollisionStrategy> strategies = new ArrayList<>();
    private int strategyIndex = 0;

    private SimulationController controller;

    public SwitchCollisionAlgorithmCommand(SimulationController controller){
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        strategyIndex = (strategyIndex + 1) % strategies.size();
        controller.setCollisionStrategy(strategies.get(strategyIndex));
    }

    public void addAlgorithmChoice(CollisionStrategy strategy){
        if(!strategies.contains(strategy)){
            strategies.add(strategy);
        }
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.COLLISION_MODE;
    }
}
