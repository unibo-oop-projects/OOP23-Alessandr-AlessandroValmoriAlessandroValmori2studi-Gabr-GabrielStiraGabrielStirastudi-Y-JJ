package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;

/**
 * A class implementing the GameStatsController interface.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameStatsHandler implements GameStatsController {

    private GameStatsModel model;

    /**
     * Constructs a new GameStatsHandler.
     */
    public GameStatsHandler() {
        model = new GameStats();
    }

    @Override
    public void updateCurrentDistance() {
        model.addDistance();
    }
    @Override
    public List<Integer> dataForView() {
        return List.of(model.getcurrentDistance(), model.getBestDistance(), GameStats.getCoins());
    }

    @Override
    public void saveChanged() {
            this.model.updateDate();
            GameStatsIO.saveToFile(model, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH));
    }
}
