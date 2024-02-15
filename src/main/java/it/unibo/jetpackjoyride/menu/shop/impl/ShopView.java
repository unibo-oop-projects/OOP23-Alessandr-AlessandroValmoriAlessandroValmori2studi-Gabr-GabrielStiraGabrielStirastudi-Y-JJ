package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
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
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import static it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

/**
 * The view class for the shop menu.
 */
public class ShopView {
    // Constants related to image positioning
    private final int imageXPos = 50;
    private final int imageSize = 110;
    private final int imageDistance = 30;

    // Constants related to button positioning
    private final int buyButtonXPosition = 210;
    private final int buttonWidth = 80;
    private final int buttonHeight = 30;
    private final int buyButtonYDisplacement = imageSize / 2;

    // Constants related to text and font
    private final int fontSize = 18;
    private final int shieldNumPosX = buyButtonXPosition + 2 * buttonWidth + 2 * imageDistance;
    private final int textPosX = shieldNumPosX + imageDistance;

    // Constants related to image positioning on the Y-axis
    private final int cuddleImageYPos = 100;
    private final int stomperImageYPos = cuddleImageYPos + imageSize + imageDistance;
    private final int profitBirdImageYPos = stomperImageYPos + imageSize + imageDistance;
    private final int shieldImageYPos = profitBirdImageYPos + imageSize + imageDistance;

    // Other constants
    private final Scene shopScene;
    private final Pane root;
    private final GameInfo gameInfo;
    private final ShopController controller;
    private final Text moneyText;
    private final Text displayEquipped;
    private Text shieldNum;
    /*
     * private List<ImageView> imageViewList = new ArrayList<>();
     * private List<Button> buttonList= new ArrayList<>();
     * private Map<Pair<Integer, Integer>, Pair<ImageView, Button>> map = new
     * HashMap<>();
     */

    /**
     * Constructor for ShopView.
     */
    public ShopView(final ShopController controller) {
        root = new Pane();
        gameInfo = GameInfo.getInstance();
        this.controller = controller;
        shopScene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        final Image backgroundImage = new Image(
                getClass().getClassLoader().getResource("shop/shopbg.png").toExternalForm());
        final ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(gameInfo.getScreenWidth());
        backgroundImageView.setFitHeight(gameInfo.getScreenHeight());

        final Button backButton = new Button("BACK");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        backButton.setPrefSize(buttonWidth, buttonHeight);
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);
        backButton.setOnAction(e -> {
            this.controller.backToMenu();
        });

        final Image cuddlesImage = new Image(
                getClass().getClassLoader().getResource("shop/cuddleart.png").toExternalForm());
        final ImageView cuddlesImageView = new ImageView(cuddlesImage);
        cuddlesImageView.setFitWidth(imageSize);
        cuddlesImageView.setFitHeight(imageSize);
        cuddlesImageView.setTranslateX(imageXPos);
        cuddlesImageView.setTranslateY(cuddleImageYPos);

        final Button buyMrCuddlesButton = new Button("BUY");
        buyMrCuddlesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyMrCuddlesButton.setPrefSize(buttonWidth, buttonHeight);
        buyMrCuddlesButton.setTranslateX(buyButtonXPosition);
        buyMrCuddlesButton.setTranslateY(cuddleImageYPos + buyButtonYDisplacement);
        buyMrCuddlesButton.setOnAction(e -> {
            this.controller.buy(Items.MRCUDDLES);
        });

        final Image stomperImage = new Image(
                getClass().getClassLoader().getResource("shop/lilstomper.png").toExternalForm());
        final ImageView stomperImageView = new ImageView(stomperImage);
        stomperImageView.setFitWidth(imageSize);
        stomperImageView.setFitHeight(imageSize);
        stomperImageView.setTranslateX(imageXPos);
        stomperImageView.setTranslateY(stomperImageYPos);

        final Button buyStomperButton = new Button("BUY");
        buyStomperButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyStomperButton.setPrefSize(buttonWidth, buttonHeight);
        buyStomperButton.setTranslateX(buyButtonXPosition);
        buyStomperButton.setTranslateY(stomperImageYPos + buyButtonYDisplacement);
        buyStomperButton.setOnAction(e -> {
            this.controller.buy(Items.STOMPER);
        });

        final Image profitBirdImage = new Image(
                getClass().getClassLoader().getResource("shop/profitbirdArt.png").toExternalForm());
        final ImageView profitBirdImageView = new ImageView(profitBirdImage);
        profitBirdImageView.setFitWidth(imageSize);
        profitBirdImageView.setFitHeight(imageSize);
        profitBirdImageView.setTranslateX(imageXPos);
        profitBirdImageView.setTranslateY(profitBirdImageYPos);

