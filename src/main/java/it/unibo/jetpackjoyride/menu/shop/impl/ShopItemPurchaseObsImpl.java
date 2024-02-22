package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;

public class ShopItemPurchaseObsImpl implements ShopItemPurchaseObs {

    private ShopController shopController;
    private GameStatsController gameStatsController;

    public ShopItemPurchaseObsImpl(ShopController shopController, GameStatsController gameStatsController) {
        this.shopController = shopController;
        this.gameStatsController = gameStatsController;
    }

    @Override
    public void onItemBought(Items item) {
        final var available = this.gameStatsController.getGameStatsModel().getTotCoins();

        if (!this.gameStatsController.getGameStatsModel().getUnlocked().contains(item)) {
            if (item.getItemCost() > available) {
                System.out.println("Not enough funds :(\n");
            } else {
                this.gameStatsController.getGameStatsModel().unlock(item);
                this.gameStatsController.getGameStatsModel().updateCoins(-item.getItemCost());
            }
        }
        shopController.updateView();
    }
}
