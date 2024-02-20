package it.unibo.jetpackjoyride.core.map.api;

import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Interface for the MapBackgroung Controller.
 * 
 * @author yukai.zhou@studio.unibo.it
 */
public interface MapBackground {

    /**
     * Updates the background model and view.
     */
    void updateBackground();

    /**
     * A method to set the backgroung on the Game root, to show the background.
     */
    void setMapOnGameRoot();

    /**
     * Method to get the x-coordinate position of the background.
     * 
     * @return A Pair representing the x-coordinate position.
     */
    Pair<Double, Double> getPosX();

    /**
     * Method to get the size of the background.
     * 
     * @return A Pair representing the width and height of the background.
     */
    Pair<Double, Double> getSize();

    /**
     * Resets the background position and reset the game speed.
     */
    void reset();
}