        final Button buyProfitBirdButton = new Button("BUY");
        buyProfitBirdButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyProfitBirdButton.setPrefSize(buttonWidth, buttonHeight);
        buyProfitBirdButton.setTranslateX(buyButtonXPosition);
        buyProfitBirdButton.setTranslateY(profitBirdImageYPos + buyButtonYDisplacement);
        buyProfitBirdButton.setOnAction(e -> {
            this.controller.buy(Items.PROFITBIRD);
        });

        shieldNum = new Text("22");
        shieldNum.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        shieldNum.setFill(Color.WHITE);
        shieldNum.setTranslateX(shieldNumPosX);
        shieldNum.setTranslateY(shieldImageYPos + buyButtonYDisplacement);

        final Image shieldImage = new Image(
                getClass().getClassLoader().getResource("shop/shield.png").toExternalForm());
        final ImageView shieldImageView = new ImageView(shieldImage);
        shieldImageView.setFitWidth(imageSize);
        shieldImageView.setFitHeight(imageSize);
        shieldImageView.setTranslateX(imageXPos);
        shieldImageView.setTranslateY(shieldImageYPos);

        final Button buyShieldButton = new Button("BUY");
        buyShieldButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16;");
        buyShieldButton.setPrefSize(buttonWidth, buttonHeight);
        buyShieldButton.setTranslateX(buyButtonXPosition);
        buyShieldButton.setTranslateY(shieldImageYPos + buyButtonYDisplacement);
        buyShieldButton.setOnAction(e -> {
            this.controller.buy(Items.SHIELD);
        });

        final Button equipShield = new Button("EQUIP");
        equipShield.setStyle("-fx-background-color: #0010e8; -fx-text-fill: white; -fx-font-size: 16;");
        equipShield.setPrefSize(buttonWidth, buttonHeight);
        equipShield.setTranslateX(buyButtonXPosition + buttonWidth + imageDistance);
        equipShield.setTranslateY(shieldImageYPos + buyButtonYDisplacement);
        equipShield.setOnAction(e -> {
            this.controller.equip(Items.SHIELD);
        });

        moneyText = new Text();
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(buttonHeight);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        displayEquipped = new Text();
        displayEquipped.setFont(Font.font("Arial", FontWeight.BOLD, buttonHeight));
        displayEquipped.setFill(Color.WHITE);
        displayEquipped.setTranslateY(200);
        displayEquipped.setTextAlignment(TextAlignment.RIGHT);
        displayEquipped.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        final Text descriptionText1 = new Text("Mr Snuggles\n Too cool not to buy, be serious...");
        descriptionText1.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText1.setFill(Color.WHITE);
        descriptionText1.setTranslateX(textPosX);
        descriptionText1.setTranslateY(cuddleImageYPos + imageSize / 2);

        final Text descriptionText2 = new Text("Lil Stomper\n Clumsy but robust vehicle");
        descriptionText2.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText2.setFill(Color.WHITE);
        descriptionText2.setTranslateX(textPosX);
        descriptionText2.setTranslateY(stomperImageYPos + imageSize / 2);

        final Text descriptionText3 = new Text("Profit Bird\n Flappy bird -like vehicle");
        descriptionText3.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText3.setFill(Color.WHITE);
        descriptionText3.setTranslateX(textPosX);
        descriptionText3.setTranslateY(profitBirdImageYPos + imageSize / 2);

        final Text descriptionText4 = new Text("Shield (Consumable\n A shield that can be equipped");
        descriptionText4.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText4.setFill(Color.WHITE);
        descriptionText4.setTranslateX(textPosX);
        descriptionText4.setTranslateY(shieldImageYPos + imageSize / 2);

        root.getChildren().addAll(
                backgroundImageView,
                backButton,
                cuddlesImageView,
                buyMrCuddlesButton,

                stomperImageView,
                buyStomperButton,

                profitBirdImageView,
                buyProfitBirdButton,

                shieldImageView,
                buyShieldButton,
                equipShield,
                moneyText,
                descriptionText1,
                descriptionText2,
                descriptionText3,
                descriptionText4,
                displayEquipped,
                shieldNum);
        this.update();
    }

    /**
     * Gets the scene of the shop menu.
     * 
     * @return The scene of the shop menu.
     */
    public Scene getScene() {
        return shopScene;
    }

    public void update() {
        this.moneyText.setText("Money: $" + this.controller.retrieveBalance());
        this.displayEquipped.setText(
                this.controller.getEquipped().isEmpty() ? "Nothing" : this.controller.getEquipped().get().toString());
    }

}
