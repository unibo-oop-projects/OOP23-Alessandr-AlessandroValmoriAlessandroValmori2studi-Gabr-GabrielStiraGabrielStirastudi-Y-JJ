package it.unibo.jetpackjoyride.menu.shop.api;

import it.unibo.jetpackjoyride.menu.shop.impl.ShopView;

/**
 * An observer interface that notifies the subscribers to the observable
 * {@link ShopView} when the back button is pressed.
 * @author alessandro.valmori2@studio.unibo.it
 */

public interface BackToMenuObs {
    /**
     * The method to notify the observers.
     */
    void goBack();
}
