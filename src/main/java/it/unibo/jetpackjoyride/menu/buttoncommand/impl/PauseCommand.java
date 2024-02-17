package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;

public class PauseCommand implements Command {

    private final GameLoop gameLoop;
    private final PauseMenu menu;
   

    public PauseCommand(final GameLoop gameLoop,final PauseMenu menu){
        this.gameLoop = gameLoop; 
        this.menu = menu;

    }

    @Override
    public void execute() {
        this.gameLoop.stopLoop();
            gameLoop.showGameOverMenu();
    }
 
}
