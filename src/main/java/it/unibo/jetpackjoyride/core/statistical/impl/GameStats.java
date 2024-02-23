package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.concurrent.atomic.AtomicInteger;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;


/**
 * A class implementing the GameStatsModel interface.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameStats implements GameStatsModel {

    private static AtomicInteger coins = new AtomicInteger();

    private int bestDistance;
    private int currentDistance; 

     /**
     * Constructs a new Gamestats and it load the data from file.
     */
    public GameStats() {
        GameStatsIO.loadFromFile(this, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH));
    }

/**
 * Updates the total coins count by adding a specified number to the current total.
 * And ensures that the total coins count does not become negative.
 * 
 * @param num the number of coins to add to the total count. This number can be negative.
 */
    public static void updateCoins(final int num) {
        coins.getAndUpdate(value -> Math.max(value + num, 0));
    }

/**
 * Gests the current total number of coins.
 * 
 * @return the total number of coins
 */
    public static int getCoins() {
        return coins.get();
    }

    @Override
    public void setCoins(final int num) {
        coins.set(num);
    }

    @Override
    public void setCurrentDistance(final int distance) {
         currentDistance = distance;
    }

    @Override
    public void setBestDistance(final int distance) {
         bestDistance = distance;
    }

    @Override
    public int getBestDistance() {
        return bestDistance;
    }

    private void setBestDistance() {
        if (currentDistance > bestDistance) {
             bestDistance = currentDistance;
        }
    }

    @Override
    public void addDistance() {
        this.currentDistance = currentDistance + GameInfo.MOVE_SPEED.get();
    }

    @Override
    public void updateDate() {
        this.setBestDistance();
        this.currentDistance = 0;
    }

    @Override
    public int getcurrentDistance() {
        return this.currentDistance;
    }
}
