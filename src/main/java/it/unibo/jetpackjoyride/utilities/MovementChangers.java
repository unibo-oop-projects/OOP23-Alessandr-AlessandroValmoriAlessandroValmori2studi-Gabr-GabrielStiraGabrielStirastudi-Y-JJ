package it.unibo.jetpackjoyride.utilities;

/**
 * The MovementChangers enum represents different types of movement changers that can affect the movement of an entity.
 * Each enum constant can be stacked multiple times and can be combined with others.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public enum MovementChangers {
    /**
     * Indicates a bouncing behavior.
     * Once the upper or lower bound of the screen is hit, the y speed is inverted.
     */
    BOUNCING,
    /**
     * Indicates a gravity based behavior.
     * The y speed is accelerated downwards.
     */
    GRAVITY,
    /**
     * Indicates an inverse gravity based behavior.
     * The y speed is accelerated upwards.
     */
    INVERSEGRAVITY,
    /**
     * Indicates a bounded behavior.
     * x and y position cannot exceed specified limits.
     */
    BOUNDS
}
