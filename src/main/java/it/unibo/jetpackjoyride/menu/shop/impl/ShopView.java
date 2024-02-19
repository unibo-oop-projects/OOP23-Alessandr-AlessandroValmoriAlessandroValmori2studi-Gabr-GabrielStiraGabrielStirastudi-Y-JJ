package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;
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
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import static it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

/**
 * The view class for the shop menu.
 */
public class ShopView extends GameMenu{
    // Constants related to image positioning
    private final int imageXPos = 50;
    private final int imageSize = 110;
    private final int imageSizeHalf = imageSize / 2;
    private final int imageDistance = 30;

    // Constants related to button positioning
    private final int buyButtonXPosition = 210;
    private final int buttonWidth = 80;
    private final int buttonHeight = 80;
    private final int buyButtonYDisplacement = (imageSize - buttonHeight) / 2;

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
    
   
    
    private final ShopController controller;
    private final Text moneyText;
    private final Text displayEquipped;
    private final Text dukeUnlocked;
    private Text shieldNum;

    private Button buyMrCuddlesButton = new Button();
    private Button buyStomperButton = new Button();
    private Button buyProfitBirdButton = new Button();
    private Button buyShieldButton = new Button();

    private Button equipShieldButton = new Button();

    private Map<Button, Items> buttonMap = new HashMap<>();

    /**
     * Constructor for ShopView.
     */
    public ShopView(final ShopController controller, Stage primaryStage, GameStatsController gameStatsHandler) {
        super(primaryStage, gameStatsHandler);
        
   
        this.controller = controller;
        //scene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        scene.setOnKeyPressed(ev -> {
            this.controller.type(ev.getCode());
            this.update();
        });
        initializeGameMenu();
        setGameStagePosition();
        stageCloseAction();

        buttonMap.put(buyMrCuddlesButton, Items.MRCUDDLES);
        buttonMap.put(buyStomperButton, Items.STOMPER);
        buttonMap.put(buyProfitBirdButton, Items.PROFITBIRD);
        buttonMap.put(buyShieldButton, Items.SHIELD);

        for (var entry : buttonMap.entrySet()) {
            entry.getKey().setText(String.valueOf(entry.getValue().getItemCost()));
            entry.getKey().setStyle(buttonStyle);
            entry.getKey().setPrefWidth(buttonWidth);
            entry.getKey().setPrefHeight(buttonHeight);
            entry.getKey().setOnAction(e -> {
                this.controller.buy(entry.getValue());
            });
        }

      

        final Button backButton = ButtonFactory.createButton("menu", e -> this.controller.backToMenu(), buttonWidth * 2,
                30 * 2);
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

        buyMrCuddlesButton.setTranslateX(buyButtonXPosition);
        buyMrCuddlesButton.setTranslateY(cuddleImageYPos + buyButtonYDisplacement);

        final Image stomperImage = new Image(
                getClass().getClassLoader().getResource("shop/lilstomper.png").toExternalForm());
        final ImageView stomperImageView = new ImageView(stomperImage);
        stomperImageView.setFitWidth(imageSize);
        stomperImageView.setFitHeight(imageSize);
        stomperImageView.setTranslateX(imageXPos);
        stomperImageView.setTranslateY(stomperImageYPos);

        buyStomperButton.setTranslateX(buyButtonXPosition);
        buyStomperButton.setTranslateY(stomperImageYPos + buyButtonYDisplacement);

        final Image profitBirdImage = new Image(
                getClass().getClassLoader().getResource("shop/profitbirdArt.png").toExternalForm());
        final ImageView profitBirdImageView = new ImageView(profitBirdImage);
        profitBirdImageView.setFitWidth(imageSize);
        profitBirdImageView.setFitHeight(imageSize);
        profitBirdImageView.setTranslateX(imageXPos);
        profitBirdImageView.setTranslateY(profitBirdImageYPos);

        buyProfitBirdButton.setTranslateX(buyButtonXPosition);
        buyProfitBirdButton.setTranslateY(profitBirdImageYPos + buyButtonYDisplacement);

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

        buyShieldButton.setTranslateX(buyButtonXPosition);
        buyShieldButton.setTranslateY(shieldImageYPos + buyButtonYDisplacement);

        equipShieldButton.setText("EQUIP");
        equipShieldButton.setStyle(equipShieldStyle);
        equipShieldButton.setPrefSize(buttonWidth, buttonHeight);
        equipShieldButton.setTranslateX(buyButtonXPosition + buttonWidth + imageDistance);
        equipShieldButton.setTranslateY(shieldImageYPos + buyButtonYDisplacement);
        equipShieldButton.setOnAction(e -> {
            this.controller.toggleEquipUnequipShield();

        });

        moneyText = new Text();
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(buttonHeight * 2);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        displayEquipped = new Text();
        displayEquipped.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        displayEquipped.setFill(Color.WHITE);
        displayEquipped.setTranslateY(200);
        displayEquipped.setTextAlignment(TextAlignment.RIGHT);
        displayEquipped.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        dukeUnlocked = new Text();
        dukeUnlocked.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        dukeUnlocked.setFill(Color.YELLOWGREEN);
        dukeUnlocked.setTranslateY(500);
        dukeUnlocked.setTextAlignment(TextAlignment.RIGHT);
        dukeUnlocked.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

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
                
                backButton,
                cuddlesImageView,
                buyMrCuddlesButton,

                stomperImageView,
                buyStomperButton,

                profitBirdImageView,
                buyProfitBirdButton,

                shieldImageView,
                buyShieldButton,
                equipShieldButton,
                moneyText,
                descriptionText1,
                descriptionText2,
                descriptionText3,
                descriptionText4,
                displayEquipped,
                dukeUnlocked,
                shieldNum);
        this.update();
    }

    /**
     * Gets the scene of the shop menu.
     * 
     * @return The scene of the shop menu.
     */
    public Scene getScene() {
        return scene;
    }

    public void update() {
        if(this.controller.getUnlocked().contains(Items.DUKE)){
            this.dukeUnlocked.setText("DUKE UNLOCKED ! ! !");
        }
        this.moneyText.setText("Money: $" + this.controller.retrieveBalance());
        this.shieldNum.setText(String.valueOf(this.controller.getNumOfShields()));
        if (this.controller.getNumOfShields() == 0) {
            this.shieldNum.setFill(Color.RED);
        } else {
            this.shieldNum.setFill(Color.GREEN);
        }
        this.displayEquipped.setText(
                this.controller.isShieldEquipped() ? "SHIELD EQUIPPED" : " ");

        for (var entry : this.buttonMap.entrySet()) {
            if (this.controller.getUnlocked().contains(entry.getValue())) {
                Image image = new Image(
                        getClass().getClassLoader().getResource("buttons/tick.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(buttonWidth);
                imageView.setFitHeight(buttonHeight);
                imageView.setPreserveRatio(false);
                entry.getKey().setStyle(
                        "-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                entry.getKey().setGraphic(imageView);
            }
        }

    }

    @Override
    protected void initializeGameMenu() {
        root.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        Image menuImage = new Image(getClass().getClassLoader().getResource("shop/shopbg.png").toExternalForm());
        setMenuImage(menuImage);
    }

    @Override
    protected void stageCloseAction(){
        stage.setOnCloseRequest(event -> {
            this.controller.save();
            defaultCloseAction();
        });
    }

    

}
