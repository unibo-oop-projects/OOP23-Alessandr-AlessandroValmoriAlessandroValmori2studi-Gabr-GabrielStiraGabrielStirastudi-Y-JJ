package it.unibo.jetpackjoyride.utilities;


public class GameInfo {
    public static final int MAP_WIDTH = 1200; 
    public static final int MAP_HEIGHT = 800;

    public static int moveSpeed = 5;

    private double screenWidth;
    private double screenHeight;
    private double screenRatio;
    

    public GameInfo() {
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
        GameInfo.moveSpeed = newSpeed;
    }
}
