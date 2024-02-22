package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;

public class GameStatsIO {
    public static final String FILE_PATH = "files/gameStats.txt";
    public static final String FILE_PATH_TEST = "files/gameStats_test.txt";

    public static void saveToFile(GameStatsModel gameStats, final String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(GameStats.getCoins() + "\n");
            writer.write(gameStats.getBestDistance() + "\n");
            writer.write(gameStats.getcurrentDistance() + "\n");
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFile(GameStatsModel gameStats, final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
