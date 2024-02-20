package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;

public class BackToMenuObsImpl implements BackToMenuObs{

    private ShopControllerImpl shopController;

    public BackToMenuObsImpl(ShopControllerImpl shopController) {
        this.shopController = shopController;
    }

    @Override
    public void goBack() {
        this.shopController.backToMenu();
    }
    
}
