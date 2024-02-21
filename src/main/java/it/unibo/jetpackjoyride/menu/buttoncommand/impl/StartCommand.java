package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoopControl;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;

/**
 * A command to start the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class StartCommand implements Command {

    private final GameLoopControl gameLoop;

    /**
     * Constructs a new StartCommand.
     *
     * @param gameLoop the game loop
     */
    public StartCommand(final GameLoopControl gameLoop) {
        this.gameLoop = gameLoop;
    }

    @Override
    public void execute() {
        gameLoop.startLoop();
    }
}
