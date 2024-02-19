package it.unibo.jetpackjoyride.core;

import java.io.IOException;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsIO;
import it.unibo.jetpackjoyride.menu.menus.OverMenu;
import it.unibo.jetpackjoyride.menu.menus.PauseMenu;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The GameLoop class manages the main game loop and manages different game stages.
 */
public final class GameLoop {

    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;

    private GameStatsController gameStatsHandler;
    private PauseMenu pauseMenu;


    private EntityHandler entityHandler;

    private static final int FPS = 70;
    private final long nSecPerFrame = Math.round(1.0 / FPS * 1e9);

    private Stage stage;
    private Pane root;
    private Group entityGroup;

    private boolean spacePressed;

    /**
     * Constructs a GameLoop object with the specified stage and game statistics controller.
     * All necessary instances are initialized here.
     * @param stage               the primary stage for the game
     * @param gameStatsController the game statistics controller
     */
    public GameLoop(final Stage stage, final GameStatsController gameStatsController) {
        this.stage = stage;
        this.spacePressed = false;
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

        gameScene.setOnKeyPressed(event -> this.spacePressed = event.getCode().equals(KeyCode.SPACE) ? true : false);
        gameScene.setOnKeyReleased(event -> this.spacePressed = event.getCode().equals(KeyCode.SPACE) ? false : true);

        setupTimer();

        map = new MapBackgroundImpl();
        pauseMenu = new PauseMenu(this.stage, this);

        entityHandler = new EntityHandler();
        entityHandler.initialize(gameStatsHandler);

    }

    private void initializeGameElements() {
        root.getChildren().add(map.getPane());
        root.getChildren().add((Node) entityGroup);
        root.getChildren().addAll(gameStatsHandler.getImageView(), gameStatsHandler.getText());
        root.getChildren().add(pauseMenu.getPauseButton());
        root.getChildren().add(pauseMenu.getVBox());
    }

    private void setupTimer() {
        timer = new AnimationTimer() {

            private long lastUpdate = 0;
            private long lastStatsupdate = 0;
            private static final long statsUpdateInterval = 1_000_000_000L;

            @Override
            public void handle(final long now) {

                if (now - lastUpdate > nSecPerFrame) {

                    map.updateBackground();
                    gameStatsHandler.updateView();


                        if (!entityHandler.update(entityGroup, spacePressed)) {
                            showGameOverMenu();            
                            endLoop();
                        }
                    lastUpdate = now;
                }

                if (now - lastStatsupdate > statsUpdateInterval) {
                    gameStatsHandler.updateModel();
                    lastStatsupdate = now;
                }
            }
        };
    }

    /**
     * Starts the game loop.
     */
    public void startLoop() {
        entityHandler.start();
        timer.start();
    }

    /**
     * Stop the game loop.
     */
    public void stopLoop() {
        entityHandler.stop();
        timer.stop();
    }

    /**
     * End the game loop.
     */
    public void endLoop() {
        saveGame();
        this.stopLoop();
        timer.stop();
    }

    /**
     * Reset the game loop.
     */
    public void resetLoop() {
        saveGame();
        if (!root.getChildren().isEmpty()) {
            root.getChildren().clear();
            entityGroup.getChildren().clear();
            map.reset();
            entityHandler.reset();
        }
        initializeGameElements();
    }

    /**
     * Gets the game scene.
     *
     * @return the game scene
     */
    public Scene getScene() {
        return this.gameScene;
    }

    private void setListenerForGameInfo() {
        gameScene.widthProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(newValue.doubleValue(), gameInfo.getScreenHeight());
            pauseMenu.getPauseButton().setLayoutX(newValue.doubleValue() - pauseMenu.getPauseButton().getWidth());
            pauseMenu.getVBox().setPrefWidth(newValue.doubleValue());
        });

        gameScene.heightProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(gameInfo.getScreenWidth(), newValue.doubleValue());
            pauseMenu.getVBox().setPrefHeight(newValue.doubleValue());
        });
    }

    private void saveGame() {
        final String filename = "gameStats.data"; 

        try {
            this.gameStatsHandler.getGameStatsModel().updateDate();
            GameStatsIO.writeToFile(this.gameStatsHandler.getGameStatsModel(), filename); 
            System.out.println("Game stats saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game stats: " + e.getMessage());
        }
    }

    /**
     * Use to set the Over menu, when player dead.
     */
    private void showGameOverMenu() {
        OverMenu overMenu = new OverMenu(stage, this, gameStatsHandler);
        overMenu.show();
    }
}
