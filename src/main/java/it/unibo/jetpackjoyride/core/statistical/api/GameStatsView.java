package it.unibo.jetpackjoyride.core.statistical.api;

import java.util.List;

import javafx.scene.layout.Pane;

/**
 * An interface representing a view for game statistics.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameStatsView {

   /**
     * A method to update the view with the data from the game statistics model.
     * @param data the game statistics date provide by data
     */
    void updateDataView(List<Integer> data);

    /**
     * Sets the imageview associated with the statistics(A Score Pane).
     * @param root The main root of the GameLoop
     */
     void setNodeOnRoot(Pane root);
}
