package it.unibo.jetpackjoyride.core.movement;

import java.util.List;

/**
 * A factory interface for creating movement modifiers.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 * @author gabriel.stira@studio.unibo.it
 */
public interface MovementModifierFactory {
    /**
     * Creates a gravity movement modifier with the given value.
     *
     * @param value The value applied to the Y speed. Can also be negative.
     * @return The gravity movement modifier. 
     */
    MovementModifier gravity(Double value);

    /**
     * Creates a bouncing movement modifier with the given map bounds.
     *
     * @param mapBoundDown The lower bound of the map.
     * @param mapBoundUp   The upper bound of the map.
     * @return The bouncing movement modifier.
     */
    MovementModifier bouncing(Double mapBoundDown, Double mapBoundUp);

    /**
     * Creates a bounded movement modifier with the given map bounds.
     *
     * @param mapBoundDown The lower bound of the map.
     * @param mapBoundUp   The upper bound of the map.
     * @return The bounds movement modifier.
     */
    MovementModifier bounds(Double mapBoundDown, Double mapBoundUp);

    /**
     * Combines a list of movement modifiers into a single modifier.
     *
     * @param list The list of movement modifiers to combine.
     * @return The combined movement modifier.
     */
    MovementModifier combineList(List<MovementModifier> list);
}
