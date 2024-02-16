package it.unibo.jetpackjoyride.menu.menus;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.StartCommand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public final class StartMenu extends GameMenu{

    private GameLoop gameLoop;

    public StartMenu(Stage primaryStage) {
        super(primaryStage);
        Image menuImage = new Image(getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm());
        setMenuImage(menuImage);
        initializeGameMenu();
        setGameStagePosition();
    }

    @Override
    protected void initializeGameMenu(){
        this.gameLoop = new GameLoop(this.stage);
        VBox buttonsRoot = new VBox(20);
        buttonsRoot.setAlignment(Pos.CENTER);

        Command startCommand = new StartCommand(this.gameLoop, stage,this);
        Button startButton = ButtonFactory.createButton("PlayGame",e->startCommand.execute(),150,50);

        buttonsRoot.getChildren().addAll(startButton);

        StackPane.setMargin(buttonsRoot, new Insets(this.scene.getHeight()/2, 0, 0, 0));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            StackPane.setMargin(buttonsRoot, new Insets(newVal.doubleValue()/2, 0, 0, 0));
        });
        
        addButtons(buttonsRoot);
    }

    @Override
    protected void extendCloseAction(){
        this.gameLoop.endLoop();
    }
   
}
