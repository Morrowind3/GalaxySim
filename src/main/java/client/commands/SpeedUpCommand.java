package client.commands;

import client.AnimationTimerPlus;
import client.SuperController;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SpeedUpCommand implements EventHandler<Event> {
    private final AnimationTimerPlus timer;

    public SpeedUpCommand(AnimationTimerPlus timer){
        this.timer = timer;
    }
    @Override
    public void handle(Event event) {
        timer.setSimulationSpeed(timer.getSimulationSpeed() - 40_000_00L);
    }
}
