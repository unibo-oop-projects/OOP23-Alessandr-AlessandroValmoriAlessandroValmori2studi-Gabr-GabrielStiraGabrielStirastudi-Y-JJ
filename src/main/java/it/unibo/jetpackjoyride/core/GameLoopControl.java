package it.unibo.jetpackjoyride.core;

public interface GameLoopControl {
      /**
     * Starts the game loop.
     */
    public void startLoop();

    /**
     * Stop the game loop.
     */
    public void stopLoop();

    /**
     * End the game loop.
     */
    public void endLoop();

    /**
     * Reset the game loop.
     */
     void resetLoop();
}
