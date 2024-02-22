package it.unibo.jetpackjoyride.menu.shop.impl;

import java.io.IOException;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsIO;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;
import javafx.stage.Stage;

/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {

    private final ShopView view;

    private final GameStatsController gameStatsHandler;

    private final GameMenu gameMenu;

    /**
     * Constructs a new ShopControllerImpl instance.
     *
     * @param primaryStage The primary stage of the application.
     * @param gameMenu     The game menu associated with the shop.
     */
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {

        this.gameMenu = gameMenu;

        this.gameStatsHandler = new GameStatsHandler();

        this.view = new ShopView(this, primaryStage, gameStatsHandler);

        ShopItemPurchaseObs shopItemPurchaseObs = new ShopItemPurchaseObsImpl(this, gameStatsHandler);
        BackToMenuObs backToMenuObs = new BackToMenuObsImpl(this);
        CharacterObs charObs = new CharacterImpl(this, gameStatsHandler);

        // Register observers with ShopView
        this.view.addBuyObs(shopItemPurchaseObs);
        this.view.addBackToMenuObs(backToMenuObs);
        this.view.addCharObs(charObs);
    }

    public GameStatsController getGameStatsController() {
        return this.gameStatsHandler;
    }

    /**
     * Retrieves the scene of the shop menu.
     * 
     * @return The scene of the shop menu.
     */
    @Override
    public void showTheShop() {
        this.view.setSceneOnStage();
    }



    @Override
    public void backToMenu() {
        this.save();
        gameMenu.showMenu();
    }

    
    @Override
    public void save() {

        final String filename = "gameStats.data";
        try {
            GameStatsIO.writeToFile(gameStatsHandler.getGameStatsModel(), filename);
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }

    @Override
    public void updateView() {
        this.view.update();
    }

    
}
