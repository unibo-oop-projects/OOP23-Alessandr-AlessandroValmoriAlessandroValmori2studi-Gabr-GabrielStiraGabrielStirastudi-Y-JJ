package it.unibo.jetpackjoyride.core.entities.pickups.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

/**
 * The {@link PickUp} interface defines the methods used by the PickUp
 * entities in the game. Currently, two types of pickUp are implemented.
 * PickUps are obstainables which give advantages to the player.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public interface PickUp extends Entity {
    /**
     * Defines the type of pickups implemented in the game.
     */
    enum PickUpType {
        /**
         * The {@link VehiclePickUp} pick-up type
         */
        VEHICLE, 
        /**
         * The {@link ShieldPickUp} pick-up type
         */
        SHIELD
    }

    /**
     * Gets the type of pickups implemented in the game.
     * 
     * @return The type of this pickup.
     */
    PickUpType getPickUpType();
}
