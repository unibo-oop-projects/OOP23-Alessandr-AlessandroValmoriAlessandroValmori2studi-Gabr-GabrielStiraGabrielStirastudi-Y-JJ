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
}
