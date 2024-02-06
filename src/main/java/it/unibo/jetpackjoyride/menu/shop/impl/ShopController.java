package it.unibo.jetpackjoyride.menu.shop.impl;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ShopController {
    private Scene shopScene;
    private Pane root;

    public ShopController() {
        initializeShop();
    }

    public Scene getScene() {
        return shopScene;
    }

    private void initializeShop() {
        // Create your shop view components
        // For demonstration purposes, let's just create a simple pane
        root = new Pane();
        root.setPrefSize(400, 300);
        shopScene = new Scene(root);
    }
}
