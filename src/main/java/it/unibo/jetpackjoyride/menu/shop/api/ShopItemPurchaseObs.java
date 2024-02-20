package it.unibo.jetpackjoyride.menu.shop.api;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

public interface ShopItemPurchaseObs {
    void onItemBought(Items item);
}
