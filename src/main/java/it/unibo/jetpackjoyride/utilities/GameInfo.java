package it.unibo.jetpackjoyride.utilities;

import java.util.concurrent.atomic.AtomicInteger;

public class GameInfo {
    public static final int MAP_WIDTH = 1200; 
    public static final int MAP_HEIGHT = 800;

    public static AtomicInteger moveSpeed = new AtomicInteger(5);

    private static GameInfo instance;

    private double screenWidth;
    private double screenHeight;
    private double screenRatio;

    public static GameInfo getInstance() {
        if (instance == null) {
            synchronized (GameInfo.class) {
                if (instance == null) {
                    instance = new GameInfo();
                }
            }
        }
        return instance;
    }
    

    private GameInfo() {
        this.screenWidth = MAP_WIDTH;
        this.screenHeight = MAP_HEIGHT;
        this.screenRatio = this.screenWidth/this.screenHeight;
    }

    public void updateInfo(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.screenRatio = this.screenWidth/this.screenHeight;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public double getScreenRatio() {
        return screenRatio;
    }

    public void setMoveSpeed(int newSpeed){
        GameInfo.moveSpeed.set(newSpeed);
    }
}
