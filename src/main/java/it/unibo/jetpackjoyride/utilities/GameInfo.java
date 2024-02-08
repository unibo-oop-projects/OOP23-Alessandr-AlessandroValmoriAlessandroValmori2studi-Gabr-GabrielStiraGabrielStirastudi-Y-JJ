package it.unibo.jetpackjoyride.utilities;

import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GameInfo {
    private final static double DEFAULTX = 1280;
    private final static double DEFAULTY = 720;
    private Dimension screenSize;
    private double screenHeight;
    private double screenWidth;

    public static AtomicInteger moveSpeed = new AtomicInteger(5);

    private static GameInfo instance;

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
        this.screenWidth = DEFAULTX;
        this.screenHeight = DEFAULTY;
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

    public double getDefaultWidth() {
        return DEFAULTX;
    }

    public double getDefaultHeight() {
        return DEFAULTY;
    }

    public double getScreenRatio() {
        return screenRatio;
    }

    public void setMoveSpeed(int newSpeed){
        GameInfo.moveSpeed.set(newSpeed);
    }
}
