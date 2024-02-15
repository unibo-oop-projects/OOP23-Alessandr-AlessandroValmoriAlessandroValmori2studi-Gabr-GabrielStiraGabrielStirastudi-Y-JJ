package it.unibo.jetpackjoyride.utilities;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;


public final class GameInfo {
    private static final double DEFAULTX = 1280;
    private static final double DEFAULTY = 720;
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
        this.screenRatio = this.screenWidth / this.screenHeight;
    }

    public static void updateBackgroundSize(Scene scene, ImageView backgroundImageView) {
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitWidth(newVal.doubleValue());
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitHeight(newVal.doubleValue());
        });
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
