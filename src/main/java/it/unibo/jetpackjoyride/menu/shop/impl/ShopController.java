package it.unibo.jetpackjoyride.menu.shop.impl;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ShopController {
    
  
    private ShopModel model;
    private ShopView view;

    public ShopController() {
       this.model = new ShopModel();
       this.view = new ShopView();
    }

    public Scene getScene() {
        return this.view.getScene();
    }

   
}
