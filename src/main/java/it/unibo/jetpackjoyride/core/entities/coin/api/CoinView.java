package it.unibo.jetpackjoyride.core.entities.coin.api;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface of the coin view.
 * @author yukai.zhou@studio.unibo.it
 */
public interface CoinView {

/**
 * Renders the coin on the specified GraphicsContext.
 *
 * @param gc the GraphicsContext on which to render the coin
 */
    void renderCoin(GraphicsContext gc);

/**
 * Sets the visibility of the coin.
 *
 * @param isVisible true if the coin should be visible, false otherwise
 */
    void setVisible(boolean isVisible);
}
