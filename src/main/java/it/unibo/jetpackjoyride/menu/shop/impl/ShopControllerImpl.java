package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;

import java.util.Optional;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.GameOverMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {
    
    private final ShopView view;
    private final Stage primaryStage;
    private final GameStatsController gameStatsHandler;

    private final GameOverMenu gameMenu;

    private Optional<Items> equipped;

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopControllerImpl(final Stage primaryStage, final GameOverMenu gameMenu) {
        this.gameMenu = gameMenu;

        this.gameStatsHandler= gameMenu.getGameStatsHandler();

        this.equipped = Optional.ofNullable(this.gameStatsHandler.getGameStatsModel().getEquipped());

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
    public void buy(final Items item) {

        var available = this.gameStatsHandler.getGameStatsModel().getTotCoins();

        if(item.getItemCost() > available){
            System.out.println("Not enough funds :(\n");
        }
        else{
        this.gameStatsHandler.getGameStatsModel().updateCoins(- item.getItemCost());
        this.view.update();
        }
       System.out.println(this.gameStatsHandler.getGameStatsModel().getEquipped());
    }

    @Override
    public void equip(final Items item) {
        this.equipped = Optional.of(item);
        this.view.update();
    }

    

    @Override
    public int retrieveBalance() {
        return this.gameStatsHandler.getGameStatsModel().getTotCoins();
    }

    @Override
    public void backToMenu() {
        
         final String filename = "gameStats.data"; 

        try {
            if(this.equipped.isPresent()){
                this.gameStatsHandler.getGameStatsModel().setEquipped(this.equipped.get());
            }
            GameStats.writeToFile(gameStatsHandler.getGameStatsModel(), filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
        primaryStage.setScene(gameMenu.getScene());
    }

    @Override
    public Optional<Items> getEquipped() {
        return this.equipped;
    }

    

    
}
