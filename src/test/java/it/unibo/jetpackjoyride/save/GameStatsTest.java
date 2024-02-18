package it.unibo.jetpackjoyride.save;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.utilities.GameInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameStatsTest {

    private GameStatsModel gameStats;
    private static final String TEST_FILE = "game_stats_test.data";

    @BeforeEach
    public void setUp() {
        gameStats = new GameStats();
    }

    @Test
    public void testUpdateCoins() {
        int initialCoins = gameStats.getTotCoins();
        int coinsToAdd = 100;
        gameStats.updateCoins(coinsToAdd);
        assertEquals(initialCoins + coinsToAdd, gameStats.getTotCoins());
    }

    @Test
    public void testSetBestDistance() {
        int initialBestDistance = gameStats.getBestDistance();
        gameStats.addDistance();
        gameStats.updateDate();
        assertEquals(initialBestDistance+GameInfo.moveSpeed.get(), gameStats.getBestDistance());
    }


    @Test
    public void testReadAndWriteToFile() {
        // Write to file
        try {
            GameStats.writeToFile(gameStats, TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Read from file
        try {
            GameStatsModel readStats = GameStats.readFromFile(TEST_FILE);
            assertNotNull(readStats);
            assertEquals(gameStats.getTotCoins(), readStats.getTotCoins());
            assertEquals(gameStats.getBestDistance(), readStats.getBestDistance());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
