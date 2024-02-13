package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
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
import static it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

import java.util.Optional;

/**
 * The view class for the shop menu.
 */
public class ShopView {
    private Scene shopScene;
    private Pane root;
    private GameInfo gameInfo;
    private double width;
    private double height;
    private ShopController controller;
    private Text moneyText;
    private Text displayEquipped;

    

    /**
     * Constructor for ShopView.
     */
    public ShopView(ShopController controller) {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        this.width = gameInfo.getScreenWidth();
        this.height = gameInfo.getScreenHeight();
        this.controller = controller;
        shopScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        
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
            this.controller.backToMenu();
        });
    
        Image cuddlesImage = new Image(getClass().getClassLoader().getResource("shop/cuddleart.png").toExternalForm());
        ImageView cuddlesImageView = new ImageView(cuddlesImage);
        cuddlesImageView.setFitWidth(150);
        cuddlesImageView.setFitHeight(150);
        cuddlesImageView.setTranslateX(50);
        cuddlesImageView.setTranslateY(100);
    
        Button buyMrCuddlesButton = new Button("BUY");
        buyMrCuddlesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyMrCuddlesButton.setPrefSize(80, 30);
        buyMrCuddlesButton.setTranslateX(210);
        buyMrCuddlesButton.setTranslateY(150);
        buyMrCuddlesButton.setOnAction(e -> {
            this.controller.buy(Items.MRCUDDLES);
        });

        Button equipCuddlesButton = new Button("EQUIP");
        equipCuddlesButton.setStyle("-fx-background-color: #0010e8; -fx-text-fill: white; -fx-font-size: 16;");
        equipCuddlesButton.setPrefSize(80, 30);
        equipCuddlesButton.setTranslateX(300);
        equipCuddlesButton.setTranslateY(150);
        equipCuddlesButton.setOnAction(e -> {
            this.controller.equip(Items.MRCUDDLES);
        });

        Image stomperImage = new Image(getClass().getClassLoader().getResource("shop/lilstomper.png").toExternalForm());
        ImageView stomperImageView = new ImageView(stomperImage);
        stomperImageView.setFitWidth(150);
        stomperImageView.setFitHeight(150);
        stomperImageView.setTranslateX(50);
        stomperImageView.setTranslateY(300);
    
        Button buyStomperButton = new Button("BUY");
        buyStomperButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyStomperButton.setPrefSize(80, 30);
        buyStomperButton.setTranslateX(210);
        buyStomperButton.setTranslateY(350);
        buyStomperButton.setOnAction(e -> {
            this.controller.buy(Items.STOMPER);
        });
    
        Button equipStomperButton = new Button("EQUIP");
        equipStomperButton.setStyle("-fx-background-color: #0010e8; -fx-text-fill: white; -fx-font-size: 16;");
        equipStomperButton.setPrefSize(80, 30);
        equipStomperButton.setTranslateX(300);
        equipStomperButton.setTranslateY(350);
        equipStomperButton.setOnAction(e -> {
            this.controller.equip(Items.STOMPER);
        });

        Text stomperNum = new Text("22");
        stomperNum.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        stomperNum.setFill(Color.WHITE);
        stomperNum.setTranslateX(400);
        stomperNum.setTranslateY(350);

    
        Image shieldImage = new Image(getClass().getClassLoader().getResource("shop/shield.png").toExternalForm());
        ImageView shieldImageView = new ImageView(shieldImage);
        shieldImageView.setFitWidth(150);
        shieldImageView.setFitHeight(150);
        shieldImageView.setTranslateX(50);
        shieldImageView.setTranslateY(500);
    
        Button buyShieldButton = new Button("BUY");
        buyShieldButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyShieldButton.setPrefSize(80, 30);
        buyShieldButton.setTranslateX(210);
        buyShieldButton.setTranslateY(550);
        buyShieldButton.setOnAction(e -> {
            this.controller.buy(Items.SHIELD);
        });
    
        Button equipShield = new Button("EQUIP");
        equipShield.setStyle("-fx-background-color: #0010e8; -fx-text-fill: white; -fx-font-size: 16;");
        equipShield.setPrefSize(80, 30);
        equipShield.setTranslateX(300);
        equipShield.setTranslateY(550);
        equipShield.setOnAction(e -> {
            this.controller.equip(Items.SHIELD);
        });
    
    
        moneyText = new Text();
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(30);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(gameInfo.getScreenWidth() - 30);

        displayEquipped = new Text();
        displayEquipped.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        displayEquipped.setFill(Color.WHITE);
        displayEquipped.setTranslateY(200);
        displayEquipped.setTextAlignment(TextAlignment.RIGHT);
        displayEquipped.setWrappingWidth(gameInfo.getScreenWidth() - 30);

        

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

        root.getChildren().addAll(
            backgroundImageView, 
            backButton, 
            cuddlesImageView, 
            buyMrCuddlesButton, 
            equipCuddlesButton, 
            stomperImageView, 
            buyStomperButton, 
            equipStomperButton, 
            shieldImageView, 
            buyShieldButton, 
            equipShield, 
            moneyText, 
            descriptionText1, 
            descriptionText2, 
            descriptionText3, 
            displayEquipped,
            stomperNum
        );
        this.update();
    }

    /**
     * Gets the scene of the shop menu.
     * @return The scene of the shop menu.
     */
    public Scene getScene() {
        return shopScene;
    }

    public void update(){
        this.moneyText.setText("Money: $" + String.valueOf(this.controller.retrieveBalance()));
        this.displayEquipped.setText(this.controller.getEquipped().isEmpty() ? "Nothing" : this.controller.getEquipped().get().toString());
    }

    
    
}
