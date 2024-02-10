package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.core.hitbox.impl.PlayerHitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public interface Barry {

    enum BarryStatus {
        WALKING, PROPELLING, FALLING, HEAD_DRAGGING, BURNED, ZAPPED, LASERED
    }

    Pair<Double, Double> getPosition();

    BarryStatus getBarryStatus();

    void move(boolean jumping);

    PlayerHitbox getHitbox();

    boolean hasShield();

}
