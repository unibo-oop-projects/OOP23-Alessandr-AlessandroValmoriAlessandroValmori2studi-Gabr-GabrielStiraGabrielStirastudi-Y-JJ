package it.unibo.jetpackjoyride.core.entities.coin.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface of the coin view.
 * @author yukai.zhou@studio.unibo.it
 */
public interface CoinView {

    /**
     * Renders the coin on the specified GraphicsContext.
     *
     * @param gc The GraphicsContext on which to render the coin
     * @param modelData The data nacessary for update the view of coin
     */
    void renderCoin(GraphicsContext gc, List<Pair<Double, Double>> modelData);

    /**
     * Sets the visibility of the coin.
     *
     * @param isVisible true if the coin should be visible, false otherwise
     */
    void setVisible(boolean isVisible);

}
