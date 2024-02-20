package it.unibo.jetpackjoyride.core.statistical.api;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * An interface representing a view for game statistics.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameStatsView {

   /**
     * A method to update the view with the data from the game statistics model.
     *
     * @param model the game statistics model
     */
    void updateDataView(List<Integer> data);

    /**
     * Gets the text associated with the statistics(like coins and meter).
     *
     * @return the text
     */
    Text getText();

    /**
     * Gets the image view associated with the statistics(A Score Pane).
     *
     * @return the image view
     */
    ImageView getImageView();
}
