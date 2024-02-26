package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;

/**
 * The implementation of the {@link ShopItemPurchaseObs},
 * responsible foor handling the attempt to buy an item from
 * the user.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 */
public final class ShopItemPurchaseObsImpl implements ShopItemPurchaseObs {

    private final ShopController shopController;

    /**
     * The constructor.
     * 
     * @param shopController the {@link ShopController} associated with this
     *                       observer.
     */
    public ShopItemPurchaseObsImpl(final ShopController shopController) {
        this.shopController = shopController;

    }

    @Override
    public void onItemBought(final Items item) {
        final var available = GameStats.getCoins();
        if (item.getItemCost() <= available) {
            if (!this.shopController.getUnlocked().contains(item)) {
                this.shopController.unlock(item);
                GameStats.updateCoins(-item.getItemCost());
            }
        }
    }
}
