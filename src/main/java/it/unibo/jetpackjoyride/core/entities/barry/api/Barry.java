package it.unibo.jetpackjoyride.core.entities.barry.api;


import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import java.util.Optional;

/**
 * The Barry interface defines the behavior of the player character, Barry, in the Jetpack Joyride game.
 * It provides methods for managing Barry's position, movement, status, hitbox, and shield.
 */
public interface Barry {

    /**
     * Enum representing various statuses of Barry.
     */
    enum BarryStatus {
        WALKING, PROPELLING, FALLING, HEAD_DRAGGING, BURNED, ZAPPED, LASERED, UNDEFINED
    }

    enum BarryLifeStatus{
        ALIVE, DEAD
    }


    /**
     * Gets the current position of Barry as a Pair of X and Y coordinates.
     *
     * @return The current position of Barry.
     */
    Pair<Double, Double> getPosition();

    /**
     * Gets the current status of Barry.
     *
     * @return The current Barry status.
     */
    BarryStatus getBarryStatus();

    /**
     * Moves Barry based on the jumping condition.
     * If jumping is true, Barry propels upwards; otherwise, Barry falls.
     *
     * @param jumping True if Barry is jumping, false if not.
     */
    boolean move(boolean jumping);

    /**
     * Gets the hitbox of Barry.
     *
     * @return The hitbox of Barry.
     */
    Optional<Hitbox> getHitbox();

    /**
     * Checks if Barry has a shield.
     *
     * @return True if Barry has a shield, false otherwise.
     */
    boolean hasShield();

    boolean isAlive();

    void removeShield();

    void setShieldOn();

    void kill (ObstacleType type);

    void setLifeStatus(BarryLifeStatus lifeStatus);

    void updateLimits(double widthRatio, double heightRatio);    

    void setActiveValue(boolean value);

    boolean isActive();

    
}
