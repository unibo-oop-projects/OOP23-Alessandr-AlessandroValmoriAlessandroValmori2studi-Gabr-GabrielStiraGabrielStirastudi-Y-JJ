package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.concurrent.atomic.AtomicInteger;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;


public final class GameStats implements GameStatsModel {

    private static AtomicInteger coins = new AtomicInteger();

    private int bestDistance;
    private int currentDistance; 

    public GameStats() {
        GameStatsIO.loadFromFile(this, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH));
    }

    public static void updateCoins(final int num) {
        coins.getAndUpdate(value -> Math.max(value + num, 0));
    }

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
