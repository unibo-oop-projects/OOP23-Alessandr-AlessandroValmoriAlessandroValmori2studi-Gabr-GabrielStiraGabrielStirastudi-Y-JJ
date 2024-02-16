package it.unibo.jetpackjoyride.menu.buttoncommand.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.application.Platform;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class PauseCommand implements Command {

    private final GameLoop gameLoop;
    private final Stage stage;
    private final PauseMenu menu;
    private WritableImage writableImage;
    private GameInfo gameInfo = GameInfo.getInstance();
   

    public PauseCommand(final GameLoop gameLoop,final Stage stage,final PauseMenu menu){
        this.gameLoop = gameLoop; 
        this.stage = stage;
        this.menu = menu;

    }
    @SuppressWarnings("unused")
    @Override
    public void execute() {
        this.gameLoop.stopLoop();
        if(true){
            gameLoop.showGameOverMenu();
        }else{
            menu.setVisbile(true);
        }
   
    }
 
}
