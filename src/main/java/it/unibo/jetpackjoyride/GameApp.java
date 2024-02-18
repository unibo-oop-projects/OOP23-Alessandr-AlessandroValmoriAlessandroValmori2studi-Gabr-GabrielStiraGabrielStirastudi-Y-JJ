package it.unibo.jetpackjoyride;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.menus.StartMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The GameApp class is the main class of the Jetpack Joyride game application.
 * It extends the JavaFX Application class and initializes the game by creating a start menu.
 */
public class GameApp extends Application {

    
    private StartMenu gameMenu;

    /**
     * Start method to start the Jetpack Joyride Game application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameStatsController gameStatsHandler = new GameStatsHandler();
        gameMenu = new StartMenu(primaryStage,gameStatsHandler);
        primaryStage.setTitle("JetPack Joyride");

        primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();
    }

}
