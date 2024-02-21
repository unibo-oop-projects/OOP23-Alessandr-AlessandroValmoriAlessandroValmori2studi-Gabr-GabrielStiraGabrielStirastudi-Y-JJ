package it.unibo.jetpackjoyride.menu.shop.api;

import java.util.Set;
import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;


/**
 * The ShopController interface defines the operations for managing the shop in the game.
 * It provides methods for navigation, purchasing items, managing shields, and saving game progress.
 */
public interface ShopController {

    /**
     * Enum representing different items available in the shop.
     */
    enum Items {
        MRCUDDLES(500, Optional.of(PowerUpType.MRCUDDLES), Optional.of(0), Optional.of("MR CUDDLES\n Too cool not to buy")), 
        SHIELD(100, Optional.empty(), Optional.of(3), Optional.of("SHIELD\n A consumable equippable shield")), 
        STOMPER(250, Optional.of(PowerUpType.LILSTOMPER), Optional.of(1), Optional.of("STOMPER\n Clumsy but robust vehicle")), 
        PROFITBIRD(400, Optional.of(PowerUpType.PROFITBIRD), Optional.of(2), Optional.of("PROFIT BIRD\n Greedy bird, moves like flappy bird")),
        DUKE(666, Optional.of(PowerUpType.DUKEFISHRON), Optional.empty(), Optional.empty());

        private final int cost;
        private final Optional<Integer> order;
        private final Optional<PowerUpType> powerup;
        private final Optional<String> description;

        Items(final int cost, final Optional<PowerUpType> powerup, final Optional<Integer> order, final Optional<String> description) {
            this.cost = cost;
            this.powerup = powerup;
            this.order = order;
            this.description = description;
        }

        /**
         * Retrieves the cost of the item.
         *
         * @return The cost of the item.
         */
        public int getItemCost() {
            return this.cost;
        }

        /**
         * Retrieves the corresponding power-up type, if any.
         *
         * @return The corresponding power-up type.
         */
        public Optional<PowerUpType> getCorresponding() {
            return this.powerup;
        }

        /**
         * Retrieves the order of the item.
         *
         * @return The order of the item.
         */
        public Optional<Integer> getOrder() {
            return this.order;
        }

        /**
         * Retrieves the description of the item.
         *
         * @return The description of the item.
         */
        public Optional<String> getDescription() {
            return this.description;
        }
    }

    GameStatsController getGameStatsController();

    /**
     * Shows the shop
     */

    void showTheShop();

    /**
     * Updates the view component of the shop
     */
    void updateView();
    
    /**
     * Navigates back to the main menu.
     */
    void backToMenu();

    /**
     * Purchases the specified item from the shop.
     *
     * @param item The item to purchase.
     */
    void buy(Items item);

    /**
     * Toggles between equipping and unequipping the shield.
     */
    void toggleEquipUnequipShield();

    /**
     * Retrieves the current balance of the player.
     *
     * @return The current balance of the player.
     */
    int retrieveBalance();

    /**
     * Retrieves the number of shields owned by the player.
     *
     * @return The number of shields owned by the player.
     */
    int getNumOfShields();

    /**
     * Checks if the shield is currently equipped.
     *
     * @return True if the shield is equipped, false otherwise.
     */
    boolean isShieldEquipped();

    /**
     * Retrieves the set of unlocked items.
     *
     * @return The set of unlocked items.
     */
    Set<Items> getUnlocked();

    /**
     * Saves the game progress.
     */
    void save();

    /**
     * Unlocks an item
     * @param item
     */

    void unlock(Items item);
}
