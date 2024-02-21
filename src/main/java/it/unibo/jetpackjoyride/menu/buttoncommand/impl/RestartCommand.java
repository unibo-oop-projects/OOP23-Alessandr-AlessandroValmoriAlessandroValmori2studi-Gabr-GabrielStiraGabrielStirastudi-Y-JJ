package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoopControl;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.api.GameMenu;
import javafx.stage.Stage;

/**
 * A command to restart the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class RestartCommand implements Command {

    private final GameLoopControl gameLoop;
    private final Stage stage;
    private final GameMenu menu;

    /**
     * Constructs a new RestartCommand.
     *
     * @param gameLoop the game loop
     * @param stage    the stage
     * @param menu     the game menu
     */
    public RestartCommand(final GameLoopControl gameLoop, final Stage stage, final GameMenu menu) {
        this.gameLoop = gameLoop;
        this.stage = stage;
        this.menu = menu;
    }

    @Override
    public void execute() {
        menu.removeListener();
        gameLoop.resetLoop();
        gameLoop.startLoop();
        stage.centerOnScreen();
    }
}
