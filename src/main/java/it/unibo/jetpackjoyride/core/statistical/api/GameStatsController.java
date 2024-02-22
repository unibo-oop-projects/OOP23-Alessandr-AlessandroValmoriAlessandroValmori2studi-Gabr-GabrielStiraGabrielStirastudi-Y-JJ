package it.unibo.jetpackjoyride.core.statistical.api;

/**
 * An interface representing a controller for game statistics.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameStatsController {
     /**
     * Updates the the current distance.
     */
    void updateCurrentDistance();

    /**
     * Updates the view.
     */
    void updateView();

     /**
     * A method to get the view of GameStats.
     *
     * @param view The view of GameStats
     */
    void getGameStatsView(GameStatsView view);
    /**
     * Saves the game statistics.
     */
    void saveChanged();
}
