package client.commands;

import client.AnimationTimerPlus;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SpeedUpCommand implements Command {
    private final AnimationTimerPlus timer;

    public SpeedUpCommand(AnimationTimerPlus timer){
        this.timer = timer;
    }
    @Override
    public void handle(Event event) {
        timer.setSimulationSpeed(timer.getSimulationSpeed() - 40_000_00L);
    }

    @Override
    public CommandNames getCommandName() {
        return CommandNames.SPEED_UP;
    }
}
