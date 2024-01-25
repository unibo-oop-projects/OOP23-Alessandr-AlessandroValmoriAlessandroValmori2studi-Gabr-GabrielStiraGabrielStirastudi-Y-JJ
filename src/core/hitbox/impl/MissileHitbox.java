package core.hitbox.impl;

import core.hitbox.AbstractHitbox;
import core.utilities.Pair;

public class MissileHitbox extends AbstractHitbox {
    /* Missile standard dimension (maybe in the future methods to grow or reduce the size could be implemented) */
    private final static Pair<Double,Double> MISSILEDIMENSIONS = new Pair<>(30.0,10.0);

    public MissileHitbox(Pair<Double,Double> hitboxStartingPos) {
        super(hitboxStartingPos, MISSILEDIMENSIONS);
    }

}
