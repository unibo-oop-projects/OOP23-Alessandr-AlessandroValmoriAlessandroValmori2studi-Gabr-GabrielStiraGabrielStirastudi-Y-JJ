package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopControllerImpl implements ShopController {
    private ShopModel model;
    private ShopView view;
    private Stage primaryStage;
    private GameLoop gameLoop;

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopControllerImpl(Stage primaryStage, GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        this.primaryStage = primaryStage;
        this.model = new ShopModel();
        this.view = new ShopView(this);
    }

    /**
     * Retrieves the scene of the shop menu.
     * @return The scene of the shop menu.
     */
    @Override
    public Scene getScene() {
        return this.view.getScene();
    }

    @Override
    public void backToMenu() {
        GameMenu gameMenu = new GameMenu(this.primaryStage, this.gameLoop);
        this.primaryStage.setScene(gameMenu.getScene());
        primaryStage.show();
    }
}
