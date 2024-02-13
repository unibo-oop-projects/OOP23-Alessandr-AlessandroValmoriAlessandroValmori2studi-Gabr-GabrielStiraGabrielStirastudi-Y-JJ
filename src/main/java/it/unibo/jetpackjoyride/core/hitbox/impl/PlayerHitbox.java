package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.api.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class PlayerHitbox extends AbstractHitbox {
    /* Player standard dimension */
    private final static Pair<Double, Double> PLAYER_DIMENSIONS = new Pair<>(120.0, 60.0);

    public PlayerHitbox(Pair<Double, Double> hitboxStartingPos, Double startingAngle) {
        super(hitboxStartingPos, PLAYER_DIMENSIONS, startingAngle);

    }
}
