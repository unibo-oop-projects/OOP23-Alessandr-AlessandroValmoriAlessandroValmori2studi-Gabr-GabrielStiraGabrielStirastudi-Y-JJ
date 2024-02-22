package it.unibo.jetpackjoyride.menu.shop.impl;

import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

public class ShopModel implements Serializable {

    private static final long serialVersionUID = 12368531123264L;


    private Set<Items> unlockedSet = new HashSet<>();

    /**
     * Constructs a new GameStats object with default values.
     */


    public Set<Items> getUnlocked() {
        return this.unlockedSet;
    }

    public void unlock(final Items item) {
        this.unlockedSet.add(item);
    }

}
