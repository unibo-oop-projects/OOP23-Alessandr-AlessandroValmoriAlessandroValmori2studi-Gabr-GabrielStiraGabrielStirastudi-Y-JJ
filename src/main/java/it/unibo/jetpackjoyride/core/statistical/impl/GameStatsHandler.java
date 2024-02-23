package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;


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
        model = new GameStats();
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
        view.updateDataView(List.of(model.getcurrentDistance(), model.getBestDistance(), GameStats.getCoins()));
    }

    public void saveChanged() {
            this.model.updateDate();
            GameStatsIO.saveToFile(model, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH));
    }
        
}
