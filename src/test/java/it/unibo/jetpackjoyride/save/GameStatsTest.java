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
        int initialCoins = gameStats.getTotCoins();
        int coinsToAdd = 100;
        gameStats.updateCoins(coinsToAdd);
        assertEquals(initialCoins + coinsToAdd, gameStats.getTotCoins());
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
        assertEquals(initialBestDistance + GameInfo.moveSpeed.get(), gameStats.getBestDistance());
    }

    /**
     * Test method to read and write game stats to file.
     */
    @Test
    public void testReadAndWriteToFile() {
        gameStats = new GameStats();
        // Write to file
        try {
            GameStatsIO.writeToFile(gameStats, TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Read from file
        try {
            readStats = GameStatsIO.readFromFile(TEST_FILE);
            assertNotNull(readStats);
            assertEquals(gameStats.getTotCoins(), readStats.getTotCoins());
            assertEquals(gameStats.getBestDistance(), readStats.getBestDistance());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
