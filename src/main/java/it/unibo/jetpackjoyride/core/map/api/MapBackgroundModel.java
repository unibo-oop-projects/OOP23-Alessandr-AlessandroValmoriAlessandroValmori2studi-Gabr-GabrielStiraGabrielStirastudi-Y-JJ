package it.unibo.jetpackjoyride.core.map.api;

import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Interface for the background model of the map. 
 * @author yukai.zhou@studio.unibo.it
 */
public interface MapBackgroundModel {

    /**
     * Updates the background model of the map.
     */
    void updateBackgroundModel();

    /**
     * A Method to get the x-coordinate position of the background model.
     * 
     * @return A Pair representing the x-coordinate position.
     */
    Pair<Double, Double> getPosX();

    /**
     * A Method to get the size of the background model.
     * 
     * @return A Pair representing the width and height of the background model.
     */
    Pair<Double, Double> getSize();

     /**
     * Resets the background position.
     */
    void reset();
}
