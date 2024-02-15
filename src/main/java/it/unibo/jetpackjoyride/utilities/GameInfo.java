package it.unibo.jetpackjoyride.utilities;

import java.util.concurrent.atomic.AtomicInteger;


public final class GameInfo {
    private static final double DEFAULTX = 1280;
    private static final double DEFAULTY = 720;
    private double screenHeight;
    private double screenWidth;

    public static AtomicInteger moveSpeed = new AtomicInteger(5);

    private static GameInfo gameInfo = new GameInfo();

    private double screenRatio;

    public static GameInfo getInstance() {
        return gameInfo;
    }

    private GameInfo() {
        this.screenWidth = DEFAULTX;
        this.screenHeight = DEFAULTY;
        this.screenRatio = this.screenWidth / this.screenHeight;
    }

    public void updateInfo(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.screenRatio = this.screenWidth / this.screenHeight;
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

    public void setMoveSpeed(int newSpeed) {
        GameInfo.moveSpeed.set(newSpeed);
    }
}
