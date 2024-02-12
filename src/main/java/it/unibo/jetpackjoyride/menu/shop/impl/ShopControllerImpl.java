package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {
    
    private ShopView view;
    private Stage primaryStage;
    private GameStatsHandler statshandler;

    private GameMenu gameMenu;

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopControllerImpl(Stage primaryStage, GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        this.primaryStage = primaryStage;
        
        this.view = new ShopView(this);
    }

    /**
     * Retrieves the scene of the shop menu.
     * @return The scene of the shop menu.
     */
    @Override
    public Scene getScene() {
        return this.view.getScene();
    }

    @Override
    public void backToMenu() {
        primaryStage.setScene(gameMenu.getScene());
    }

    @Override
    public void buy(Items item) {
       System.out.println(item.toString() +" bought");
    }

    @Override
    public void equip(Items item) {
        System.out.println(item.toString() +" equipped");
    }

    @Override
    public int retrieveBalance() {
        return 2;
    }

    

    
}
