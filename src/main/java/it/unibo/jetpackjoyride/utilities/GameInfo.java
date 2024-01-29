package it.unibo.jetpackjoyride.utilities;


public class GameInfo {
    public static int moveSpeed = 2;

    private double screenWidth;
    private double screenHeight;
    private double screenRatio;
    

    public GameInfo(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
