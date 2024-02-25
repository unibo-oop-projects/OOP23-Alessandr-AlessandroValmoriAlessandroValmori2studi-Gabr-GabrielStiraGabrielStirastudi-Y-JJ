package it.unibo.jetpackjoyride.menu.menus.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.buttoncommand.api.Command;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.OpenShopCommand;
import it.unibo.jetpackjoyride.menu.buttoncommand.impl.StartCommand;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Optional;

/**
 * Class representing the  Start menu, extend from the GameMenu.
 * @author yukai.zhou@studio.unibo.it
 */
public final class StartMenu extends GameMenuImpl {
    private static final int SPACING = 20;

    private Optional<GameLoop> gameLoop = Optional.empty();
    private final ShopController shopController;
    private final GameStatsController gameStatsController;

    /**
     * Constructs a new StartMenu.
     * And it call back the constructor of the superclass
     * @param primaryStage        the primary stage
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public StartMenu(final Stage primaryStage) {
        super(primaryStage);
        final Image menuImage = new Image(getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm());
        setMenuImage(menuImage);
        gameStatsController = new GameStatsHandler();
        shopController = new ShopControllerImpl(primaryStage, this);
        this.gameLoop = Optional.of(new GameLoop(primaryStage, shopController)); 
        initializeGameMenu(primaryStage);
        primaryStage.setMinHeight(GameInfo.getInstance().getDefaultHeight());
        primaryStage.setMinWidth(GameInfo.getInstance().getDefaultWidth());
        primaryStage.centerOnScreen();
        stageCloseAction(primaryStage);
    }

    @Override
    protected void initializeGameMenu(final Stage primaryStage) {
        final VBox buttonsRoot = new VBox(SPACING);
        buttonsRoot.setAlignment(Pos.CENTER);

        final Button startButton = ButtonFactory.createButton("PlayGame", e -> { 
            gameStatsController.saveChanged();
            this.gameLoop = Optional.of(new GameLoop(primaryStage, shopController)); 
            final Command startCommand = new StartCommand(this.gameLoop.get());
            this.removeListener();
            startCommand.execute();
            primaryStage.centerOnScreen();
        }, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);
        final Command openShopCommand = new OpenShopCommand(shopController);
        final Button  shopButton = ButtonFactory
        .createButton("Shop", e -> openShopCommand.execute(), DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);

        buttonsRoot.getChildren().addAll(startButton, shopButton);

        StackPane.setMargin(buttonsRoot, new Insets(primaryStage.getHeight() / 2, 0, 0, 0));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            StackPane.setMargin(buttonsRoot, new Insets(newVal.doubleValue() / 2, 0, 0, 0));
        });

        addButtons(buttonsRoot);
    }

    @Override
    protected void stageCloseAction(final Stage primaryStage) {
       primaryStage.setOnCloseRequest(event -> {
            if (this.gameLoop.isPresent()) {
            this.gameLoop.get().endLoop();
            }
            this.shopController.save();
            defaultCloseAction();
        });
    }
}
