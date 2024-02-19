package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl  implements ShopController {

    private final ShopView view;
    private final Stage primaryStage;
    private final GameStatsController gameStatsHandler;

    private final GameMenu gameMenu;

    private int numOfShields;
    private boolean isShieldEquipped;
    private final Set<Items> unlockedItems;
    private final Deque<String> characters;

    private final String pw = "TRUFFLEWORM";

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {
        
        this.characters = new LinkedList<>();
        this.gameMenu = gameMenu;

        this.gameStatsHandler = gameMenu.getGameStatsHandler();

        this.isShieldEquipped = this.gameStatsHandler.getGameStatsModel().isShieldEquipped();

        this.numOfShields = this.gameStatsHandler.getGameStatsModel().getNumOfShields();

        this.unlockedItems = new HashSet<>(this.gameStatsHandler.getGameStatsModel().getUnlocked());

        this.primaryStage = primaryStage;

        this.view = new ShopView(this, primaryStage, gameMenu.getGameStatsHandler());
    }

    /**
     * Retrieves the scene of the shop menu.
     * 
     * @return The scene of the shop menu.
     */
    @Override
    public Scene getScene() {
        return this.view.getScene();
    }

    @Override
    public void buy(final Items item) {

        var available = this.gameStatsHandler.getGameStatsModel().getTotCoins();

        if (item.equals(Items.SHIELD)) {
            if (item.getItemCost() > available) {
                System.out.println("Not enough funds :(\n");
            } else {
                this.numOfShields++;
                this.gameStatsHandler.getGameStatsModel().updateCoins(-item.getItemCost());
            }
        } else {

            if (!this.unlockedItems.contains(item)) {
                if (item.getItemCost() > available) {
                    System.out.println("Not enough funds :(\n");
                } else {
                    this.unlockedItems.add(item);
                    this.gameStatsHandler.getGameStatsModel().updateCoins(-item.getItemCost());
                }
            }

        }

        this.view.update();

    }

    @Override
    public int retrieveBalance() {
        return this.gameStatsHandler.getGameStatsModel().getTotCoins();
    }

    @Override
    public void backToMenu() {
        this.save();
        primaryStage.setScene(gameMenu.getScene());
    }

    @Override
    public void toggleEquipUnequipShield() {
        this.isShieldEquipped = !this.isShieldEquipped;
        if (this.isShieldEquipped) {
            System.out.println("Shop: shield is equipped");
        } else {
            System.out.println("Shop: shield is NOT equipped");
        }
        this.view.update();
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

    @Override
    public void type(KeyCode code) {
        if (!this.unlockedItems.contains(Items.DUKE)) {
            StringBuilder sb = new StringBuilder();
           
            characters.addLast(code.getChar());
            if (this.characters.size() == 12) {
                this.characters.removeFirst();
            }
            if (this.characters.size() == 11) {

                for (var ch : this.characters) {
                    sb.append(ch);
                }
                String concatenatedString = sb.toString();
                if (concatenatedString.equals(pw)) {
                    System.out.println("DUKE Unlocked");
                    this.unlockedItems.add(Items.DUKE);
                }
            }
        }
    }

    @Override
    public void save() {
        this.gameStatsHandler.getGameStatsModel().addShields(this.numOfShields);
            this.gameStatsHandler.getGameStatsModel().setShield(this.isShieldEquipped);
            this.gameStatsHandler.getGameStatsModel().unlock(this.unlockedItems);


        final String filename = "gameStats.data";

        try {
            
            GameStats.writeToFile(gameStatsHandler.getGameStatsModel(), filename);
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }

   
}
