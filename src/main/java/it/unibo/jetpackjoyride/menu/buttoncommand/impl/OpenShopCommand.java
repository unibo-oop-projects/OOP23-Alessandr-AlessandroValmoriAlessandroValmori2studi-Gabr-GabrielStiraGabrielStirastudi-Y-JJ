package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import javafx.stage.Stage;

/**
 * A command to open the shop.
 * @author yukai.zhou@studio.unibo.it
 */
public final class OpenShopCommand implements Command {

    private final ShopController shopController;
    private final Stage stage;

     /**
     * Constructs a new OpenShopCommand.
     *
     * @param shopController the shop controller
     * @param stage          the stage
     */
    public OpenShopCommand(final ShopController shopController, final Stage stage) {
        this.shopController = shopController;
        this.stage = stage;
    }

    @Override
    public void execute() {
        stage.setScene(shopController.getScene());
    }
}
