package it.unibo.jetpackjoyride.save;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsIO;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the GameStats model class.
 * @author yukai.zhou@studio.unibo.it
 */
class GameStatsTest {

    private static final int DEFALUT_MOVESPEED = 5;

    private GameStatsModel gameStats;
    /**
     * Test important method to update coins.
     */
    @Test
    void testUpdateCoins() {
        gameStats = new GameStats();
        final int initialCoins = GameStats.getCoins();
        final int coinsToAdd = 100;
        GameStats.updateCoins(coinsToAdd);
        assertEquals(initialCoins + coinsToAdd, GameStats.getCoins());
    }

    /**
     * Test important method to set best distance.
     */
    @Test
    void testSetCurrentDistance() {
        gameStats = new GameStats();
        final int initialDistance = gameStats.getcurrentDistance();
        GameInfo.MOVE_SPEED.set(DEFALUT_MOVESPEED);
        gameStats.addDistance();
        assertEquals(initialDistance + DEFALUT_MOVESPEED, gameStats.getcurrentDistance());
    }

    /**
     * Test method to read and write game stats to file.
     */
    @Test
    void testReadAndWriteToFile() {
        gameStats = new GameStats();
        // Write to file
            GameStatsIO.saveToFile(gameStats, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH_TEST));
        // Read from file
            final GameStatsModel readStats = new GameStats();
            GameStatsIO.loadFromFile(readStats, GameStatsIO.getFilePath(GameStatsIO.FILE_PATH_TEST));
            assertNotNull(readStats);
            assertEquals(GameStats.getCoins(), GameStats.getCoins());
            assertEquals(gameStats.getBestDistance(), readStats.getBestDistance());

    }
}
