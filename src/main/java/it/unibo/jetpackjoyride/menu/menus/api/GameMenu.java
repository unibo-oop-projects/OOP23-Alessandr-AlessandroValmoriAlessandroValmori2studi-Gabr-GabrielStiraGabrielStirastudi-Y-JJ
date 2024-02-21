package it.unibo.jetpackjoyride.menu.menus.api;

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
}
