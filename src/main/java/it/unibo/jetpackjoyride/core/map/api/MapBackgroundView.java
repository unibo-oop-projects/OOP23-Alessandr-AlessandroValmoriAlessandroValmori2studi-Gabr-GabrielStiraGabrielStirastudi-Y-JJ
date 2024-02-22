package it.unibo.jetpackjoyride.core.map.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.layout.Pane;

/**
 * Interface for the background view of the map. 
 * @author yukai.zhou@studio.unibo.it
 */
public interface MapBackgroundView {

   /**
     * Updates the background view of the map.
     * @param modelData The data necessary for update view from model
     */
    void updateBackgroundView(List<Pair<Double, Double>> modelData);

    /**
     * A method to add the backgroung Node into the Game root.
     * @param gameRoot The main root of GameLoop, use to add nodes
     */
    void addNodeInRoot(Pane gameRoot);

    /**
     * A method to add the backgroung Node into the Game root.
     * @param num The number representing which imageView need to set a new image
     * @param index The index representing the image to set for imageView
     */
    void changeImage(int num, int index);

    /**
    * Determines whether the current image in a specified image view 
    * differs from a reference image in the background images array.
    * @param num   Identifies the image view (0 for the first, any other value for the second).
    * @param index Index of the reference image in the background images array. For the second image view, it uses index + 1.
    * @return true if the current image differs from the reference image, false otherwise.
    */
    boolean isChange(int num, int index);
}
