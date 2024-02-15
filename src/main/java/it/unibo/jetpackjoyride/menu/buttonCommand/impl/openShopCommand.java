package it.unibo.jetpackjoyride.menu.buttonCommand.impl;

import it.unibo.jetpackjoyride.menu.buttonCommand.api.Command;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import javafx.stage.Stage;

public class openShopCommand implements Command {
    
    private final ShopController shopController;
    private final Stage stage;

    public openShopCommand(final ShopController shopController,final Stage stage){
        this.shopController = shopController;
        this.stage = stage;
    }
    @Override
    public void execute() {
        stage.setScene(shopController.getScene());
    }
    
}
