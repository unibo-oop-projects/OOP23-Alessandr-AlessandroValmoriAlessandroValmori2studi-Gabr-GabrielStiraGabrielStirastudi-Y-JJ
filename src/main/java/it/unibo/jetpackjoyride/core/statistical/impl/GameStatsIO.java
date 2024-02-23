package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    private GameStatsIO() {

    }

     /**
     * Gets the full file path based on the provided file name.
     *
     * @param filePath The file name.
     * @return The full file path.
     */
    public static  String getFilePath(final String filePath) {
        String directory = System.getProperty("user.home") + File.separator + "jetpackJoyride";
        return directory + File.separator + filePath;
    }

    /**
     * Saves the game statistics to a file.
     *
     * @param gameStats The game statistics to be saved.
     * @param filePath  The file path where the statistics will be saved.
     */
    public static void saveToFile(final GameStatsModel gameStats, final String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
           try {
            throw new IOException("Filed to create the directory" + parentDir);
           } catch (IOException e) {
            e.printStackTrace();
           }
        } 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write(GameStats.getCoins() + "\n");
            writer.write(gameStats.getBestDistance() + "\n");
            writer.write(gameStats.getcurrentDistance() + "\n");
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
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
            gameStats.setCoins(Integer.parseInt(reader.readLine()));
            gameStats.setBestDistance(Integer.parseInt(reader.readLine()));
            gameStats.setCurrentDistance(Integer.parseInt(reader.readLine()));
            System.out.println("Game stats loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            gameStats.setCoins(1000);
            gameStats.setBestDistance(0);
            gameStats.setCurrentDistance(0);
        }
    }
}
