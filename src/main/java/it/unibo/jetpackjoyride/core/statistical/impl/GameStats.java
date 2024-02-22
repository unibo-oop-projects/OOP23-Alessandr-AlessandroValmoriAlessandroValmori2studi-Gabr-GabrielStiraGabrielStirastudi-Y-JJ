package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.concurrent.atomic.AtomicInteger;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public final class GameStats implements GameStatsModel {

    private static final long serialVersionUID = 9848324261L;
    public static final AtomicInteger COINS = new AtomicInteger();

    private int bestDistance;
    private int currentDistance;
    private int coins;

    /**
     * Constructs a new GameStats object with default values.
     */
    public GameStats() {
        this.bestDistance = 0;
        this.currentDistance = 0;
        this.coins = 1000;
        GameStats.COINS.set(this.coins);
    }

    /**
     * A method to update the total number of coins.
     *
     * @param coins the number of coins to add
     */
    public static void updateCoins(final int num) {
        GameStats.COINS.getAndUpdate(numOfcoins -> {
            int newNumofCoins = numOfcoins +num;
            return newNumofCoins;
        });
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
