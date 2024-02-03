package it.unibo.jetpackjoyride.core;

import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.handler.EntityHandlerImpl;
import it.unibo.jetpackjoyride.core.handler.PowerUpController;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameLoop{
   
    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;

    private EntityHandlerImpl entityHandler;
    private PlayerMover playerMover;
    private PowerUpController powerUpHandler;

    private final int FPS=70;
    private final long nSecPerFrame= Math.round(1.0/FPS * 1e9);

    private Pane root ;
    private Group obstacleGroup;
    private Group powerUpGroup;

   
    private InputHandler inputH = new InputHandler();


    public GameLoop(){
        initializeScene();
        initializeGameElements();
     
    }

    private void initializeScene() {
        root = new Pane();
        obstacleGroup = new Group();
        powerUpGroup = new Group();
        gameInfo = new GameInfo();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        
        gameScene.setOnKeyPressed(event -> inputH.keyPressed(event.getCode()));
        gameScene.setOnKeyReleased(event -> inputH.keyReleased(event.getCode()));
        setupTimer();   
    }

    private void initializeGameElements(){
        
        map = new MapBackgroundImpl(gameInfo);

        entityHandler = new EntityHandlerImpl();
        entityHandler.initialize();

        playerMover = new PlayerMover();
        powerUpHandler = new PowerUpController();

        root.getChildren().add((Node)map);
        root.getChildren().add((Node)obstacleGroup);
        root.getChildren().add((Node)powerUpGroup);
    }

    private void setupTimer(){
        timer = new AnimationTimer() {

            private long lastUpdate=0;

            @Override
            public void handle(long now) {

                if(now - lastUpdate > nSecPerFrame){
                
               

                updateModel();
                updateView();
                entityHandler.update(obstacleGroup, playerMover.getHitbox());
                powerUpHandler.update(inputH.isSpacePressed(), powerUpGroup);
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
    }

    public void endLoop(){
        entityHandler.over();
    }
    
    public Scene getScene(){
        return this.gameScene;
    }

}