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

    /**
     * A method to set whether a shield is equipped.
     *
     * @param isShieldEquipped true if a shield is equipped, false otherwise
     */
    void setShield(boolean isShieldEquipped);

    /**
     * A method to add shields to the player's inventory.
     *
     * @param num the number of shields to add
     */
    void addShields(int num);

    /**
     * A method to retrieve the number of shields in the player's inventory.
     *
     * @return the number of shields
     */
    int getNumOfShields();

    /**
     * A method to check if a shield is currently equipped.
     *
     * @return true if a shield is equipped, false otherwise
     */
    boolean isShieldEquipped();
}
