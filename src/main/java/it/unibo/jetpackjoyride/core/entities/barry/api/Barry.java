package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.hitbox.impl.PlayerHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public interface Barry {

    public enum BarryStatus{
        WALKING, PROPELLING, FALLING, LAND, HEAD_DRAGGING, DEAD;
    }

    public Pair<Double, Double> getPosition();
    

    public BarryStatus getBarryStatus();


    public void move(boolean jumping);

    public PlayerHitbox getHitbox();


}
