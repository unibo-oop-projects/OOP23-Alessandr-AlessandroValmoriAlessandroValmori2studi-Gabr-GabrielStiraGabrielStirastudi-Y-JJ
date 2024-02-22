package it.unibo.jetpackjoyride.menu.shop.impl;

import java.util.Deque;
import java.util.LinkedList;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import javafx.scene.input.KeyCode;

public class CharacterImpl implements CharacterObs{

    private ShopController shopController;
    private ShopModel shopModel;
    private final Deque<String> characters;
    private static final String PASSWORD = "TRUFFLEWORM";
    private static final int PW_LEN = PASSWORD.length(); 
    

    public CharacterImpl(ShopController shopController, ShopModel shopModel){
        this.shopController = shopController;
        this.shopModel = shopModel;
        this.characters = new LinkedList<>();
    }

    @Override
    public void type(KeyCode code) {
        if (!this.shopModel.getUnlocked().contains(Items.DUKE)) {
            final StringBuilder sb = new StringBuilder();
           
            characters.addLast(code.getChar());
            if (this.characters.size() == PW_LEN+1) {
                this.characters.removeFirst();
           
                for (final var ch : this.characters) {
                    sb.append(ch);
                }
                final String concatenatedString = sb.toString();
                if (concatenatedString.equals(PASSWORD)) {
                    
                    this.shopModel.unlock(Items.DUKE);

                    this.shopController.updateView();
                }
            }
        }
    }
    
}
