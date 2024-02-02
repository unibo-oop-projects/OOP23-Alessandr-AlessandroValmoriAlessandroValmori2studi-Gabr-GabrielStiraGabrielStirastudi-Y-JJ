package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class ZapperHitbox extends AbstractHitbox  {
    /* Zapper standard dimension (maybe in the future methods to grow or reduce the size could be implemented) */
    private final static Pair<Double,Double> ZAPPERDIMENSIONS = new Pair<>(200.0,50.0);

    public ZapperHitbox(Pair<Double,Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, ZAPPERDIMENSIONS, startingAngle);
    }
}
