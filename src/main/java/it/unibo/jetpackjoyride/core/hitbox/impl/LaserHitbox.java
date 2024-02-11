package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class LaserHitbox extends AbstractHitbox {
    /* Laser standard dimension */
    private final static Pair<Double, Double> LASERDIMENSIONS = new Pair<>(1150.0, 32.0);

    public LaserHitbox(Pair<Double, Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, LASERDIMENSIONS, startingAngle);
    }
}
