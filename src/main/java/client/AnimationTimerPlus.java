package client;

import javafx.animation.AnimationTimer;

public class AnimationTimerPlus extends AnimationTimer {
    private long lastUpdate = 0 ;
    private volatile long simulationSpeed = 80_000_00L;

    private volatile boolean isRunning;
    private volatile boolean taskReady = true;

    public AnimationTimerPlus(){
        super();
    }

    @Override
    public void handle(long now) {
        //should be overridden anonymously
    }

    public AnimationTimerPlus(boolean taskNeedsSetup){
        super();
        if(taskNeedsSetup) taskReady = false;
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

    public void ready(){
        this.taskReady = true;
    }
    public boolean isReady(){ return taskReady; }

    @Override
    public void start(){
        if(!taskReady) return;
        super.start();
        isRunning = true;
    }

    @Override
    public void stop(){
        if(!taskReady) return;
        super.stop();
        isRunning = false;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public boolean shouldContinue(long now){
        long timeSinceLastUpdate = now - lastUpdate;
        if (timeSinceLastUpdate <= simulationSpeed) {
            return false;
        }
        lastUpdate = now;
        return true;
    }
}
