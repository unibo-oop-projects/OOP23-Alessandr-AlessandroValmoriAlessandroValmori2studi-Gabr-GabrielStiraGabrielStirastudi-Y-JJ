package it.unibo.jetpackjoyride.menu.buttonCommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttonCommand.api.Command;
import javafx.stage.Stage;

public class StartCommand implements Command {

    private final GameLoop gameLoop;
    private final Stage stage;

    public StartCommand(final GameLoop gameLoop,final Stage stage){
        this.gameLoop = gameLoop;
        this.stage = stage;
    }
    @Override
    public void execute() {
        stage.setScene(gameLoop.getScene());
        gameLoop.startLoop();
    }
    
}
