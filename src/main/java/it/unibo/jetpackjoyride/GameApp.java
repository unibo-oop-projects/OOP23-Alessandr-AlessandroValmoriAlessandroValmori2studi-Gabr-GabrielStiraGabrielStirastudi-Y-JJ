package it.unibo.jetpackjoyride;

import it.unibo.jetpackjoyride.menu.menus.StartMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApp extends Application {

    
    private StartMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        gameMenu = new StartMenu(primaryStage);
        primaryStage.setTitle("JetPack Joyride");

        primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();
    }

}
