package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ShopView {
    private Scene shopScene;
    private Pane root;
    private GameInfo gameInfo;

    public ShopView() {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        shopScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        initializeShop();
    }

    public Scene getScene() {
        return shopScene;
    }

    private void initializeShop() {
        Text shopText = new Text("This is the shop view");
        shopText.setTranslateX(50);
        shopText.setTranslateY(50);

        Button button1 = new Button("Button 1");
        button1.setPrefSize(200, 100); 
        button1.setTranslateX(50);
        button1.setTranslateY(100);

        Button button2 = new Button("Button 2");
        button2.setPrefSize(200, 100); 
        button2.setTranslateX(50);
        button2.setTranslateY(250);

        Button button3 = new Button("Button 3");
        button3.setPrefSize(200, 100); 
        button3.setTranslateX(50);
        button3.setTranslateY(400);

        root.getChildren().addAll(shopText, button1, button2, button3);
    }
}
