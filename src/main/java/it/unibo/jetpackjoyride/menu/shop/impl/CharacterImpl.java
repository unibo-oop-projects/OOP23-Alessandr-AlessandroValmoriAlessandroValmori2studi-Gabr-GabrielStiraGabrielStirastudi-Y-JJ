package it.unibo.jetpackjoyride.menu.shop.impl;

import java.util.Deque;
import java.util.LinkedList;

import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import javafx.scene.input.KeyCode;

/**
 * The implementation of the {@link CharacterObs} interface,
 * responsible for handling a key press, in order to unlock
 * the secret powerup, {@link DukeFishron}.
 * @author alessandro.valmori2@studio.unibo.it
 */

public class CharacterImpl implements CharacterObs {

    /** The shop controller .*/
    private final ShopController shopController;
    /** A Deque used to store the characters pressed .*/
    private final Deque<String> characters;
    /** The password which needs to be matched .*/
    private static final String PASSWORD = "TRUFFLEWORM";

    /** The constructor.
     * 
     * @param shopController the {@link ShopController} class
    */
    public CharacterImpl(final ShopController shopController) {
        this.shopController = shopController;
        this.characters = new LinkedList<>();
    }

    /**
     * A method used for checking wether the password typed mathes the password
     * if so, alerts the {@link ShopController} of the recent unlocking.
     */
    @Override
    public void type(final KeyCode code) {
        if (!this.shopController.getUnlocked().contains(Items.DUKE)) {
            final StringBuilder sb = new StringBuilder();

            characters.addLast(code.getChar());
            if (this.characters.size() == PASSWORD.length() + 1) {
                this.characters.removeFirst();

                for (final var ch : this.characters) {
                    sb.append(ch);
                }

                final String concatenatedString = sb.toString();
                if (PASSWORD.equals(concatenatedString)) {
                    this.shopController.unlock(Items.DUKE);
                }
            }
        }
    }
}
