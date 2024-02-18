package it.unibo.jetpackjoyride;

import javafx.application.Application;

/**
 * The Game class serves as the entry point for launching the Jetpack Joyride game application.
 * It contains the main method that launches the JavaFX application.
 */
public final class Game {

    
    private Game() {
    }

    /**
     * Main method to launch the Jetpack Joyride Game application.
     */
    public static void main(final String[] args) {
        Application.launch(GameApp.class, args);
    }
}


