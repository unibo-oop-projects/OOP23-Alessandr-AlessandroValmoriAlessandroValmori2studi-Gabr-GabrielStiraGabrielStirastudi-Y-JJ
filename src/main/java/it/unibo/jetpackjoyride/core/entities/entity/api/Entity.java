package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link Entity} interface defines the common behaviour on which to base
 * the implementation of all entities in the game.
 * 
 * @author gabriel.stira@studio.unibo.it
 */

public interface Entity {
    /**
     * Defines the types of entities in the game.
     */
    enum EntityType {
        OBSTACLE, POWERUP, PICKUP, BARRY
    }

    /**
     * Defines the current status of an entity.
     */
    enum EntityStatus { 
        /**
         * Entity has no active collision and is shown
         */
        CHARGING,
        /**
         * Entity has an active collision and is shown
         */
        ACTIVE,
        /**
         * Entity has no active collision and is shown
         */
        DEACTIVATED,
        /**
         * Entity has no active collision and is not shown
         */
        INACTIVE
    }

    /**
     * Gets the type of the entity.
     *
     * @return The type of the entity.
     */
    EntityType getEntityType();

    /**
     * Gets the current status of the entity.
     *
     * @return The current status of the entity.
     */
    EntityStatus getEntityStatus();

    /**
     * Gets the class which handles the movement characteristics of the entity.
     *
     * @return The movement behaviour of the entity.
     */
    Movement getEntityMovement();

    /**
     * Gets the class which handles the collision characteristics of the entity.
     *
     * @return The collision behaviour of the entity.
     */
    Hitbox getHitbox();

    /**
     * Gets the number of times the entity has been updated (ticks).
     *
     * @return The age of the entity.
     */
    Integer getLifetime();

    /**
     * Sets a new status for the entity.
     *
     * @param entityStatus The new status of the entity.
     */
    void setEntityStatus(EntityStatus entityStatus);

    /**
     * Sets the class which handles the movement characteristics of the entity
     *
     * @param newMovement The new movement of the entity.
     */
    void setEntityMovement(Movement newMovement);

    /**
     * Updates the entity.
     *
     * @param isSpaceBarPressed Is used for entities which interact with the player directly. 
     * As of now, only thePowerUps use this parameter. Other entities ignore it.
     */
    void update(boolean isSpaceBarPressed);

}