package it.unibo.jetpackjoyride.menu.shop.api;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;


/**
 * Observer interface that notifies the subscribers to the observable ShopView 
 * when a "buy" button is pressed .
 * @author alessandro.valmori2@studio.unibo.it
 */
public interface ShopItemPurchaseObs {

    /**
     * The method to notify the observers that an item 
     * has been clicked.
     * @param item @param item the {@link Items} representing the item that was clicked.
     */
    void onItemBought(Items item);
}
