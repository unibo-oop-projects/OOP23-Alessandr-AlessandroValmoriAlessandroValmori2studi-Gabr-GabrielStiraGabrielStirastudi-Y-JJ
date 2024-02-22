package it.unibo.jetpackjoyride.menu.shop.impl;


import java.io.IOException;
import java.util.Set;

import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
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
    private ShopModel model;

    private final GameMenu gameMenu;

    /**
     * Constructs a new ShopControllerImpl instance.
     *
     * @param primaryStage The primary stage of the application.
     * @param gameMenu     The game menu associated with the shop.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public ShopControllerImpl(final Stage primaryStage, final GameMenu gameMenu) {

        this.gameMenu = gameMenu;
        loadDateFromFile();
        this.view = new ShopView(this, primaryStage, model);

        ShopItemPurchaseObs shopItemPurchaseObs = new ShopItemPurchaseObsImpl(this, model);
        BackToMenuObs backToMenuObs = new BackToMenuObsImpl(this);
        CharacterObs charObs = new CharacterImpl(this, model);

        // Register observers with ShopView
        this.view.addBuyObs(shopItemPurchaseObs);
        this.view.addBackToMenuObs(backToMenuObs);
        this.view.addCharObs(charObs);
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

    private void loadDateFromFile() {
        try {
            this.model = GameStatsIO.readShopFromFile("shop.data"); 
            System.out.println("Game Shop loaded successfully.");
            System.out.println(GameStats.COINS.get());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game stats: " + e.getMessage());
            this.model = new ShopModel();
        }
    }

    
    @Override
    public void save() {
          final String filename = "shop.data"; 

        try {
            GameStatsIO.writeToFile(this.model, filename); 
            System.out.println("Game Shop saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }

    @Override
    public void updateView() {
        this.view.update(this.model);
    }

    @Override
    public ShopModel getShopModel() {
         return this.model;
    }

    @Override
    public Set<Items> getUnlocked() {
         return this.model.getUnlocked();
    }

    @Override
    public void unlock(Items item) {
        this.model.unlock(item);
    }
 
}
