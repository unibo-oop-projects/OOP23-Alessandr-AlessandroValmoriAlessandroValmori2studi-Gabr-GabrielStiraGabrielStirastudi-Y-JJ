package it.unibo.jetpackjoyride.core;


import it.unibo.jetpackjoyride.core.handler.ChunkMakerImpl;
import it.unibo.jetpackjoyride.core.handler.ObstacleController;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/** */
public class GameLoop {
    public static final int MAP_WIDTH = 1200; 
    public static final int MAP_HEIGHT = 800;

    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    private ChunkMakerImpl chunkMaker;
    private Thread chunkThread;
    Pane root ;


    public GameLoop(){
        initializeScene();
        initializeGameElements();
        setupTimer();
    }

    private void initializeScene() {
        root = new Pane();
        gameScene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);
    }

    private void initializeGameElements(){
        gameInfo = new GameInfo(MAP_WIDTH, MAP_HEIGHT);
        map = new MapBackgroundImpl(gameInfo);

        chunkMaker = new ChunkMakerImpl();
        chunkMaker.initialize();

        root.getChildren().add((Node)map);
    }

    private void setupTimer(){
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                update();
            }
             
        };
    }

    private void update(){ 
        updateScreenSize();
        map.updateBackground();
        chunkMaker.update(root);
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
