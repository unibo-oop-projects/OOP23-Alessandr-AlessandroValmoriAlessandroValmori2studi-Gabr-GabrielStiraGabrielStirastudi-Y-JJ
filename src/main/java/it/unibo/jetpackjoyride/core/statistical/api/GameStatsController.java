package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * An interface representing a controller for game statistics.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameStatsController {
     /**
     * Updates the model.
     */
    void updateModel();

    /**
     * Updates the view.
     */
    void updateView();

    /**
     * Gets the text associated with the statistics(like coins and meter).
     *
     * @return the text
     */
    Text getText();

    /**
     * Gets the image view associated with the statistics(A Score Pane).
     *
     * @return the image view
     */
    ImageView getImageView();

    /**
     * Gets the game statistics model.
     *
     * @return the game statistics model
     */
    GameStatsModel getGameStatsModel();
}
