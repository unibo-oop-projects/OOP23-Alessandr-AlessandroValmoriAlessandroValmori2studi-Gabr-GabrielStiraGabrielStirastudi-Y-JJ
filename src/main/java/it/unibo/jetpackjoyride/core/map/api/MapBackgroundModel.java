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
     * A Method to get the index use for image.
     * 
     * @return The number representing the image to be displayed.
     */
    int getIndexForImage();

    /**
     * Update the num of index for the next image to displayed.
     */
    void updateIndexForImage();

   /**
     * A Method to set the map's size, and should be called when the Screen Size changes.
     * @param width The new width of the map.
     * @param height The new height of the map.
     */
    void setMapSize(double width, double height);

    /**
     * A Method to corrects the map position based on the given width ratios.
     * @param widthRatio The ratio by which the width of the background should be adjusted.
     * @param widthRatio1 Another width ratio, for the second map Image.
     */
    void correctBackgroundPos(double widthRatio, double widthRatio1);
}
