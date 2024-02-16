package it.unibo.jetpackjoyride.core;

import it.unibo.jetpackjoyride.core.entities.coin.impl.CoinGenerator;
import java.io.IOException;
import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler.Event;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.menus.OverMenu;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;
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

    private CoinGenerator coinGenerator;
    private GameStatsController gameStatsHandler;
    private PauseMenu pauseMenu;

    private PlayerMover playerMover;
    private EntityHandler entityHandler;

    private static final int fps = 70;
    private final long nSecPerFrame = Math.round(1.0 / fps * 1e9);

    private Stage stage;
    private Pane root;
    private Group entityGroup;

    private final InputHandler inputH = new InputHandler();

    public GameLoop(Stage stage) {
        this.stage = stage;
        initializeScene();
        this.initializeGameElements();
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
        gameStatsHandler = new GameStatsHandler();

        entityHandler = new EntityHandler();
        entityHandler.initialize(gameStatsHandler.getGameStatsModel().getUnlocked());

        playerMover = new PlayerMover();
       
        coinGenerator = new CoinGenerator(playerMover.getHitbox(),gameStatsHandler.getGameStatsModel());
    }

    private void initializeGameElements() {

        root.getChildren().add(map.getPane());
        root.getChildren().add(coinGenerator.getCanvas());  
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

                    updateModel();
                    updateView();

                    /* TEMPORARY do not code thinking this is finished*/
                    /* TEMPORARY do not code thinking this is finished*/
                    //The idea is to make one big class EntityHandler to handle all smaller handlers such as
                    //powerUphandler, pickUpHandler, obstacleHAndler, etc...
                    //This class will update all handlers and organize the events such as | pickUp took -> powerUpSpawn |
                    //I NEED TO KNOW IF YOU WANT IT TO RETURN SOMETHING IN PARTICULAR (maybe something like an Event of 
                    //an Event enum with all cases (PowerUpSpawned, PowerUpDestroyed, ObstacleHit... so to organize better
                    //with other elements of the game like barry or the speed of the game, etc...))
                   
                     /* TEMPORARY*/
                    if(false){
                        showGameOverMenu();
                        endLoop();  
                    }else{
                        Event eventHappening;
                        eventHappening = entityHandler.update(entityGroup, playerMover.getHitbox(), inputH.isSpacePressed());
                        System.out.println(eventHappening);
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
        //stopLoop();
        coinGenerator.startGenerate();
        entityHandler.start();
        timer.start();
    }

    public void stopLoop(){   
        coinGenerator.stopGenerate();
        entityHandler.stop();
        timer.stop();
    }

    public void endLoop(){
        this.stopLoop();
        timer.stop();
        saveGame();
    }

    public void resetLoop(){
        saveGame();
        if(!root.getChildren().isEmpty()){
            root.getChildren().clear();
            entityGroup.getChildren().clear();
            coinGenerator.clean();
            map.reset();
            entityHandler.reset();
        }
        initializeGameElements();
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
            pauseMenu.getPauseButton().setLayoutX(newWidth-pauseMenu.getPauseButton().getWidth());
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

    public void showGameOverMenu(){
        OverMenu overMenu = new OverMenu(stage, this, gameStatsHandler);
        overMenu.show();
    }
}
