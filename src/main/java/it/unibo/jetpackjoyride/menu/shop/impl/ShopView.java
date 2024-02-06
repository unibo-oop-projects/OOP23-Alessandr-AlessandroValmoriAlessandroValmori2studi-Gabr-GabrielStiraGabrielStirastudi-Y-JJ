package it.unibo.jetpackjoyride.menu.shop.impl;



import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ShopView {
    private Scene shopScene;
    private Pane root;

    public ShopView() {
        initializeShop();
    }

    public Scene getScene() {
        return shopScene;
    }

    private void initializeShop() {
        // Create your shop view components
        root = new Pane();
        root.setPrefSize(400, 300);
        
        Text shopText = new Text("This is the shop view");
        shopText.setTranslateX(50);
        shopText.setTranslateY(50);
        
        root.getChildren().add(shopText);
        
        shopScene = new Scene(root);
    }
}
