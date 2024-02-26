package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;

/**
 * Utility class for saving and loading game statistics to file or from file.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameStatsIO {
    /**
     * Default file name for saving game statistics.
     */
    public static final String FILE_PATH = "gameStats.txt";
    /**
     * Default test file name for saving game statistics.
     */
    public static final String FILE_PATH_TEST = "gameStats_test.txt";

    private static final Logger LOGGER = Logger.getLogger(GameStatsIO.class.getName());


    private GameStatsIO() {

    }

     /**
     * Gets the full file path based on the provided file name.
     *
     * @param filePath The file name.
     * @return The full file path.
     */
    public static  String getFilePath(final String filePath) {
        final String directory = System.getProperty("user.home") + File.separator + "jetpackJoyride";
        return directory + File.separator + filePath;
    }

    /**
     * Saves the game statistics to a file.
     *
     * @param gameStats The game statistics to be saved.
     * @param filePath  The file path where the statistics will be saved.
     */
    public static void saveToFile(final GameStatsModel gameStats, final String filePath) {
        final File file = new File(filePath);
        final File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            LOGGER.log(Level.SEVERE, "Failed to create the directory {0}", parentDir);
        } 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write(GameStats.getCoins() + "\n");
            writer.write(gameStats.getBestDistance() + "\n");
            writer.write(gameStats.getcurrentDistance() + "\n");
            LOGGER.info("Game stats saved successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error for saving game stats", e);
        }
    }

    /**
     * Loads game statistics from a file.
     *
     * @param gameStats The game statistics model where loaded statistics will be set.
     * @param filePath  The file path from which statistics will be loaded.
     */
    public static void loadFromFile(final GameStatsModel gameStats, final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            GameStats.setCoins(Integer.parseInt(reader.readLine()));
            gameStats.setBestDistance(Integer.parseInt(reader.readLine()));
            gameStats.setCurrentDistance(Integer.parseInt(reader.readLine()));
            LOGGER.info("Game stats loaded successfully.");
        } catch (IOException e) {
            LOGGER.info("Error for reading Game stats. Using default values.");
            GameStats.setCoins(1000);
            gameStats.setBestDistance(0);
            gameStats.setCurrentDistance(0);
        }
    }
}
