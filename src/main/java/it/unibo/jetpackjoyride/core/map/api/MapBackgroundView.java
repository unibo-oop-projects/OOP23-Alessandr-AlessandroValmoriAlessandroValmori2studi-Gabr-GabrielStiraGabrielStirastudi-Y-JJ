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
     * A method to get the pane containing the background view.
     * 
     * @return The pane containing the background view.
     */
    Pane getPane();
}
