package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import javafx.stage.Stage;

public class OpenShopCommand implements Command {
    
    private final ShopController shopController;
    private final Stage stage;

    public OpenShopCommand(final ShopController shopController,final Stage stage){
        this.shopController = shopController;
        this.stage = stage;
    }
    @Override
    public void execute() {
        stage.setScene(shopController.getScene());
    }
    
}
