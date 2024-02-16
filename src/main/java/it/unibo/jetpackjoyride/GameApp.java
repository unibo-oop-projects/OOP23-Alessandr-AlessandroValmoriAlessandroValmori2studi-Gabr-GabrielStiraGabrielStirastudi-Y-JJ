package it.unibo.jetpackjoyride;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.menus.StartMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApp extends Application {

    
    private StartMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameStatsController gameStatsHandler = new GameStatsHandler();
        gameMenu = new StartMenu(primaryStage,gameStatsHandler);
        primaryStage.setTitle("JetPack Joyride");

        primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();
    }

}
