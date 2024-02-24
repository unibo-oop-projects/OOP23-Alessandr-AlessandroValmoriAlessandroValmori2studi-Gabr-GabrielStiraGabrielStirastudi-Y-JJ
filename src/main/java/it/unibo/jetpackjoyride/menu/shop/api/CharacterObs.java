package it.unibo.jetpackjoyride.menu.shop.api;

import javafx.scene.input.KeyCode;

/**
 * An observer interface that notifies the observers subscribed to the observable 
 * {@link ShopView} when a character is pressed, used for unlocking the secret poweup
 * {@link DukeFishron}
 * @param code the keycode of the key pressed
 */

public interface CharacterObs {
    
    void type(KeyCode code);
}
