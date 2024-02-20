package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.layout.Pane;

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
     * Sets the image view associated with the statistics(A Score Pane) on Game.
     * @param root The main root of the GameLoop
     */
    void setScorePaneOnRoot(Pane root);

    /**
     * Gets the game statistics model.
     *
     * @return the game statistics model
     */
    GameStatsModel getGameStatsModel();
}
