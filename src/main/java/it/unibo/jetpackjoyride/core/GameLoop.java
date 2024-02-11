package it.unibo.jetpackjoyride.core;

import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandlerImpl;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
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
   
    private CoinGenerator coinGenerator;
    private GameStatsController gameStatsHandler;

    private ObstacleHandlerImpl entityHandler;
    private PlayerMover playerMover;
    private PowerUpHandler powerUpHandler;

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
        gameInfo = GameInfo.getInstance();
        obstacleGroup = new Group();
        powerUpGroup = new Group();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        
        gameScene.setOnKeyPressed(event -> inputH.keyPressed(event.getCode()));
        gameScene.setOnKeyReleased(event -> inputH.keyReleased(event.getCode()));
        setupTimer();   
    }

    private void initializeGameElements(){
        
        map = new MapBackgroundImpl(gameInfo);
        gameStatsHandler = new GameStatsHandler();

        entityHandler = new ObstacleHandlerImpl();
        entityHandler.initialize();

        playerMover = new PlayerMover();
        powerUpHandler = new PowerUpHandler();

        root.getChildren().add((Node)map);
        coinGenerator = new CoinGenerator(playerMover.getHitbox(),gameStatsHandler.getGameStatsModel());
       
      
        root.getChildren().add(coinGenerator.getCanvas());  
        root.getChildren().add((Node)obstacleGroup);
        root.getChildren().add((Node)powerUpGroup);
        root.getChildren().add(gameStatsHandler.getText());
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

    public void starLoop(){
        coinGenerator.startGenerate();
        timer.start();
    }

    public void endLoop(){
        entityHandler.over();
    }
    
    public Scene getScene(){
        return this.gameScene;
    }

    private void updateModel(){ 
        
        playerMover.move(inputH.isSpacePressed());
        updateScreenSize();
        map.updateBackgroundModel();
        coinGenerator.updatPosition();
        gameStatsHandler.updateModel();
        
    }

    private void updateView(){
        
        map.updateBackgroundView();
        coinGenerator.renderCoin();
        playerMover.updateView(root);
        gameStatsHandler.updateView();
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
}