package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.GameLoopControl;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;

/**
 * A command to pause the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class PauseCommand implements Command {

    private final GameLoopControl gameLoop;
    private final GameMenu menu;


     /**
     * Constructs a new PauseCommand.
     *
     * @param gameLoop the game loop
     * @param menu     the pause menu
     */
    public PauseCommand(final GameLoopControl gameLoop, final GameMenu menu) {
        this.gameLoop = gameLoop; 
        this.menu = menu;
    }

    @Override
    public void execute() {
        this.gameLoop.stopLoop();
        menu.setVisible(true); 
    }
}
