package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * The view class for the shop menu.
 */
public class ShopView {
    private Scene shopScene;
    private Pane root;
    private GameInfo gameInfo;

    /**
     * Constructor for ShopView.
     */
    public ShopView() {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        shopScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        initializeShop();
    }

    /**
     * Gets the scene of the shop menu.
     * @return The scene of the shop menu.
     */
    public Scene getScene() {
        return shopScene;
    }

    private void initializeShop() {
        Image backgroundImage = new Image(getClass().getClassLoader().getResource("shop/shopbg.png").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(gameInfo.getScreenWidth());
        backgroundImageView.setFitHeight(gameInfo.getScreenHeight());
    
        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        backButton.setPrefSize(80, 30);
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);
        backButton.setOnAction(e -> {
            System.out.println("Back button clicked");
            // Handle back button action
        });
    
        Image image1 = new Image(getClass().getClassLoader().getResource("shop/cuddleart.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(150);
        imageView1.setFitHeight(150);
        imageView1.setTranslateX(50);
        imageView1.setTranslateY(150);
    
        Button buyButton1 = new Button("BUY");
        buyButton1.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyButton1.setPrefSize(80, 30);
        buyButton1.setTranslateX(210);
        buyButton1.setTranslateY(150);
    
        Button equipButton1 = new Button("EQUIP");
        equipButton1.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        equipButton1.setPrefSize(80, 30);
        equipButton1.setTranslateX(300);
        equipButton1.setTranslateY(150);

        Image image2 = new Image(getClass().getClassLoader().getResource("shop/lilstomper.png").toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(150);
        imageView2.setFitHeight(150);
        imageView2.setTranslateX(50);
        imageView2.setTranslateY(350);
    
        Button buyButton2 = new Button("BUY");
        buyButton2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyButton2.setPrefSize(80, 30);
        buyButton2.setTranslateX(210);
        buyButton2.setTranslateY(350);
    
        Button equipButton2 = new Button("EQUIP");
        equipButton2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        equipButton2.setPrefSize(80, 30);
        equipButton2.setTranslateX(300);
        equipButton2.setTranslateY(350);
    
        Image image3 = new Image(getClass().getClassLoader().getResource("shop/shield.png").toExternalForm());
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(150);
        imageView3.setFitHeight(150);
        imageView3.setTranslateX(50);
        imageView3.setTranslateY(550);
    
        Button buyButton3 = new Button("BUY");
        buyButton3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyButton3.setPrefSize(80, 30);
        buyButton3.setTranslateX(210);
        buyButton3.setTranslateY(550);
    
        Button equipButton3 = new Button("EQUIP");
        equipButton3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        equipButton3.setPrefSize(80, 30);
        equipButton3.setTranslateX(300);
        equipButton3.setTranslateY(550);
    
    
        Text moneyText = new Text("Money: $100");
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(30);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(gameInfo.getScreenWidth() - 30);

        Text descriptionText1 = new Text("Mr Snuggles\nToo cool not to buy");
        descriptionText1.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        descriptionText1.setFill(Color.WHITE);
        descriptionText1.setTranslateX(450);
        descriptionText1.setTranslateY(180);

        Text descriptionText2 = new Text("Lil Stomper\n Clumsy but robust vehicle");
        descriptionText2.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        descriptionText2.setFill(Color.WHITE);
        descriptionText2.setTranslateX(450);
        descriptionText2.setTranslateY(380);

        Text descriptionText3 = new Text("Shield\n A shield that can be equipped");
        descriptionText3.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        descriptionText3.setFill(Color.WHITE);
        descriptionText3.setTranslateX(450);
        descriptionText3.setTranslateY(580);

        root.getChildren().addAll(backgroundImageView, backButton, imageView1, buyButton1, equipButton1, imageView2, buyButton2, equipButton2, imageView3, buyButton3, equipButton3, moneyText, descriptionText1, descriptionText2, descriptionText3);
    }
}
