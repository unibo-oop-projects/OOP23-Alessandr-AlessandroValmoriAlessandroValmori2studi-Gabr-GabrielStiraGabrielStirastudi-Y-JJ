package it.unibo.jetpackjoyride;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.GameMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApp extends Application {

    private GameLoop gameLoop;
    private GameMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameLoop = new GameLoop();
        gameMenu = new GameMenu(primaryStage, gameLoop);
        primaryStage.setTitle("JetPack Joyride");

        primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();

        gameLoop.starLoop();
    }


}
