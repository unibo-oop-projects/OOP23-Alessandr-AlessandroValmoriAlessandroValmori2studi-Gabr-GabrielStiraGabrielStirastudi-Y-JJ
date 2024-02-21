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
     * A Method to set the x-coordinate position of the background model.
     * 
     * @param num The num representing which imageView need to be reset .
     */
    void setPositionX(int num);

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

     /**
     * Updates the size of the background based on the screen size.
     * If the screen size has changed, this method adjusts the background accordingly.
     */
    void updateSize();

      /**
     * A Method to get the index use for image.
     * 
     * @return The number representing the image to be displayed.
     */
    int getIndexForImage();

    /**
     * Update the num of index for the next image to displayed.
     */
    void updateImage();
}
