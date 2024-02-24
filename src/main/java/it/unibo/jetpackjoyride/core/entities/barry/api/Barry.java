package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import java.util.Optional;

/**
 * @author alessandro.valmori2@studio.unibo.it
 */

/**
 * The Barry interface defines the behavior of the player character, Barry, in the Jetpack Joyride game.
 * It provides methods for managing Barry's position, movement, status, hitbox, and shield.
 */
public interface Barry extends Entity{

    /**
     * Enum representing various statuses of Barry.
     */
    enum PerformingAction{
        WALKING, PROPELLING, FALLING, HEAD_DRAGGING, BURNED, ZAPPED, LASERED, UNDEFINED
    }

    /**
     * Enum representing life statuses of Barry.
     */
    enum BarryLifeStatus{
        ALIVE, DEAD
    }

    /**Checks if Barry has a shield */
    boolean hasShield();

    /**
     * Retrieves the current performing action
     * @return
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
     * by what
     * @param type The type of obstacle that hit Barry.
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
