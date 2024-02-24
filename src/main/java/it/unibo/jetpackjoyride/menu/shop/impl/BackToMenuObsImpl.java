package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

/**The implementation of the {@link BackToMenuObs} interface */
public class BackToMenuObsImpl implements BackToMenuObs{

    /**The shop controller */
    private ShopController shopController;

    /**
     * A constructor takes in a 
     * @param shopController
     */
    public BackToMenuObsImpl(ShopController shopController) {
        this.shopController = shopController;
    }
    /**
     * invokes the method to navigate back to the main menu of the game
     * */
    @Override
    public void goBack() {
        this.shopController.backToMenu();
    }
    
}
