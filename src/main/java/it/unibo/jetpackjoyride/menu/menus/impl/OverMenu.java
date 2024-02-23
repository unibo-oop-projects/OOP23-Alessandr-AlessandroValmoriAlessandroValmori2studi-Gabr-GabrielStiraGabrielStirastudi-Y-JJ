package it.unibo.jetpackjoyride.menu.menus.impl;

import java.io.IOException;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.OpenShopCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.RestartCommand;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class representing the game over menu, extend from the GameMenu.
 * @author yukai.zhou@studio.unibo.it
 */
public final class OverMenu extends GameMenuImpl {

    private static final int SPACE = 20;
    private static final int RESTART_WIDTH = 220;
    private static final int RESTART_HEIGHT = 120;

    private VBox buttonsVBox;
    private GameLoop gameLoop;
    private final ShopController shopController;
    private GameStatsController gameStatsController;
    private WritableImage writableImage;

    /**
     * Constructs a new game over menu.
     * And it call back the constructor of the superclass
     *
     * @param primaryStage      the primary stage
     * @param gameScene          the game scene
     * @param gameStatsController the gameStatsController uses for shop
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public OverMenu(final Stage primaryStage,
                        final Scene gameScene, final GameStatsController gameStatsController) {
        super(primaryStage);
        writableImage = 
        new WritableImage((int) gameScene.getWidth(), (int) gameScene.getHeight());
        gameScene.snapshot(writableImage);
        setMenuImage(writableImage);
        this.gameStatsController = gameStatsController;
        shopController = new ShopControllerImpl(primaryStage, this);
        buttonsVBox = new VBox();
        initializeGameMenu(primaryStage);
    }

    @Override
    protected void initializeGameMenu(final Stage primaryStage) {
        buttonsVBox.setPrefWidth(GameInfo.getInstance().getScreenWidth());
        buttonsVBox.setPrefHeight(GameInfo.getInstance().getScreenHeight());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(SPACE);
        buttonsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        Button restartButton = ButtonFactory.createButton("PlayAgain",
        e -> { 
            gameStatsController.saveChanged();
            this.gameLoop = new GameLoop(primaryStage, shopController); 
            Command restartCommand = new RestartCommand(this.gameLoop);
            this.removeListener();
            restartCommand.execute(); 
        }, RESTART_WIDTH, RESTART_HEIGHT);
        Command openShopCommand = new OpenShopCommand(shopController);
        Button  shopButton = ButtonFactory
        .createButton("Shop", e -> openShopCommand.execute(), DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);

        buttonsVBox.getChildren().addAll(restartButton, shopButton);
        addButtons(buttonsVBox);
    }
}
