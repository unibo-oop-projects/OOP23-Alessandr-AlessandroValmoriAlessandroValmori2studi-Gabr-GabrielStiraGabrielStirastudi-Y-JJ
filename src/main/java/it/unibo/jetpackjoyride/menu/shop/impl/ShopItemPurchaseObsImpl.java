package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;

public class ShopItemPurchaseObsImpl implements ShopItemPurchaseObs {

    private ShopController shopController;
    private ShopModel shopModel;

    public ShopItemPurchaseObsImpl(ShopController shopController, ShopModel shopModel) {
        this.shopController = shopController;
        this.shopModel = shopModel;
    }

    @Override
    public void onItemBought(Items item) {
        final var available = GameStats.getCoins();

        if (!this.shopModel.getUnlocked().contains(item)) {
            if (item.getItemCost() > available) {
                System.out.println("Not enough funds :(\n");
            } else {
                this.shopModel.unlock(item);
                GameStats.updateCoins(-item.getItemCost());
            }
        }
        shopController.updateView();
    }
}
