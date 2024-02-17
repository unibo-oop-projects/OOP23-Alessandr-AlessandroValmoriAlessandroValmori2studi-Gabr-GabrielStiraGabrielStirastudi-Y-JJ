package it.unibo.jetpackjoyride.core;

import java.io.IOException;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.menus.OverMenu;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class GameLoop {

    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;

    private GameStatsController gameStatsHandler;
    private PauseMenu pauseMenu;


    private EntityHandler entityHandler;

    private static final int fps = 70;
    private final long nSecPerFrame = Math.round(1.0 / fps * 1e9);

    private Stage stage;
    private Pane root;
    private Group entityGroup;

    private final InputHandler inputH = new InputHandler();

    public GameLoop(Stage stage, final GameStatsController gameStatsController) {
        this.stage = stage;
        this.gameStatsHandler = gameStatsController;
        initializeScene();
        this.initializeGameElements();
        setListenerForGameInfo();
    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        entityGroup = new Group();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        gameScene.setOnKeyPressed(event -> inputH.keyPressed(event.getCode()));
        gameScene.setOnKeyReleased(event -> inputH.keyReleased(event.getCode()));

        setupTimer();

        map = new MapBackgroundImpl();
        pauseMenu = new PauseMenu(this.stage, this);
        
        entityHandler = new EntityHandler();
        entityHandler.initialize(gameStatsHandler);

    }

    private void initializeGameElements() {

        root.getChildren().add(map.getPane());
        root.getChildren().add((Node)entityGroup);
        root.getChildren().addAll(gameStatsHandler.getImageView(),gameStatsHandler.getText());
        root.getChildren().add(pauseMenu.getPauseButton());
        root.getChildren().add(pauseMenu.getVBox());
    }

    private void setupTimer() {
        timer = new AnimationTimer() {

            long lastUpdate = 0;
            long lastStatsupdate = 0;
            private static final long statsUpdateInterval = 1_000_000_000L;

            @SuppressWarnings("unused")
            @Override
            public void handle(final long now) {

                if (now - lastUpdate > nSecPerFrame) {

                    map.updateBackground();
                    updateModel();
                    updateView();

                    if(false){
                        showGameOverMenu();
                        endLoop();  
                    }else{
                        entityHandler.update(entityGroup, inputH.isSpacePressed());
                    }
                   
                    lastUpdate = now;
                }

                if(now - lastStatsupdate > statsUpdateInterval){
                    gameStatsHandler.updateModel();
                    lastStatsupdate = now;
                }

            }
        };
    }

    public void startLoop(){
        entityHandler.start();
        timer.start();
    }

    public void stopLoop(){   
        entityHandler.stop();
        timer.stop();
    }

    public void endLoop(){
        saveGame();
        this.stopLoop();
        timer.stop();
    }

    public void resetLoop(){
        saveGame();
        if(!root.getChildren().isEmpty()){
            root.getChildren().clear();
            entityGroup.getChildren().clear();
            map.reset();
            entityHandler.reset();
        }
        initializeGameElements();
    }
    
    
    public Scene getScene(){
        return this.gameScene;
    }

    private void updateModel(){ 
       
    }

    private void updateView() {
        gameStatsHandler.updateView();
    }

    private void setListenerForGameInfo() {
        gameScene.widthProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(newValue.doubleValue(), gameInfo.getScreenHeight());
            pauseMenu.getPauseButton().setLayoutX(newValue.doubleValue()-pauseMenu.getPauseButton().getWidth());
            pauseMenu.getVBox().setPrefWidth(newValue.doubleValue());
        });

        gameScene.heightProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(gameInfo.getScreenWidth(), newValue.doubleValue());
            pauseMenu.getVBox().setPrefHeight(newValue.doubleValue());
        });
    }

    private void saveGame(){
        final String filename = "gameStats.data"; 

        try {
            this.gameStatsHandler.getGameStatsModel().updateDate();
            GameStats.writeToFile(this.gameStatsHandler.getGameStatsModel(), filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }

    public void showGameOverMenu(){
        OverMenu overMenu = new OverMenu(stage, this, gameStatsHandler);
        overMenu.show();
    }
}
