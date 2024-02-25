package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

/**
 * @author alessandro.valmori2@studio.unibo.it
 */

/**
 * The Barry interface defines the behavior of the player character, Barry, in
 * the Jetpack Joyride game.
 * It provides methods for managing Barry's position, movement, status, hitbox,
 * and shield.
 */
public interface Barry extends Entity {

    /**
     * Enum representing various statuses of Barry.
     */
    enum PerformingAction {
        /**
         * When Barry is walking
         */
        WALKING, 
        /**
         * When Barry is going upwards
         */
        PROPELLING, 
        /**
         * When Barry is falling down
         */
        FALLING, 
        /**
         * When Barry is sliding his head on the celing
         */
        HEAD_DRAGGING, 
        /**
         * When Barry is hit by a {@link Missile},
         * it is burned
         */
        BURNED, 
        /**
         * When Barry is hit by a {@link Zapper} 
         * it is zapped
         */
        ZAPPED, 
        /**
         * When Barry is hit by a {@link Laser} 
         * it is lasered
         */
        LASERED, 
        /**
         * Un undefined state, which would be an error
         */
        UNDEFINED
    }

    /**
     * Enum representing life statuses of Barry.
     */
    enum BarryLifeStatus {
        /**
         * Barry is alive at the start of the game, 
         * until it is dead
         */
        ALIVE, 
        /**Barry is dead when an obstacle hits him
         * while having no shield on*
         */
        DEAD
    }

    /** 
     * Checks if Barry has a shield.
     */
    boolean hasShield();

    /**
     * Retrieves the current performing action.
     * 
     * @return the current performing action.
     */

    PerformingAction getPerformingAction();

    /**
     * Checks if Barry is alive.
     *
     * @return True if Barry is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Removes the shield from Barry.
     */
    void removeShield();

    /**
     * Sets the shield on Barry.
     */
    void setShieldOn();

    /**
     * A method used to communicate to the player that it has been hit, and
     * by what type of obstacle
     * 
     * @param obstacleType the type of obstacle that hit Barry.
     */
    void hit(ObstacleType obstacleType);

    /**
     * Sets the life status of Barry to either DEAD
     * or ALIVE
     *
     * @param lifeStatus The life status of Barry.
     */
    void setLifeStatus(BarryLifeStatus lifeStatus);

}
