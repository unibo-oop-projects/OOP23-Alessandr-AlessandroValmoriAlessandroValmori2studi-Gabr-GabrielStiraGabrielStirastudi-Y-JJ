package it.unibo.jetpackjoyride.utilities;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton class to hold global game information such as screen dimensions and game speed.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameInfo {
    private static final double DEFAULTX = 1280;
    private static final double DEFAULTY = 720;
    private double screenHeight;
    private double screenWidth;

    private static final Integer INITALGAMESPEED = 5;
    /**
    * The globle speed for all elements of the game.
    */
    public static final AtomicInteger MOVE_SPEED = new AtomicInteger(INITALGAMESPEED);

    /**
     * Nested static class to hold the singleton instance of GameInfo.
     * It use the lazy-load with the initialization-on-demand holder pattern.
     */
    private static class LazyHolder {
        private static final GameInfo GAME_INFO = new GameInfo();
    }

    /**
     * Provides the global singleton instance of GameInfo.
     *
     * @return the singleton instance of GameInfo.
     */
    public static GameInfo getInstance() {
        return LazyHolder.GAME_INFO;
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private GameInfo() {
        this.screenWidth = DEFAULTX;
        this.screenHeight = DEFAULTY;
    }

    /**
     * Updates the screen dimensions stored in this instance.
     *
     * @param screenWidth  the new width of the screen.
     * @param screenHeight the new height of the screen.
     */
    public void updateInfo(final double screenWidth, final double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Gets the current screen width.
     *
     * @return the current screen width.
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the current screen height.
     *
     * @return the current screen height.
     */
    public double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Gets the default screen width.
     *
     * @return the default screen width.
     */
    public double getDefaultWidth() {
        return DEFAULTX;
    }

     /**
     * Gets the default screen height.
     *
     * @return the default screen height.
     */
    public double getDefaultHeight() {
        return DEFAULTY;
    }

     /**
     * Sets the game's move speed.
     *
     * @param newSpeed the new speed to set.
     */
    public void setMoveSpeed(final int newSpeed) {
        GameInfo.MOVE_SPEED.set(newSpeed);
    }

    /**
     * Gets the initial game speed.
     *
     * @return the initial speed of the game.
     */
    public Integer getInitialGameSpeed() {
        return INITALGAMESPEED;
    }
}
