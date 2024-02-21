package it.unibo.jetpackjoyride.menu.menus.api;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import javafx.stage.Stage;


/**
 * This interface shows the main methods of GameMenu that provide  to the other class.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameMenu {
    /**
     * A method to show the game menu on Screen.
     */
    void showMenu();

    /**
     * Removes the listeners attached to the scene.
     */
    void removeListener();

    /**
     * A method to get the game statistics controller.
     *
     * @return the game statistics controller
     */
    GameStatsController getGameStatsHandler();

     /**
     * Gets the primary stage of the game menu.
     * 
     * @return The primary stage of the game menu.
     */
    Stage getStage();

    /**
     * Sets the visibility of the menu.
     *
     * @param isVisible whether the menu is visible
     */
    void setVisible(final boolean isVisible);
}
