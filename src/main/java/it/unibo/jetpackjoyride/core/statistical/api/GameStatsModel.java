package it.unibo.jetpackjoyride.core.statistical.api;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

import java.io.Serializable;
import java.util.Set;

/**
 * An interface representing the model for game statistics.
 */
public interface GameStatsModel extends Serializable {

    /**
     * A method to retrieve the best distance reached.
     *
     * @return the best distance reached
     */
    int getBestDistance();

    /**
     * A method to retrieve the current distance.
     *
     * @return the current distance
     */
    int getcurrentDistance();

    /**
     * A method to retrieve the total number of coins collected.
     *
     * @return the total number of coins collected
     */
    int getTotCoins();

    /**
     * A method to update the total number of coins.
     *
     * @param coins the number of coins to add
     */
    void updateCoins(int coins);

    /**
     * A method to add distance to the current distance.
     */
    void addDistance();

    /**
     * A method to update the game date.
     */
    void updateDate();

    /**
     * A method to retrieve the set of unlocked items.
     *
     * @return the set of unlocked items
     */
    Set<Items> getUnlocked();

    /**
     * A method to unlock the specified items.
     *
     * @param items the items to unlock
     */
    void unlock(Set<Items> items);

}
