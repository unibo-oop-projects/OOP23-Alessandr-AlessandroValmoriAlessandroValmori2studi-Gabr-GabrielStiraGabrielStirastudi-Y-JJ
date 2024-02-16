package it.unibo.jetpackjoyride.core;

import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;

import java.io.IOException;

import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandler;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpHandler;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.GameOverMenu;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class GameLoop {

    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    private WritableImage writableImage;

    private CoinGenerator coinGenerator;
    private GameStatsController gameStatsHandler;

    private PlayerMover playerMover;
    private ObstacleHandler obstacleHandler;
    private PowerUpHandler powerUpHandler;
    private PickUpHandler pickUpHandler;

    private static final int fps = 70;
    private final long nSecPerFrame = Math.round(1.0 / fps * 1e9);

    private Stage stage;
    private Pane root;
    private Group obstacleGroup;
    private Group powerUpGroup;
    private Group pickUpGroup;

    private final InputHandler inputH = new InputHandler();

    public GameLoop(Stage stage) {
        this.stage = stage;
        initializeScene();
        initializeGameElements();

    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        obstacleGroup = new Group();
        powerUpGroup = new Group();
        pickUpGroup = new Group();
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        gameScene.setOnKeyPressed(event -> inputH.keyPressed(event.getCode()));
        gameScene.setOnKeyReleased(event -> inputH.keyReleased(event.getCode()));
        setupTimer();
    }

    private void initializeGameElements() {

        map = new MapBackgroundImpl();
        gameStatsHandler = new GameStatsHandler();

        obstacleHandler = new ObstacleHandler();
        obstacleHandler.initialize();

        playerMover = new PlayerMover();
        powerUpHandler = new PowerUpHandler();
        pickUpHandler = new PickUpHandler();
       
        coinGenerator = new CoinGenerator(playerMover.getHitbox(),gameStatsHandler.getGameStatsModel());
        root.getChildren().add(map.getPane());
        root.getChildren().add(coinGenerator.getCanvas());  
        root.getChildren().add((Node)obstacleGroup);
        root.getChildren().add((Node)powerUpGroup);
        root.getChildren().add((Node)pickUpGroup);
        root.getChildren().addAll(gameStatsHandler.getImageView(),gameStatsHandler.getText());
    }

    private void setupTimer() {
        timer = new AnimationTimer() {

            long lastUpdate = 0;
            long lastStatsupdate = 0;
            private static final long statsUpdateInterval = 1_000_000_000L;

            @Override
            public void handle(final long now) {

                if (now - lastUpdate > nSecPerFrame) {

                    updateModel();
                    updateView();

                    /* TEMPORARY do not code thinking this is finished*/
                    obstacleHandler.update(obstacleGroup, playerMover.getHitbox());
                    if(pickUpHandler.update(obstacleGroup, playerMover.getHitbox())) {
                        powerUpHandler.spawnPowerUp(PowerUpType.LILSTOMPER);
                    }
                    powerUpHandler.update(powerUpGroup, inputH.isSpacePressed());
                    /* TEMPORARY do not code thinking this is finished*/
                    //The idea is to make one big class EntityHandler to handle all smaller handlers such as
                    //powerUphandler, pickUpHandler, obstacleHAndler, etc...
                    //This class will update all handlers and organize the events such as | pickUp took -> powerUpSpawn |
                    //I NEED TO KNOW IF YOU WANT IT TO RETURN SOMETHING IN PARTICULAR (maybe something like an Event of 
                    //an Event enum with all cases (PowerUpSpawned, PowerUpDestroyed, ObstacleHit... so to organize better
                    //with other elements of the game like barry or the speed of the game, etc...))

                    lastUpdate = now;
                }

                if(now - lastStatsupdate > statsUpdateInterval){
                    //stopLoop();
                    gameStatsHandler.updateModel();
                    lastStatsupdate = now;
                }

            }
        };
    }

    public void startLoop(){
        //temporary
        // palyerMover.setLifeStatus(true);
        coinGenerator.startGenerate();
        timer.start();
    }

    public void stopLoop(){
         writableImage = new WritableImage((int)this.gameScene.getWidth(), (int)this.gameScene.getHeight());
         this.gameScene.snapshot(writableImage);
         GameOverMenu gameOverMenu = 
         new GameOverMenu(this.stage, writableImage, this,gameStatsHandler);
         this.stage.setScene(gameOverMenu.getScene());
         endLoop();
    }

    public void endLoop(){
        obstacleHandler.over();
        coinGenerator.stopGenerate();
        saveGame();
        timer.stop();
    }
    
    
    public Scene getScene(){
        return this.gameScene;
    }

    private void updateModel(){ 
             
        updateScreenSize();
        playerMover.move(inputH.isSpacePressed());
        map.updateBackgroundModel();
        coinGenerator.updatPosition();
        
    }

    private void updateView() {

        map.updateBackgroundView();
        coinGenerator.renderCoin();
        playerMover.updateView(root);
        gameStatsHandler.updateView();
    }

    private void updateScreenSize() {
        gameScene.widthProperty().addListener((obs, oldValue, newValue) -> {

            final double newWidth = newValue.doubleValue();
            gameInfo.updateInfo(newWidth, gameInfo.getScreenHeight());
        });

        gameScene.heightProperty().addListener((obs, oldValue, newValue) -> {

            final double newHeight = newValue.doubleValue();
            gameInfo.updateInfo(gameInfo.getScreenWidth(), newHeight);
        });
    }

    private void saveGame(){
        final String filename = "gameStats.data"; 

        try {
            gameStatsHandler.getGameStatsModel().updateDate();
            GameStats.writeToFile(gameStatsHandler.getGameStatsModel(), filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }
}
