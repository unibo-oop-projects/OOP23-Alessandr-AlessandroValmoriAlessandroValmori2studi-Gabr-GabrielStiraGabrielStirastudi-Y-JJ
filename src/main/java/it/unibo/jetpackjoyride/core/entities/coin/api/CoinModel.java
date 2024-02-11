package it.unibo.jetpackjoyride.core.entities.coin.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

public interface CoinModel {

    void updateCoinModel();

    Pair<Double, Double> getPosition();

    void setPosition(Pair<Double, Double> position);

    double getHeight();

    public double getWidth();
}
