package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;
import javafx.stage.Stage;

/**
 * A command to start the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class StartCommand implements Command {

    private final GameLoop gameLoop;
    private final Stage stage;
    private final GameMenu menu;

    /**
     * Constructs a new StartCommand.
     *
     * @param gameLoop the game loop
     * @param stage    the stage
     * @param menu     the game menu
     */
    public StartCommand(final GameLoop gameLoop, final Stage stage, final GameMenu menu) {
        this.gameLoop = gameLoop;
        this.stage = stage;
        this.menu = menu;
    }

    @Override
    public void execute() {
        stage.setScene(gameLoop.getScene());
        menu.removeListener();
        gameLoop.startLoop();
    }
}
