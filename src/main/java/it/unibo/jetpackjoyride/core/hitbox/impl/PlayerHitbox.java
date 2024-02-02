package it.unibo.jetpackjoyride.core.hitbox.impl;

import it.unibo.jetpackjoyride.core.hitbox.AbstractHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public class PlayerHitbox extends AbstractHitbox{

      private final static Pair<Double,Double> PLAYER_DIMENSIONS = new Pair<>(150.0,80.0);
    
    public PlayerHitbox(Pair<Double,Double> hitboxStartingPos, Double startingAngle){
        super(hitboxStartingPos, PLAYER_DIMENSIONS, startingAngle);
        
    }
}
