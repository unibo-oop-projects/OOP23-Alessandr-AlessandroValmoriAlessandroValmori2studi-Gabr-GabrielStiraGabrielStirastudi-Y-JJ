package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;

/**
 * A command to pause the game.
 * @author yukai.zhou@studio.unibo.it
 */
public class PauseCommand implements Command {

    private final GameLoop gameLoop;
    private final PauseMenu menu;
   

     /**
     * Constructs a new PauseCommand.
     *
     * @param gameLoop the game loop
     * @param menu     the pause menu
     */
    public PauseCommand(final GameLoop gameLoop,final PauseMenu menu){
        this.gameLoop = gameLoop; 
        this.menu = menu;

    }

    @Override
    public void execute() {
        this.gameLoop.stopLoop();
        menu.setVisible(true);        
    }
 
}
