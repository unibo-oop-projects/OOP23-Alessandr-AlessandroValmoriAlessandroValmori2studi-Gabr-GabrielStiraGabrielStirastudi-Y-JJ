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


/**
 * Class representing the  Pause menu, extend from the GameMenu
 * @author yukai.zhou@studio.unibo.it
 */
public class PauseMenu extends GameMenu {

    private final VBox buttonsVBox;
    private final GameLoop gameLoop;
    private Button pauseButton;


    /**
     * Constructs a new PauseMenu.
     * And it call back the constructor of the superclass
     * @param primaryStage the primary stage
     * @param gameLoop     the game loop
     */
    public PauseMenu(final Stage primaryStage,final GameLoop gameLoop) {
        super(primaryStage,null);
        this.gameLoop = gameLoop;
        buttonsVBox = new VBox();
        initializeGameMenu();
        
    }

    @Override
    protected void initializeGameMenu(){
        buttonsVBox.setPrefWidth(gameInfo.getScreenWidth());
        buttonsVBox.setPrefHeight(gameInfo.getScreenHeight());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(20);
        buttonsVBox.setStyle("-fx-background-color: rgb(0, 0, 0);");
        Command continueCommand = new StartCommand(gameLoop, stage,this);
        Button restartButton = ButtonFactory.createButton("back",
        e->{continueCommand.execute();this.buttonsVBox.setVisible(false);},220,120);
        
        Command pauseCommand = new PauseCommand(gameLoop,this);
        pauseButton = ButtonFactory.createButton("Pause",e->pauseCommand.execute(),50,50);
        pauseButton.setLayoutX(gameInfo.getScreenWidth()-70);
        pauseButton.setLayoutY(0);
        
        buttonsVBox.getChildren().addAll(restartButton);
        buttonsVBox.setVisible(false);
    }
   
   /**
     * Gets the pause button.
     *
     * @return the pause button
     */
    public Button getPauseButton() {
        return pauseButton;
    }

    /**
     * Gets the VBox.
     *
     * @return the VBox
     */
    public VBox getVBox() {
        return this.buttonsVBox;
    }

    /**
     * Sets the visibility of the menu.
     *
     * @param isVisible whether the menu is visible
     */
    public void setVisible(final boolean isVisible) {
        this.buttonsVBox.setVisible(isVisible);
    }

}
