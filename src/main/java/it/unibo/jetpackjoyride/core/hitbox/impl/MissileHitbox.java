package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MissileHitbox extends AbstractHitbox {
    /* Missile standard dimension (maybe in the future methods to grow or reduce the size could be implemented) */
    private final static Pair<Double,Double> MISSILEDIMENSIONS = new Pair<>(200.0,50.0);

    public MissileHitbox(Pair<Double,Double> hitboxStartingPos) {
        super(hitboxStartingPos, MISSILEDIMENSIONS);
    }

}
