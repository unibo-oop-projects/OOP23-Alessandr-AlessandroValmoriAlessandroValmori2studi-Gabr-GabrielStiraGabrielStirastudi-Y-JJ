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
        view.updateDataView(List.of(model.getcurrentDistance(), model.getBestDistance(), GameStats.getCoins()));
    }

    public void saveChanged() {
            this.model.updateDate();
            this.model.saveToFile(); 
    }
        
}
