package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;

/**
 * A command to start the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class StartCommand implements Command {

    private final GameLoop gameLoop;
    private final GameMenu menu;

    /**
     * Constructs a new StartCommand.
     *
     * @param gameLoop the game loop
     * @param stage    the stage
     * @param menu     the game menu
     */
    public StartCommand(final GameLoop gameLoop, final GameMenu menu) {
        this.gameLoop = gameLoop;
        this.menu = menu;
    }

    @Override
    public void execute() {
        menu.removeListener();
        gameLoop.startLoop();
    }
}
