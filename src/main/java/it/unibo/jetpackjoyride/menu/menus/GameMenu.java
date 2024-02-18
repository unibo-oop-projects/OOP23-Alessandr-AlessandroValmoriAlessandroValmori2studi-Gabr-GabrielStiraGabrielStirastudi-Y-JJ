package it.unibo.jetpackjoyride.menu.menus;


import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Abstract class representing a basic game menu extensible.
 * @author yukai.zhou@studio.unibo.it
 */
public abstract class GameMenu {
    private static final int PORTION = 4;

    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> heightListener;
    
    protected Scene scene;
    protected Stage stage;
    protected StackPane root;
    protected ImageView menuImageView;     
    protected GameInfo gameInfo = GameInfo.getInstance();
    private GameStatsController gameStatsController;

   /**
     * Constructs a new game menu.
     *
     * @param primaryStage      the primary stage
     * @param gameStatsController the game statistics controller
     */
    public GameMenu(final Stage primaryStage,final GameStatsController gameStatsController) {
         this.stage = primaryStage;
         this.root = new StackPane();
         this.scene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
         this.gameStatsController = gameStatsController;
    }

    /**
     * A method to get the scene of this game menu.
     *
     * @return the scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Removes the listeners attached to the scene.
     */
    public void removeListener(){
        if(scene != null && widthListener != null){
            scene.widthProperty().removeListener(widthListener);
        }
        if(scene != null && heightListener != null){
            scene.heightProperty().removeListener(heightListener);
        }
    }

    /**
     * A method to get the game statistics controller.
     *
     * @return the game statistics controller
     */
    public GameStatsController getGameStatsHandler(){
        return this.gameStatsController;
    }

     /**
     * here can initializes specify funtion of the  menu.
     */
    protected abstract void initializeGameMenu();

    /**
     * Adds buttons to the game menu.
     *
     * @param buttons the buttons to add
     */
    protected void addButtons(Node buttons) {
        this.root.getChildren().add(buttons);
    }

    /**
     * Sets the image for menu.
     *
     * @param menuImage the menu image
     */
    protected void setMenuImage(Image menuImage){
        if(menuImageView != null){
            this.root.getChildren().remove(menuImageView);
        }
        menuImageView = new ImageView(menuImage);
        menuImageView.setFitWidth(gameInfo.getScreenWidth());
        menuImageView.setFitHeight(gameInfo.getScreenHeight());
        addSizeListener();
        root.getChildren().add(0, menuImageView);
    }

    /**
     * The method can define the action to perform when the stage is closed.
     */
    protected void stageCloseAction(){
        stage.setOnCloseRequest(event -> {
            defaultCloseAction();
        });
    }

    /**
     *  The default action when the stage is closed.
     */
    protected void defaultCloseAction(){
        Platform.exit(); 
        System.exit(0);
    }
       
    /**
     * Sets the position of the game stage.
     */
    protected void setGameStagePosition() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double sw = screenSize.getWidth();
        final double sh = screenSize.getHeight();
        final double mapw = gameInfo.getScreenWidth();
        final double maph = gameInfo.getScreenHeight();

        final double stageX = (sw - mapw) / (PORTION / 2);
        final double stageY = (sh - maph) / PORTION;

        this.stage.setX(stageX);
        this.stage.setY(stageY);
    }

    /**
     * adds the listeners to the scene.
     */
    private void addSizeListener(){
        widthListener = (obs,oldvalue,newVal) ->{
            double ratioX = newVal.doubleValue()/oldvalue.doubleValue();
            menuImageView.setFitWidth(menuImageView.getFitWidth()*ratioX);
        };
        scene.widthProperty().addListener(widthListener);
        heightListener = (obs,oldvalue,newVal) ->{
            double ratioY = newVal.doubleValue()/oldvalue.doubleValue();
            menuImageView.setFitHeight(menuImageView.getFitHeight()*ratioY);
        };
        scene.heightProperty().addListener(heightListener);
    }
}
