package it.unibo.jetpackjoyride.core.map.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.layout.Pane;

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
     * @param gameRoot The main root of GameLoop, use to add nodes
     */
    void setMapOnGameRoot(Pane gameRoot);

    /**
     * Method to get the x-coordinate position of the background.
     * 
     * @return A List witch contain the  x-coordinate position and size.
     */
    List<Pair<Double, Double>> getModelData();

    /**
     * Resets the background position and reset the game speed.
     */
    void reset();
}
