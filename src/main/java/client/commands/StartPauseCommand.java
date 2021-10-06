package client.commands;

import client.AnimationTimerPlus;
import client.SuperController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class StartPauseCommand implements EventHandler<Event>  {
    private final AnimationTimerPlus timer;

    public StartPauseCommand(AnimationTimerPlus timer){
        this.timer = timer;
    }
    @Override
    public void handle(Event event) {
        if(timer.isRunning()){
            timer.stop();
        } else timer.start();
    }
}
