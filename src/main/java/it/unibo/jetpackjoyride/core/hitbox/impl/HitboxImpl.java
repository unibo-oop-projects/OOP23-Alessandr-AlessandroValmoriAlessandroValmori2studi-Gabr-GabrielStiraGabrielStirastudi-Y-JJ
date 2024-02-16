package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class HitboxImpl extends AbstractHitbox {
    public HitboxImpl(Pair<Double, Double> hitboxStartingPos, Pair<Double, Double> hitboxDimensions, Double startingAngle) {
        super(hitboxStartingPos, hitboxDimensions, startingAngle);
    }
}