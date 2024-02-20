package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;

/**
 * A class that provide the salve and load method.
 * @author yukai.zhou@studio.unibo.it
 */
public final class GameStatsIO {

    private GameStatsIO() {

    }

    /**
     * Save the game statistics to a file.
     *
     * @param stats    the game statistics to save
     * @param filename the name of the file
     * @throws IOException if an I/O error occurs
     */
    public static void writeToFile(final GameStatsModel stats, final String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(stats);
        }
    }

    /**
     * Loads the game statistics from a file.
     *
     * @param filename the name of the file
     * @return the game statistics load from the file
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public static GameStatsModel readFromFile(final String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameStatsModel) in.readObject();
        }
    }
}
