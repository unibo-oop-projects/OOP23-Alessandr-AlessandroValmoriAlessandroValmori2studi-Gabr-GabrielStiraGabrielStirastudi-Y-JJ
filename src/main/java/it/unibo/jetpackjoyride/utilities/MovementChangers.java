package it.unibo.jetpackjoyride.utilities;

/**
 * The MovementChangers enum represents different types of movement changers that can affect the movement of an entity.
 * Each enum constant can be stacked multiple times and can be combined with others.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public enum MovementChangers {
    /**
     * Indicates bouncing behavior.
     * Once the upper or lower bound of the screen is hit, the y speed is inverted.
     */
    BOUNCING,
    /**
     * Indicates gravity behavior.
     * The y speed is accelerated downwards.
     */
    GRAVITY,
    /**
     * Indicates inverse gravity behavior.
     * The y speed is accelerated upwards.
     */
    INVERSEGRAVITY,
    /**
     * Indicates bounds behavior.
     * x and y will only vary between specified limits.
     */
    BOUNDS
}
