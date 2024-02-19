package it.unibo.jetpackjoyride.core.statistical.impl;


import java.util.Set;

import java.util.HashSet;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.utilities.GameInfo;


public final class GameStats implements GameStatsModel {

    private static final long serialVersionUID = 48181123264L;

    private int bestDistance;
    private int totCoins;
    private int currentDistance;
    private Set<Items> unlockedSet = new HashSet<>();
    private boolean isShieldEquipped;
    private int numOfShields;
    
    /**
     * Constructs a new GameStats object with default values.
     */
    public GameStats() {
        this.numOfShields = 0;
        this.isShieldEquipped = false;
        this.bestDistance = 0;
        this.currentDistance = 0;
        this.totCoins = 1000;
    }

    @Override
    public int getTotCoins() {
        return this.totCoins;
    }

    @Override
    public void updateCoins(final int coin) {
        this.totCoins = totCoins + coin;
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
        this.currentDistance = currentDistance + GameInfo.moveSpeed.get();
    }

    @Override
    public void updateDate() {
        this.setBestDistance();
        this.currentDistance = 0;
    }

    @Override
    public String toString() {
        return "GameStats {" 
                + " best distance=" 
                + bestDistance 
                + '}';
    }


    @Override
    public int getcurrentDistance() {
        return this.currentDistance;
    }

    @Override
    public Set<Items> getUnlocked() {
        return this.unlockedSet;
    }

    @Override
    public void unlock(final Set<Items> items) {
        this.unlockedSet.addAll(items);
    }

    @Override
    public void addShields(final int num) {
        System.out.println("CALLED");
        this.numOfShields = num;
    }

    @Override
    public int getNumOfShields() {
        return this.numOfShields;
    }

    @Override
    public boolean isShieldEquipped() {
        return this.isShieldEquipped;
    }

    @Override
    public void setShield(final boolean isShieldEquipped) {
        this.isShieldEquipped = isShieldEquipped;
    }
}
