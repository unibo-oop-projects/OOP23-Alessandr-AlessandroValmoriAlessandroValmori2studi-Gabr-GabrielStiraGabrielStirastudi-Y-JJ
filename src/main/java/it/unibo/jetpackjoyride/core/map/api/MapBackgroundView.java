package it.unibo.jetpackjoyride.core.map.api;

import javafx.scene.layout.Pane;

/**
 * Interface for the background view of the map. 
 * @author yukai.zhou@studio.unibo.it
 */
public interface MapBackgroundView {

   /**
     * Updates the background view of the map.
     */
    void updateBackgroundView();

    /**
     * A method to add the backgroung Node into the Game root.
     * @param gameRoot The main root of GameLoop, use to add nodes
     */
    void addNodeInRoot(Pane gameRoot);
}
