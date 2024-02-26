package it.unibo.jetpackjoyride.menu.shop.api;

import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopView;
import javafx.scene.input.KeyCode;

/**
 * An observer interface that notifies the observers subscribed to the observable 
 * {@link ShopView} when a character is pressed, used for unlocking the secret powerup.
 * {@link DukeFishron}.
 * @author alessandro.valmori2@studio.unibo.it
 */

public interface CharacterObs {
    /***
     * The method used to notify the observers.
     * @param code the keycode of the key pressed.
     */
    void type(KeyCode code);
}
