package it.unibo.jetpackjoyride.core;

// TEMPORARY
import it.unibo.jetpackjoyride.core.handler.ChunkSpawner;
import it.unibo.jetpackjoyride.core.handler.ObstacleController;
// TEMPORARY
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;

/** */
public class GameLoop {
   
    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    // TEMPORARY
    private ChunkSpawner chunkspawner;
    // TEMPORARY
    Pane root ;

    private List<ObstacleController> obstaclesControllers;

    public GameLoop(){
        initializeScene();
        initializeGameElements();
        setupTimer();
    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = new GameInfo();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
    }

    private void initializeGameElements(){
        
        map = new MapBackgroundImpl(gameInfo);
        // TEMPORARY
        chunkspawner = new ChunkSpawner();
        chunkspawner.initialize();
        obstaclesControllers = chunkspawner.generateChunk();
        // TEMPORARY
        root.getChildren().add((Node)map);
        for (ObstacleController obstacle : obstaclesControllers) {
            root.getChildren().add(obstacle.getImageView());
        }
    }

    private void setupTimer(){
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                update();
                for (ObstacleController obstacle : obstaclesControllers) {
                    obstacle.update();
                }
            }
             
        };
    }


    private void update(){ 
        updateScreenSize();
        map.updateBackground();
    }

    private void updateScreenSize() {
        gameScene.widthProperty().addListener((obs, oldValue, newValue) -> {

            double newWidth = newValue.doubleValue();
            gameInfo.updateInfo(newWidth, gameInfo.getScreenHeight());
        });
    
        gameScene.heightProperty().addListener((obs, oldValue, newValue) -> {
           
            double newHeight = newValue.doubleValue();
            gameInfo.updateInfo(gameInfo.getScreenWidth(), newHeight);
        });
    }
    

 

    public void starLoop(){
        timer.start();
    }
    


    public Scene getScene(){
        return this.gameScene;
    }

}
