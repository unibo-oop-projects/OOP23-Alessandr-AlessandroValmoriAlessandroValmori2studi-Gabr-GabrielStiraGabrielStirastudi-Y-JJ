package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class LaserHitbox extends AbstractHitbox {
    /* Missile standard dimension (maybe in the future methods to grow or reduce the size could be implemented) */
    private final static Pair<Double,Double> LASERDIMENSIONS = new Pair<>(1200.0,150.0);

    public LaserHitbox(Pair<Double,Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, LASERDIMENSIONS, startingAngle);
    }
}
