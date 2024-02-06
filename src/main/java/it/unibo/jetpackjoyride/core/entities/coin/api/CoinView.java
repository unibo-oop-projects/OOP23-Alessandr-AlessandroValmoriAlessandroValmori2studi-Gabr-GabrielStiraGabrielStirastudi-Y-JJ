package it.unibo.jetpackjoyride.core.entities.coin.api;

import javafx.scene.canvas.GraphicsContext;

/**
 * coinView
 */
public interface CoinView {

    void renderCoin(GraphicsContext gc);

    void setVisible(boolean isvisible);
    
}