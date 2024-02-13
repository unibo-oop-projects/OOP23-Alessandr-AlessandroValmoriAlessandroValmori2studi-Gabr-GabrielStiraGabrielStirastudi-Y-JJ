package it.unibo.jetpackjoyride.core.entities.coin.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public interface Coin {
    void update();
    
    void render();

    void setPosition(Pair<Double, Double> position);

    boolean isCollected();

    void setCollectedState(boolean isCollected);
    
    void setVisible(boolean isvisible);

    Pair<Double, Double> getPosition();

    Hitbox geHitbox();

}
