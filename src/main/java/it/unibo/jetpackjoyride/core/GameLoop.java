package it.unibo.jetpackjoyride.core;


import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryView;
import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.handler.ChunkMakerImpl;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameLoop{
   
    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    private ChunkMakerImpl chunkMaker;
    Pane root ;
    private boolean isRunning;
    private final int FPS=70;
    private long nSecPerFrame= Math.round(1.0/FPS * 1e9);
    PlayerMover playerMover;

   
    private InputHandler inputH = new InputHandler();


    public GameLoop(){
        this.isRunning = false;
        initializeScene();
        initializeGameElements();
     
    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = new GameInfo();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        
        gameScene.setOnKeyPressed(event -> inputH.keyPressed(event.getCode()));
        gameScene.setOnKeyReleased(event -> inputH.keyReleased(event.getCode()));
        setupTimer();   
    }

    private void initializeGameElements(){
        
        map = new MapBackgroundImpl(gameInfo);

        chunkMaker = new ChunkMakerImpl();
        chunkMaker.initialize();

        playerMover = new PlayerMover();

        root.getChildren().add((Node)map);
    }

    private void setupTimer(){
        timer = new AnimationTimer() {

            private long lastUpdate=0;

            @Override
            public void handle(long now) {

                if(now - lastUpdate > nSecPerFrame){
                
               

                updateModel();
                updateView();
                chunkMaker.update(root);

                lastUpdate=now;
                }
                
            }
        };
    }


    private void updateModel(){ 
        
        playerMover.move(inputH.isSpacePressed());
        updateScreenSize();
        map.updateBackgroundModel();
        
    }

    private void updateView(){
        
        map.updateBackgroundView();
        playerMover.updateView(root);
        
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

    /* 

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

*/
}