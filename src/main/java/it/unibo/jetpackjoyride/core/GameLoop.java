package it.unibo.jetpackjoyride.core;

import it.unibo.jetpackjoyride.core.handler.ChunkMakerImpl;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameLoop implements Runnable{
   
    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    private ChunkMakerImpl chunkMaker;
    Pane root ;
    private boolean isRunning;


    public GameLoop(){
        this.isRunning = false;
        initializeScene();
        initializeGameElements();
     
    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = new GameInfo();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        setupTimer();
    }

    private void initializeGameElements(){
        
        map = new MapBackgroundImpl(gameInfo);

        chunkMaker = new ChunkMakerImpl();
        chunkMaker.initialize();

        root.getChildren().add((Node)map);
    }

    private void setupTimer(){
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                updateModel();
                updateView();
            }
        };
    }


    private void updateModel(){ 
        updateScreenSize();
        map.updateBackgroundModel();
        chunkMaker.updateModel();
        
    }

    private void updateView(){
        map.updateBackgroundView();
        chunkMaker.updateView(root);
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
        this.isRunning = true;
    }

    public void endLoop(){
        this.isRunning = false;
        chunkMaker.over();
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