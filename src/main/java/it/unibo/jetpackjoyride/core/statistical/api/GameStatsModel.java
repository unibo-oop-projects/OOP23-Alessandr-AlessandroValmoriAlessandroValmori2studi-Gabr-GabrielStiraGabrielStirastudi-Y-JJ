package it.unibo.jetpackjoyride.core.statistical.api;

/**
 * An interface representing the model for game statistics.
 */
public interface GameStatsModel  {

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

    /**
     * A method to set the coins number.
     * @param num The num to set for coins
     */
    void setCoins(int num);

     /**
     * A method to set the new distance.
     * @param distance The set a new value for current distance
     */
    void setCurrentDistance(int distance);

     /**
     * A method to set the new distance.
     * @param distance The set a new value for current distance
     */
    void setBestDistance(int distance);
}
