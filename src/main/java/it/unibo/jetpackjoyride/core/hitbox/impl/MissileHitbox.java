package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MissileHitbox extends AbstractHitbox {
    /* Missile standard dimension */
    private final static Pair<Double, Double> MISSILEDIMENSIONS = new Pair<>(150.0, 50.0);

    public MissileHitbox(Pair<Double, Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, MISSILEDIMENSIONS, startingAngle);
    }
}
