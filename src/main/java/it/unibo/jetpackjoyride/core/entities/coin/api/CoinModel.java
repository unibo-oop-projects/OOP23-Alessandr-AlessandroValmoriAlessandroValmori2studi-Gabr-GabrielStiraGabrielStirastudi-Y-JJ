package it.unibo.jetpackjoyride.core.entities.coin.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Interface of the coin model.
 * 
 * @author yukai.zhou@studio.unibo.it
 */
public interface CoinModel {

    /**
     * Update coin model.
     */
    void updateCoinModel();

    /**
     * Get position of the coin.
     * 
     * @return The position of the coin.
     */
    Pair<Double, Double> getPosition();

    /**
     * Set position of the coin.
     * 
     * @param position The position to set.
     */
    void setPosition(Pair<Double, Double> position);

    /**
     * Get size of the coin.
     * 
     * @return The size of the coin.
     */
    Pair<Double, Double> getSize();

    /**
     * Get hitbox of the coin.
     * 
     * @return The hitbox of the coin.
     */
    Hitbox geHitbox();

    /**
     * Check if the coin is collected.
     * 
     * @return True if the coin is collected, false otherwise.
     */
    boolean isCollected();

    /**
     * Set the collected state of the coin.
     * 
     * @param isCollected The collected state to set.
     */
    void setCollectedState(boolean isCollected);
}
