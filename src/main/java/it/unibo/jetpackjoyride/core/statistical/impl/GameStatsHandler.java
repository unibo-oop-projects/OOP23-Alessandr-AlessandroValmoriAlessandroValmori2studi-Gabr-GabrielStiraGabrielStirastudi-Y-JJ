package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;


/**
 * A class implementing the GameStatsController interface.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameStatsHandler implements GameStatsController {

    private GameStatsModel model;
    private GameStatsView view;

    /**
     * Constructs a new GameStatsHandler.
     */
    public GameStatsHandler() {
        System.out.println("GAME STATS CREATED");
        loadDateFromFile();
    }

    @Override
    public void getGameStatsView(GameStatsView view) {
        this.view = view;
    }

    @Override
    public void updateCurrentDistance() {
        model.addDistance();
    }
    @Override
    public void updateView() {
        view.updateDataView(List.of(model.getcurrentDistance(), model.getBestDistance(), GameStats.COINS.get()));
    }
     /**
     * A method use to load the last statistical.
     */
    private void loadDateFromFile() {
        try {
            this.model = GameStatsIO.readFromFile("gameStats.data"); 
            System.out.println("Game stats loaded successfully.");
            System.out.println(GameStats.COINS.get());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game stats: " + e.getMessage());
            this.model = new GameStats();
        }
    }

    public void saveChanged() {
        final String filename = "gameStats.data"; 

        try {
            this.model.updateDate();
            GameStatsIO.writeToFile(this.model, filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }
}
