/*package com.fxversion;

import java.util.Random;

public class ObstacleModel {

    private double x, y, velocityX;
    private double width;
    private double height;
    private boolean isWarning;
    private final int warningTime = 320;
    private int timer;

    private Random random = new Random();

    public ObstacleModel (double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 5;
        this.isWarning = true;
        this.timer = 0;
    }

    public void update() {
        if (isWarning) {
            timer++;
            if (timer > warningTime) {
                timer = 0;
                isWarning = false;
                x += velocityX;
            }
        } else {
            x -= velocityX;
            if (x + width < 0) {
                resetPosition();
            }
        }
    }

    private void resetPosition() {
        x = InfiniteScrollingMapFX.MAP_WIDTH;
        y = random.nextDouble() * (InfiniteScrollingMapFX.MAP_HEIGHT - height);
        isWarning = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public boolean isWarning() {
        return isWarning;
    }

    public int getTimer() {
        return timer;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight( ) {
        return this.height;
    }

}
*/