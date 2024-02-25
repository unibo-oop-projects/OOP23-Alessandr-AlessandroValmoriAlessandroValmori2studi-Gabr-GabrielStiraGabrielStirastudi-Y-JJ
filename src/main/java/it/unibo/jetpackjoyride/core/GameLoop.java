package it.unibo.jetpackjoyride.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jetpackjoyride.core.handler.entity.EntityController;
import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.impl.MapBackgroundImpl;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsViewImpl;
import it.unibo.jetpackjoyride.menu.menus.impl.OverMenu;
import it.unibo.jetpackjoyride.menu.menus.impl.PauseMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The GameLoop class manages the main game loop and manages different game
 * stages.
 */
public final class GameLoop implements GameLoopControl {
    private static final int FPS = 70;
    private static final long nSecPerFrame = Math.round(1.0 / FPS * 1e9);

    private Scene gameScene;
    private GameInfo gameInfo;
    private AnimationTimer timer;
    private MapBackground map;
    private GameStatsController gameStatsHandler;
    private PauseMenu pauseMenu;
    private GameStatsView gameStatsView;
    private EntityController entityController;
    private Stage stage;
    private Pane root;
    private Group entityGroup;
    private boolean spacePressed;

    /**
     * Constructs a GameLoop object with the specified stage and game statistics
     * controller.
     * All necessary instances are initialized here.
     * 
     * @param stage          the primary stage for the game
     * @param shopController The shopController
     */
    @SuppressFBWarnings(value = "EI2", 
    justification = "Stage object is necessary for game state management and transitions")
    public GameLoop(final Stage stage, final ShopController shopController) {
        this.stage = stage;
        this.spacePressed = false;
        this.gameStatsHandler = new GameStatsHandler();
        this.entityController = new EntityController(shopController);
        initializeScene();
        this.initializeGameElements();
        setListenerForGameInfo();
        stage.centerOnScreen();
    }

    private void initializeScene() {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        entityGroup = new Group();
        gameInfo.updateInfo(gameInfo.getDefaultWidth(), gameInfo.getDefaultHeight());
        gameScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        gameScene.setOnKeyPressed(event -> this.spacePressed = event.getCode().equals(KeyCode.SPACE));
        gameScene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.SPACE)) {
                this.spacePressed = false;
            }
        });

        setupTimer();

        map = new MapBackgroundImpl();
        pauseMenu = new PauseMenu(this.stage, this);
        gameStatsView = new GameStatsViewImpl();
    }

    private void initializeGameElements() {
        map.setMapOnGameRoot(this.root);
        root.getChildren().add((Node) entityGroup);
        gameStatsView.setNodeOnRoot(root);
        pauseMenu.setPauseButton(this.root);
        pauseMenu.setButtonVBox(this.root);
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
                    gameStatsView.updateDataView(gameStatsHandler.dataForView());

                    if (!entityController.update(entityGroup, spacePressed)) {
                        showGameOverMenu();
                        endLoop();
                    }
                    lastUpdate = now;
                }

                if (now - lastStatsupdate > statsUpdateInterval) {
                    gameStatsHandler.updateCurrentDistance();
                    lastStatsupdate = now;
                }
            }
        };
    }

    /**
     * Starts the game loop.
     */
    public void startLoop() {
        stage.setScene(gameScene);
        entityController.start();
        timer.start();
        this.stage.centerOnScreen();
    }

    /**
     * Stop the game loop.
     */
    public void stopLoop() {
        entityController.stop();
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
            entityController.reset();
        }
        initializeGameElements();
    }

    private void setListenerForGameInfo() {
        gameScene.widthProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(newValue.doubleValue(), gameInfo.getScreenHeight());
            pauseMenu.setPauseButtonSize(newValue.doubleValue());
            pauseMenu.setButtonVBoxSizeX(newValue.doubleValue());
        });

        gameScene.heightProperty().addListener((obs, oldValue, newValue) -> {
            gameInfo.updateInfo(gameInfo.getScreenWidth(), newValue.doubleValue());
            pauseMenu.setButtonVBoxSizeY(newValue.doubleValue());
        });
    }

    private void saveGame() {
        gameStatsHandler.saveChanged();
    }

    /**
     * Use to set the Over menu, when player dead.
     */
    private void showGameOverMenu() {
        OverMenu overMenu = new OverMenu(stage, this.gameScene, this.gameStatsHandler);
        overMenu.showMenu();
    }
}
