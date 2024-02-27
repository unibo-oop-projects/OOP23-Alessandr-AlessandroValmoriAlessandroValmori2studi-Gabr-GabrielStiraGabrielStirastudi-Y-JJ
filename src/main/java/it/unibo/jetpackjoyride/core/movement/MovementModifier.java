package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.core.movement.Movement.MovCharacterizing;

/**
 * An interface representing a movement modifier. Movement modifiers are used to
 * create modified copies of
 * {@link MovCharacterizing} with altered movement characteristics, as records
 * are immutable.
 * Implementations of this interface define specific behaviors that produce
 * modified copies of characterizing
 * objects, leaving the original instances unchanged.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 * @author gabriel.stira@studio.unibo.it
 */
@FunctionalInterface
public interface MovementModifier {

    /**
     * Applies the movement modifier to the given {@link MovCharacterizing},
     * producing a new instance
     * with altered movement characteristics.
     *
     * @param toModify the characterizing object to which the modifier is applied.
     * @return A new {@link MovCharacterizing} with the modified movement
     *         characteristics.
     */
    MovCharacterizing applyModifier(MovCharacterizing toModify);

}
