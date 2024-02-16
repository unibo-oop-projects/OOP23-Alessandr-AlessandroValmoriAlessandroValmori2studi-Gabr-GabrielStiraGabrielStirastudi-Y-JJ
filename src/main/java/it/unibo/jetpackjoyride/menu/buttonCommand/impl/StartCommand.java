package it.unibo.jetpackjoyride.menu.buttonCommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.BaseMenu;
import it.unibo.jetpackjoyride.menu.buttonCommand.api.Command;
import javafx.stage.Stage;

public class StartCommand implements Command {

    private final GameLoop gameLoop;
    private final Stage stage;
    private final BaseMenu menu;
   

    public StartCommand(final GameLoop gameLoop,final Stage stage,final BaseMenu menu){
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
