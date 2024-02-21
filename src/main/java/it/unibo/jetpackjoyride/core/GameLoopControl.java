package it.unibo.jetpackjoyride.core;

/**
 * A interface use to control the game loop.
 * @author yukai.zhou@studio.unibo.it
 */
public interface GameLoopControl {
      /**
     * Starts the game loop.
     */
      void startLoop();

    /**
     * Stop the game loop.
     */
     void stopLoop();

    /**
     * End the game loop.
     */
     void endLoop();

    /**
     * Reset the game loop.
     */
     void resetLoop();
}
