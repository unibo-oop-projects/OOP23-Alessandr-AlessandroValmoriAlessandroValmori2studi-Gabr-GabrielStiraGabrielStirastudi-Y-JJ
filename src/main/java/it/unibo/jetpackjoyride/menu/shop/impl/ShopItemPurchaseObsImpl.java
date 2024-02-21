package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;

public class ShopItemPurchaseObsImpl implements ShopItemPurchaseObs {

    private ShopController shopController;

    public ShopItemPurchaseObsImpl(ShopController shopController) {
        this.shopController = shopController;
    }

    @Override
    public void onItemBought(Items item) {
        shopController.buy(item);
        shopController.updateView();
    }
}
