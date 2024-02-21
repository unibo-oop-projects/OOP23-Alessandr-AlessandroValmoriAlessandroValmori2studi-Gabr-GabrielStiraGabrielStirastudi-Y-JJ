package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoopControl;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;

/**
 * A command to pause the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class PauseCommand implements Command {

    private final GameLoopControl gameLoop;


     /**
     * Constructs a new PauseCommand.
     *
     * @param gameLoop the game loop
     */
    public PauseCommand(final GameLoopControl gameLoop) {
        this.gameLoop = gameLoop; 
    }

    @Override
    public void execute() {
        this.gameLoop.stopLoop();
    }
}
