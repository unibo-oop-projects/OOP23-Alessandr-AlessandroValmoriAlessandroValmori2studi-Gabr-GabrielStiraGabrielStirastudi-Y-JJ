package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * The {@link HitboxImpl} class represents an instance of the {@link AbstractHitbox}
 * class. Specifying a position, a dimension and an initial angle, an hitbox can be
 * created. 
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public final class HitboxImpl extends AbstractHitbox {
    /**
     * Constructor used to generate an instance of HitboxImpl.
     * 
     * @param hitboxStartingPos The initial position of the hitbox.
     * @param hitboxDimensions The dimensions of the hitbox.
     * @param initialAngle The initial angle of rotation of the hitbox.
     */
    public HitboxImpl(final Pair<Double, Double> hitboxStartingPos, final Pair<Double, Double> hitboxDimensions, final Double initialAngle) {
        super(hitboxStartingPos, hitboxDimensions, initialAngle);
    }
}