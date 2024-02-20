// ShieldEquippedObsImpl.java
package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShieldEquippedObs;

public class ShieldEquippedObsImpl implements ShieldEquippedObs {

    private ShopControllerImpl shopController;

    public ShieldEquippedObsImpl(ShopControllerImpl shopController) {
        this.shopController = shopController;
    }

    @Override
    public void toggleShieldEquipped() {

        shopController.toggleEquipUnequipShield();
    }
}
