package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

/**
 * A command to open the shop.
 * @author yukai.zhou@studio.unibo.it
 */
public final class OpenShopCommand implements Command {

    private final ShopController shopController;

     /**
     * Constructs a new OpenShopCommand.
     *
     * @param shopController the shop controller
     */
    public OpenShopCommand(final ShopController shopController) {
        this.shopController = shopController;
    }

    @Override
    public void execute() {
        shopController.showTheShop();
    }
}
