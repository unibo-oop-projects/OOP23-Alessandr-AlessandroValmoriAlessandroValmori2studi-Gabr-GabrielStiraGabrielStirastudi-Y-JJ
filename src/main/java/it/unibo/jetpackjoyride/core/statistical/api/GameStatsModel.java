package it.unibo.jetpackjoyride.core.statistical.api;

import java.io.Serializable;

/**
 * An interface representing the model for game statistics.
 */
public interface GameStatsModel extends Serializable {

    /**
     * A method to retrieve the best distance reached.
     *
     * @return the best distance reached
     */
    int getBestDistance();

    /**
     * A method to retrieve the current distance.
     *
     * @return the current distance
     */
    int getcurrentDistance();


    /**
     * A method to add distance to the current distance.
     */
    void addDistance();

    /**
     * A method to update the game date.
     */
    void updateDate();

}
