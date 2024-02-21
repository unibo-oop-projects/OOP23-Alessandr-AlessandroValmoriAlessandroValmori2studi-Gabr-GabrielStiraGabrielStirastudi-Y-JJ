package it.unibo.jetpackjoyride.core.entities.coin.api;

import java.util.List;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface of the coin controller.
 * @author yukai.zhou@studio.unibo.it
 */
public interface Coin {

/**
 * Updates the model of the coin.
 */
    void updateModel();

/**
 * Renders the coin.
 * @param gc The canvas's GraphicsContext use to paint
 */
    void render(GraphicsContext gc);

/**
 * Get the model of the coin.
 *
 * @return the model of the coin
 */
    List<Pair<Double, Double>> getModelData();

/**
 * Set the position of the coin.
 *
 * @param position the position to set for the coin
 */
    void setPosition(Pair<Double, Double> position);

/**
 * Set the collected state of the coin.
 *
 * @param isCollected true if the coin is collected, false otherwise
 */
    void setCollectedState(boolean isCollected);

/**
 * Check the collision between coin and player.
 * @return the value 1 in case of collision happend, otherwise 0
 * @param playerHitbox the hitbox of the player, use to check collision with coin
 */
    Integer checkCollision(Hitbox playerHitbox);
}
