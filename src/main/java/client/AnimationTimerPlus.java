package client;

import javafx.animation.AnimationTimer;

public class AnimationTimerPlus extends AnimationTimer {
    private long lastUpdate = 0 ;
    private volatile long simulationSpeed = 80_000_00L;

    private volatile boolean isRunning;

    @Override
    public void handle(long now) {
        //should be overridden anonymously
    }

    public void setSimulationSpeed(long speed){
        if(speed < 0L){
            speed = 0L;
        }
        simulationSpeed = speed;
    }
    public long getSimulationSpeed(){
        return simulationSpeed;
    }

    @Override
    public void start(){
        super.start();
        isRunning = true;
    }

    @Override
    public void stop(){
        super.stop();
        isRunning = false;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public boolean shouldPause(long now){
        long timeSinceLastUpdate = now - lastUpdate;
        if (timeSinceLastUpdate <= simulationSpeed) {
            return true;
        }
        lastUpdate = now;
        return false;
    }
}
