package it.unibo.jetpackjoyride.menu.shop.api;

import java.util.Set;
import java.util.Optional;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopView;

/**
 * The ShopController interface defines the operations for managing the shop in the game.
 * It provides methods for navigation, purchasing items and consulting how much money is possessed.
 * It also has methods to get the currently unlocked powerups. 
 */
public interface ShopController {

    /**
     * Enum representing different items available in the shop, it is used to associate each item
     * to a corresponding powerup, a corresponding order in the {@link ShopView} GUI, and
     * a corresponding description
     * 
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

    /**
     * Shows the shop
     */

    void showTheShop();

    /**
     * Updates the view component of the shop
     */
    void updateView();
    
    /**
     * Saves the modifications made in the shop and navigates back to the main menu
     */
    void backToMenu();

    /**
     * Retrieved the set of unlocked items
     * @return the set of unlocked items
     */
    Set<Items> getUnlocked();

    /**Unlocks a particular 
     * @param item
     */
    void unlock(final Items item);
    /**
     * Saves the game progress onto a file in the user's home directory
     */
     void save();
    
}
