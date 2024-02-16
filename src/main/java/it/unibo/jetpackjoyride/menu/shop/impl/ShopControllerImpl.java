package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.GameOverMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Collections;


/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {
    
    private final ShopView view;
    private final Stage primaryStage;
    private final GameStatsController gameStatsHandler;

    private final GameOverMenu gameMenu;

    private int numOfShields;
    private boolean isShieldEquipped;
    private Set<Items> unlockedItems;

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopControllerImpl(final Stage primaryStage, final GameOverMenu gameMenu) {
        this.gameMenu = gameMenu;

        this.gameStatsHandler= gameMenu.getGameStatsHandler();

        this.isShieldEquipped= this.gameStatsHandler.getGameStatsModel().isShieldEquipped();

        this.numOfShields = this.gameStatsHandler.getGameStatsModel().getNumOfShields();

        this.unlockedItems = new HashSet<>(this.gameStatsHandler.getGameStatsModel().getUnlocked());

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
        if(item.equals(Items.SHIELD)){
            this.numOfShields++;
        }
        else{
            this.unlockedItems.add(item);
            System.out.println(this.unlockedItems);
        }
        this.view.update();
        }
     
    }

    

    @Override
    public int retrieveBalance() {
        return this.gameStatsHandler.getGameStatsModel().getTotCoins();
    }

    @Override
    public void backToMenu() {
        
         final String filename = "gameStats.data"; 

        try {
            this.gameStatsHandler.getGameStatsModel().addShields(this.numOfShields);
            this.gameStatsHandler.getGameStatsModel().setShield(this.isShieldEquipped);
            this.gameStatsHandler.getGameStatsModel().unlock(this.unlockedItems);
            GameStats.writeToFile(gameStatsHandler.getGameStatsModel(), filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
        primaryStage.setScene(gameMenu.getScene());
    }

    @Override
    public void toggleEquipUnequipShield() {
        this.isShieldEquipped = !this.isShieldEquipped;
    }

    @Override
    public boolean isShieldEquipped() {
        return this.isShieldEquipped;
    }

    @Override
    public int getNumOfShields() {
       return this.numOfShields;
    }

    @Override
    public Set<Items> getUnlocked() {
        return this.unlockedItems;
    }

    

   

    

    
}
