package it.unibo.jetpackjoyride;

import it.unibo.jetpackjoyride.menu.GameMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApp extends Application {

    
    private GameMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        gameMenu = new GameMenu(primaryStage);
        primaryStage.setTitle("JetPack Joyride");

        primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();
    }

}
