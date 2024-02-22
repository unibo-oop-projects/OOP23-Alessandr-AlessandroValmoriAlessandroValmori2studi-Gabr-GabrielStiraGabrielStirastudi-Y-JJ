package it.unibo.jetpackjoyride.save;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsIO;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the GameStats model class.
 * @author yukai.zhou@studio.unibo.it
 */
public class GameStatsTest {

    private GameStatsModel gameStats;
    private GameStatsModel readStats;
    private static final String TEST_FILE = "game_stats_test.data";

    /**
     * Test important method to update coins.
     */
    @Test
    public void testUpdateCoins() {
        gameStats = new GameStats();
        int initialCoins = GameStats.getCoins();
        int coinsToAdd = 100;
        GameStats.updateCoins(coinsToAdd);
        assertEquals(initialCoins + coinsToAdd,GameStats.getCoins());
    }

    /**
     * Test important method to set best distance.
     */
    @Test
    public void testSetBestDistance() {
        gameStats = new GameStats();
        int initialBestDistance = gameStats.getBestDistance();
        gameStats.addDistance();
        gameStats.updateDate();
        assertEquals(initialBestDistance + GameInfo.MOVE_SPEED.get(), gameStats.getBestDistance());
    }

}
