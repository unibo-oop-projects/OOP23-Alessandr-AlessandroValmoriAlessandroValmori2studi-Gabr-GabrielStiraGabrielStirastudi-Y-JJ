package it.unibo.jetpackjoyride.core.statistical.impl;

import java.util.concurrent.atomic.AtomicInteger;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.utilities.GameInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public final class GameStats implements GameStatsModel {
    private static final String FILE_PATH = "gameStats.txt";
    private static AtomicInteger coins = new AtomicInteger();

    private int bestDistance;
    private int currentDistance; 

    public GameStats() {
        loadFromFile();
    }

    public synchronized void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(coins.get() + "\n"); 
            writer.write(bestDistance + "\n"); 
            writer.write(currentDistance + "\n");
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            coins.set(Integer.parseInt(reader.readLine()));
            bestDistance = Integer.parseInt(reader.readLine());
            currentDistance = Integer.parseInt(reader.readLine());
            System.out.println("GAME STATS CREATED");
        } catch (IOException e) {
            e.printStackTrace();
            coins.set(1000);
            bestDistance = 0;
            currentDistance = 0;
        }
    }

    public static void updateCoins(int num) {
        coins.getAndUpdate(value -> Math.max(value + num, 0));
    }

    public static int getCoins() {
        return coins.get();
    }
    @Override
    public int getBestDistance() {
        return bestDistance;
    }

    private void setBestDistance() {
        if (currentDistance > bestDistance) {
             bestDistance = currentDistance;
        }
    }

    @Override
    public void addDistance() {
        this.currentDistance = currentDistance + GameInfo.MOVE_SPEED.get();
    }

    @Override
    public void updateDate() {
        this.setBestDistance();
        this.currentDistance = 0;
    }

    @Override
    public int getcurrentDistance() {
        return this.currentDistance;
    }
}
