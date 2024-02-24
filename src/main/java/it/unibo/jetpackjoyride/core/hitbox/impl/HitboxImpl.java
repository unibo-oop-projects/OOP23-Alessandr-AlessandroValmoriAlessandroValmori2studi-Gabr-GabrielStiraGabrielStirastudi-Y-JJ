package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class HitboxImpl extends AbstractHitbox {
    public HitboxImpl(final Pair<Double, Double> hitboxStartingPos, final Pair<Double, Double> hitboxDimensions, final Double initialAngle) {
        super(hitboxStartingPos, hitboxDimensions, initialAngle);
    }
}