// ShieldEquippedObsImpl.java
package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShieldEquippedObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

public class ShieldEquippedObsImpl implements ShieldEquippedObs {

    private ShopController shopController;

    public ShieldEquippedObsImpl(ShopController shopController) {
        this.shopController = shopController;
    }

    @Override
    public void toggleShieldEquipped() {

        shopController.toggleEquipUnequipShield();
        shopController.updateView();
    }
}
