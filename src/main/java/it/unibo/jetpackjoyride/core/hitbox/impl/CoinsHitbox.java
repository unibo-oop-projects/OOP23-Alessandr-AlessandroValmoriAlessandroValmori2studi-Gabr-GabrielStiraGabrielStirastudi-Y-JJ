package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class CoinsHitbox extends AbstractHitbox{

    private final static Pair<Double,Double> CoinDimensions = new Pair<>(30.0,30.0);
    public CoinsHitbox(Pair<Double, Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, CoinDimensions, startingAngle);
    }
    
}
