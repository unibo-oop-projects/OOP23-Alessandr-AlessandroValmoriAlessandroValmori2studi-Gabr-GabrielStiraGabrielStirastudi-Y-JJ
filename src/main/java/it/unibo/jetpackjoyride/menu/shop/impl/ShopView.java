package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.menu.buttonCommand.ButtonFactory;
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
    private final int imageSizeHalf = imageSize/2;
    private final int imageDistance = 30;

    // Constants related to button positioning
    private final int buyButtonXPosition = 210;
    private final int buttonWidth = 80;
    private final int buttonHeight = 80;
    private final int buyButtonYDisplacement = (imageSize-buttonHeight)/2;

    // Constants related to text and font
    private final int fontSize = 20;
    private final int shieldNumPosX = buyButtonXPosition + 2 * buttonWidth + 2 * imageDistance;
    private final int textPosX = shieldNumPosX + imageDistance;
    private final String buttonStyle = "-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 16;";
    private final String equipShieldStyle = "-fx-background-color: #0000ff; -fx-text-fill: white; -fx-font-size: 16;";


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

        final Button backButton = ButtonFactory.createButton("menu", e -> this.controller.backToMenu(),buttonWidth*2 ,30*2 );
        ;
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);
        
        final Image cuddlesImage = new Image(
                getClass().getClassLoader().getResource("shop/cuddleart.png").toExternalForm());
        final ImageView cuddlesImageView = new ImageView(cuddlesImage);
        cuddlesImageView.setFitWidth(imageSize);
        cuddlesImageView.setFitHeight(imageSize);
        cuddlesImageView.setTranslateX(imageXPos);
        cuddlesImageView.setTranslateY(cuddleImageYPos);

        final Button buyMrCuddlesButton = new Button(String.valueOf(Items.MRCUDDLES.getItemCost()));
        buyMrCuddlesButton.setStyle(buttonStyle);
        buyMrCuddlesButton.setPrefSize(buttonWidth, buttonHeight);
        buyMrCuddlesButton.setTranslateX(buyButtonXPosition);
        buyMrCuddlesButton.setTranslateY(cuddleImageYPos  + buyButtonYDisplacement);
        buyMrCuddlesButton.setOnAction(e -> {
            this.controller.buy(Items.MRCUDDLES);
            System.out.println("cuddle");
        });

        final Image stomperImage = new Image(
                getClass().getClassLoader().getResource("shop/lilstomper.png").toExternalForm());
        final ImageView stomperImageView = new ImageView(stomperImage);
        stomperImageView.setFitWidth(imageSize);
        stomperImageView.setFitHeight(imageSize);
        stomperImageView.setTranslateX(imageXPos);
        stomperImageView.setTranslateY(stomperImageYPos);

        final Button buyStomperButton = new Button(String.valueOf(Items.STOMPER.getItemCost()));
        buyStomperButton.setStyle(buttonStyle);
        buyStomperButton.setPrefSize(buttonWidth, buttonHeight);
        buyStomperButton.setTranslateX(buyButtonXPosition);
        buyStomperButton.setTranslateY(stomperImageYPos   + buyButtonYDisplacement);
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

        final Button buyProfitBirdButton = new Button(String.valueOf(Items.PROFITBIRD.getItemCost()));
        buyProfitBirdButton.setStyle(buttonStyle);
        buyProfitBirdButton.setPrefSize(buttonWidth, buttonHeight);
        buyProfitBirdButton.setTranslateX(buyButtonXPosition);
        buyProfitBirdButton.setTranslateY(profitBirdImageYPos + buyButtonYDisplacement);
        buyProfitBirdButton.setOnAction(e -> {
            this.controller.buy(Items.PROFITBIRD);
        });

        shieldNum = new Text(String.valueOf(this.controller.getNumOfShields()));
        shieldNum.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        shieldNum.setFill(Color.GREEN);
        shieldNum.setTranslateX(shieldNumPosX);
        shieldNum.setTranslateY(shieldImageYPos + buyButtonYDisplacement);

        

        final Image shieldImage = new Image(
                getClass().getClassLoader().getResource("shop/shield.png").toExternalForm());
        final ImageView shieldImageView = new ImageView(shieldImage);
        shieldImageView.setFitWidth(imageSize);
        shieldImageView.setFitHeight(imageSize);
        shieldImageView.setTranslateX(imageXPos);
        shieldImageView.setTranslateY(shieldImageYPos);

        final Button buyShieldButton = new Button(String.valueOf(Items.SHIELD.getItemCost()));
        buyShieldButton.setStyle(buttonStyle);
        buyShieldButton.setPrefSize(buttonWidth, buttonHeight);
        buyShieldButton.setTranslateX(buyButtonXPosition);
        buyShieldButton.setTranslateY(shieldImageYPos + buyButtonYDisplacement);
        buyShieldButton.setOnAction(e -> {
            this.controller.buy(Items.SHIELD);
        });

        final Button equipShield = new Button("EQUIP");
        equipShield.setStyle(equipShieldStyle);
        equipShield.setPrefSize(buttonWidth, buttonHeight);
        equipShield.setTranslateX(buyButtonXPosition + buttonWidth + imageDistance);
        equipShield.setTranslateY(shieldImageYPos  + buyButtonYDisplacement);
        equipShield.setOnAction(e -> {
            this.controller.toggleEquipUnequipShield();
            System.out.println("pressed equip");
        });

        moneyText = new Text();
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(buttonHeight*2);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        displayEquipped = new Text();
        displayEquipped.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        displayEquipped.setFill(Color.WHITE);
        displayEquipped.setTranslateY(200);
        displayEquipped.setTextAlignment(TextAlignment.RIGHT);
        displayEquipped.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        final Text descriptionText1 = new Text("Mr Snuggles\n Too cool not to buy, be serious...");
        descriptionText1.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText1.setFill(Color.WHITE);
        descriptionText1.setTranslateX(textPosX);
        descriptionText1.setTranslateY(cuddleImageYPos + imageSizeHalf);

        final Text descriptionText2 = new Text("Lil Stomper\n Clumsy but robust vehicle");
        descriptionText2.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText2.setFill(Color.WHITE);
        descriptionText2.setTranslateX(textPosX);
        descriptionText2.setTranslateY(stomperImageYPos + imageSizeHalf);

        final Text descriptionText3 = new Text("Profit Bird\n Flappy bird -like vehicle");
        descriptionText3.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText3.setFill(Color.WHITE);
        descriptionText3.setTranslateX(textPosX);
        descriptionText3.setTranslateY(profitBirdImageYPos + imageSizeHalf);

        final Text descriptionText4 = new Text("Shield (Consumable\n A shield that can be equipped");
        descriptionText4.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        descriptionText4.setFill(Color.WHITE);
        descriptionText4.setTranslateX(textPosX);
        descriptionText4.setTranslateY(shieldImageYPos + imageSizeHalf);

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
        this.shieldNum.setText(String.valueOf(this.controller.getNumOfShields()));
        if(this.controller.getNumOfShields() == 0){
            this.shieldNum.setFill(Color.RED);
        }
        else{
            this.shieldNum.setFill(Color.GREEN);
        }
        this.displayEquipped.setText(
                this.controller.isShieldEquipped() ? "SHIELD EQUIPPED" : " ");
        
    }

}
