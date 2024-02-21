package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import java.util.Optional;

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

 
    boolean hasShield();

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
     * Kills Barry based on the type of obstacle.
     *
     * @param type The type of obstacle that killed Barry.
     */
    void kill (ObstacleType type);

    /**
     * Sets the life status of Barry.
     *
     * @param lifeStatus The life status of Barry.
     */
    void setLifeStatus(BarryLifeStatus lifeStatus);




}
