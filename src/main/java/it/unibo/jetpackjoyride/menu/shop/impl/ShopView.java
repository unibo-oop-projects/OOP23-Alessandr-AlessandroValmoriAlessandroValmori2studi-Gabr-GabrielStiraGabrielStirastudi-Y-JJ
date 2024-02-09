package it.unibo.jetpackjoyride.menu.shop.impl;


import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        Rectangle background = new Rectangle(gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        background.setFill(Color.LIGHTBLUE);

        Button backButton = new Button("Back");
        backButton.setPrefSize(100, 50);
        backButton.setStyle("-fx-background-color: #CCCCCC; -fx-text-fill: black; -fx-font-size: 16;");
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);
        backButton.setOnAction(e -> {
            System.out.println("Back button clicked");
        });

        Button button1 = new Button();
        button1.setPrefSize(150, 150);
        button1.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");
        button1.setTranslateX(50);
        button1.setTranslateY(150);
        setImageOnButton(button1, "shop/cuddleart.png");

        Button button2 = new Button();
        button2.setPrefSize(150, 150);
        button2.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");
        button2.setTranslateX(50);
        button2.setTranslateY(350);
        setImageOnButton(button2, "shop/lilstomper.png");

        Button button3 = new Button();
        button3.setPrefSize(150, 150);
        button3.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");
        button3.setTranslateX(50);
        button3.setTranslateY(550);
        setImageOnButton(button3, "shop/shield.png");

        root.getChildren().addAll(background, backButton, button1, button2, button3);
    }

    private void setImageOnButton(Button button, String imageUrl) {
        Image image = new Image(getClass().getClassLoader()
        .getResource(imageUrl).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(button.getPrefWidth());
        imageView.setFitHeight(button.getPrefHeight());
        button.setGraphic(imageView);
    }
}
