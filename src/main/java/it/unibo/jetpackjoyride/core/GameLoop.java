package it.unibo.jetpackjoyride.core;

// TEMPORARY
import it.unibo.jetpackjoyride.core.handler.ChunkSpawner;
import it.unibo.jetpackjoyride.core.handler.ObstacleController;
// TEMPORARY
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.*;

/** */
public class GameLoop implements Runnable{
   
    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    // TEMPORARY
    private ChunkSpawner chunkspawner;
    // TEMPORARY
    Pane root ;
    private boolean isRunning;

    private List<ObstacleController> obstaclesControllers;

    public GameLoop(){
        this.isRunning = false;
        initializeScene();
        initializeGameElements();
     
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
            
            }
             
        };
    }


    private void updateModel(){ 
        updateScreenSize();
        map.updateBackgroundModel();
        for (ObstacleController obstacle : obstaclesControllers) {
            obstacle.updateModel();
        }
        
    }

    private void updateView(){
        map.updateBackgroundView();
        for (ObstacleController obstacle : obstaclesControllers) {
            obstacle.updateView();
        }
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
        this.isRunning = true;
    }

    public void endLoop(){
        this.isRunning = false;
    }
    
    public Scene getScene(){
        return this.gameScene;
    }

    @Override
       public void run() {
           long lastTime = System.nanoTime();
           double amountOfTicks = 60.0;
           double ns = 1000000000 / amountOfTicks;
           double delta = 0;
           long timer = System.currentTimeMillis();
           int updates = 0;
           int frames = 0;
   
           while (isRunning) {
               long now = System.nanoTime();
               delta += (now - lastTime) / ns;
               lastTime = now;
               while (delta >= 1) {

                updateModel();
                
                Platform.runLater(()->{
                  updateView();
                });
              
                   updates++;
                   delta--;
               }
   
               frames++;
   
               if (System.currentTimeMillis() - timer > 1000) {
                   timer += 1000;
                   updates = 0;
                   frames = 0;
               }
            }
        }


}
