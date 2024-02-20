package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.Node;

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
     * Gets the image view associated with the statistics(A Score Pane) from View.
     *
     * @return the image view with Text given by View
     */
    Node getScoreNode();

    /**
     * Gets the game statistics model.
     *
     * @return the game statistics model
     */
    GameStatsModel getGameStatsModel();
}
