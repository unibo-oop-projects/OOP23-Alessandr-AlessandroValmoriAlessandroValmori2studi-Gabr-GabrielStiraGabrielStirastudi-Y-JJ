package it.unibo.jetpackjoyride.menu.menus;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.StartCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.PauseCommand;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PauseMenu extends GameMenu {

    private  VBox buttonsVBox;
    private final GameLoop gameLoop;
    private Button pauseButton;


    public PauseMenu(final Stage primaryStage,final GameLoop gameLoop) {
        super(primaryStage,null);
        this.gameLoop = gameLoop;
        initializeGameMenu();
        
    }

    @Override
    protected void initializeGameMenu(){
        buttonsVBox = new VBox();
        buttonsVBox.setPrefWidth(gameInfo.getScreenWidth());
        buttonsVBox.setPrefHeight(gameInfo.getScreenHeight());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(20);
        buttonsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        Command continueCommand = new StartCommand(gameLoop, stage,this);
        Button restartButton = ButtonFactory.createButton("back",
        e->{continueCommand.execute();this.buttonsVBox.setVisible(false);},220,120);
        
        Command pauseCommand = new PauseCommand(gameLoop,this);
        pauseButton = ButtonFactory.createButton("Pause",e->pauseCommand.execute(),50,50);
        pauseButton.setLayoutX(gameInfo.getScreenWidth()-70);
        pauseButton.setLayoutY(0);
        pauseButton.setFocusTraversable(false);
        
        buttonsVBox.getChildren().addAll(restartButton);
        buttonsVBox.setVisible(false);
    }
   
    public Button getPauseButton(){
        return pauseButton;
    }

    public VBox getVBox(){
        return this.buttonsVBox;
    }

    public void setVisbile(boolean isvisible){
        this.buttonsVBox.setVisible(isvisible);
    }
}
