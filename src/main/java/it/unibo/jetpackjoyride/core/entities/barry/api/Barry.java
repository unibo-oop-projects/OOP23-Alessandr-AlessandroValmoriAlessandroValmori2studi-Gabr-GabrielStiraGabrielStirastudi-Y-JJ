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

    /**
     * Enum representing life statuses of Barry.
     */
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
     * @return True if the movement was successful, false otherwise.
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

    /**
     * Updates the limits of Barry based on width and height ratios.
     *
     * @param widthRatio The ratio for the width of Barry.
     * @param heightRatio The ratio for the height of Barry.
     */
    void updateLimits(double widthRatio, double heightRatio);    

    /**
     * Sets the active value of Barry.
     *
     * @param value The active value of Barry.
     */
    void setActiveValue(boolean value);

    /**
     * Checks if Barry is active.
     *
     * @return True if Barry is active, false otherwise.
     */
    boolean isActive();

}
