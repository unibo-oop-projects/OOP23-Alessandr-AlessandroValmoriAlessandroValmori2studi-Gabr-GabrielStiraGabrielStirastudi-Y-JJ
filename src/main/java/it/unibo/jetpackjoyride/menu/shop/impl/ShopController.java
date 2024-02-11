package it.unibo.jetpackjoyride.menu.shop.impl;

import javafx.scene.Scene;


/**
 * Controller class for the shop menu.
 * This class manages the interaction between the shop model and view.
 */
public final class ShopController {
    private ShopModel model;
    private ShopView view;

    /**
     * Constructs a new ShopController.
     * Initializes the model and view components.
     */
    public ShopController() {
        this.model = new ShopModel();
        this.view = new ShopView();
    }

    /**
     * Retrieves the scene of the shop menu.
     * @return The scene of the shop menu.
     */
    public Scene getScene() {
        return this.view.getScene();
    }
}
