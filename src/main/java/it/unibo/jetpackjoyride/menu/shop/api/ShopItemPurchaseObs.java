package it.unibo.jetpackjoyride.menu.shop.api;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;


/**
 * Observer interface that notifies the subscribers to the observable ShopView 
 * when a "buy" button is pressed 
 * @param item the {@link Items} representing the item that was bought
 */
public interface ShopItemPurchaseObs {
    void onItemBought(Items item);
}
