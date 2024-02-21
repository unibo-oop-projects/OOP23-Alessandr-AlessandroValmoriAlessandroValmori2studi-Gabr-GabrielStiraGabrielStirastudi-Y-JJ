package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoopControl;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;

/**
 * A command to restart the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class RestartCommand implements Command {

    private final GameLoopControl gameLoop;

    /**
     * Constructs a new RestartCommand.
     *
     * @param gameLoop the game loop
     */
    public RestartCommand(final GameLoopControl gameLoop) {
        this.gameLoop = gameLoop;
    }

    @Override
    public void execute() {
        gameLoop.resetLoop();
        gameLoop.startLoop();
    }
}
