package it.unibo.jetpackjoyride.core.entities.coin.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Interface of the coin model.
 * @author yukai.zhou@studio.unibo.it
 */
public interface CoinModel {

   
    /**
     * Updates the state of the coin model.
     */
    void updateCoinModel();

    /**
     * Gets the position of the coin.
     *
     * @return the position of the coin as a Pair of x and y coordinates
     */
    Pair<Double, Double> getPosition();

    /**
     * Sets the position of the coin.
     *
     * @param position the new position of the coin as a Pair of x and y coordinates
     */
    void setPosition(Pair<Double, Double> position);

    /**
     * Gets the size of the coin.
     *
     * @return the size of the coin as a Pair of width and height
     */
    Pair<Double, Double> getSize();

    /**
     * Gets the hitbox of the coin.
     *
     * @return the hitbox of the coin
     */
    Hitbox geHitbox();

    /**
     * Checks if the coin has been collected.
     *
     * @return true if the coin has been collected, false otherwise
     */
    boolean isCollected();

    /**
     * Sets the collected state of the coin.
     *
     * @param isCollected true if the coin has been collected, false otherwise
     */
    void setCollectedState(boolean isCollected);
}