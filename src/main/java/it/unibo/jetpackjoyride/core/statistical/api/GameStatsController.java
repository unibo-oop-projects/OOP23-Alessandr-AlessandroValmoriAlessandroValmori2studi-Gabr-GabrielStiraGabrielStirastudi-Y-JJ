package it.unibo.jetpackjoyride.core.statistical.api;

import java.util.List;

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
     * Gives the data for the view to update.
     * @return A List that containg the date of model for view
     */
    List<Integer> dataForView(); 

    /**
     * Saves the game statistics.
     */
    void saveChanged();
}
