package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

public class BackToMenuObsImpl implements BackToMenuObs{

    private ShopController shopController;

    public BackToMenuObsImpl(ShopController shopController) {
        this.shopController = shopController;
    }

    @Override
    public void goBack() {
        this.shopController.backToMenu();
    }
    
}
