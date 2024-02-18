package it.unibo.jetpackjoyride.core.entities.coin.api;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Coin {
    void updateModel();
    
    void render();

    CoinModel getModel();

    void setPosition(Pair<Double, Double> position);

    void setCollectedState(boolean isCollected);
    

}
