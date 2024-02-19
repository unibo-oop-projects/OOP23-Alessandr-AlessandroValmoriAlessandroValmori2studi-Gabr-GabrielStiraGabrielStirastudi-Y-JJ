package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;

import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.menus.GameMenu;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import java.util.HashMap;
import static it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

/**
 * The view class for the shop menu.
 */
public class ShopView extends GameMenu {
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
    private final int cuddleImageYPos=(int)GameInfo.getInstance().getScreenHeight()/8;

    // Other constants

    private final ShopController controller;
    private final Text moneyText;
    private final Text displayEquipped;
    private final Text dukeUnlocked;
    private Text shieldNum;

    private Button equipShieldButton = new Button();

    private Map<Button, Text> descriptionsMap = new HashMap<>();
    private Map<Button, Items> buttonMap = new HashMap<>();
    private Map<Button, ImageView> imageMap = new HashMap<>();

    /**
     * Constructor for ShopView.
     */
    public ShopView(final ShopController controller, Stage primaryStage, GameStatsController gameStatsHandler) {
        super(primaryStage, gameStatsHandler);

      
        this.controller = controller;

        scene.setOnKeyPressed(ev -> {
            this.controller.type(ev.getCode());
            this.update();
        });
        initializeGameMenu();
        setGameStagePosition();
        stageCloseAction();

        // initializes the button map e la description map
        for (var entry : Items.values()) {
            if (!entry.equals(Items.DUKE)) {
                this.buttonMap.put(new Button(), entry);
            }
        }

        // inizializza la imageMap e il description set
        for (var entry : buttonMap.entrySet()) {
            this.descriptionsMap.put(entry.getKey(), new Text(entry.getValue().getDescription().get()));
            this.imageMap.put(entry.getKey(), new ImageView(new Image(
                    getClass().getClassLoader().getResource("shop/shop" + entry.getValue().name() + ".png")
                            .toExternalForm())));

        }
        // posiziona la imageMap n
        for (var entry : imageMap.entrySet()) {

            entry.getValue().setFitWidth(imageSize);
            entry.getValue().setFitHeight(imageSize);
            entry.getValue().setTranslateX(imageXPos);
            entry.getValue().setTranslateY(
                    (this.buttonMap.get(entry.getKey()).getOrder().get()) * (this.imageSize + this.imageDistance) + this.cuddleImageYPos);

        }

        for (var entry : this.descriptionsMap.entrySet()) {
            entry.getValue().setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
            entry.getValue().setFill(Color.WHITE);
            entry.getValue().setTranslateX(textPosX);
            entry.getValue().setTranslateY(
                    (this.buttonMap.get(entry.getKey()).getOrder().get()) * (this.imageSize + this.imageDistance)
                            + this.cuddleImageYPos);
        }

        // posiziona la buttonMap
        for (var entry : buttonMap.entrySet()) {

            entry.getKey().setText(String.valueOf(entry.getValue().getItemCost()));
            entry.getKey().setStyle(buttonStyle);
            entry.getKey().setPrefWidth(buttonWidth);
            entry.getKey().setPrefHeight(buttonHeight);
            entry.getKey().setTranslateX(buyButtonXPosition);
            entry.getKey().setTranslateY(
                    entry.getValue().getOrder().get() * (this.imageSize + this.imageDistance) + buyButtonYDisplacement+ this.cuddleImageYPos);
            entry.getKey().setOnAction(e -> {
                this.controller.buy(entry.getValue());
            });
        }

        // inizializza e posiziona la description map 

        final Button backButton = ButtonFactory.createButton("menu", e -> this.controller.backToMenu(), buttonWidth * 2,
                30 * 2);
        ;
        backButton.setTranslateX(20);
        backButton.setTranslateY(20);

        
          shieldNum = new Text(String.valueOf(this.controller.getNumOfShields()));
          shieldNum.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
          shieldNum.setFill(Color.GREEN);
          shieldNum.setTranslateX(shieldNumPosX);
          shieldNum.setTranslateY(Items.SHIELD.getOrder().get() * (this.imageSize + this.imageDistance) + this.cuddleImageYPos);
          
          
          
          
          
          equipShieldButton.setText("EQUIP");
          equipShieldButton.setStyle(equipShieldStyle);
          equipShieldButton.setPrefSize(buttonWidth, buttonHeight);
          equipShieldButton.setTranslateX(buyButtonXPosition + buttonWidth +
          imageDistance);
          equipShieldButton.setTranslateY(Items.SHIELD.getOrder().get() * (this.imageSize + this.imageDistance)
          + this.cuddleImageYPos + buyButtonYDisplacement);
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
        displayEquipped.setTranslateY(this.cuddleImageYPos*3);
        displayEquipped.setTextAlignment(TextAlignment.RIGHT);
        displayEquipped.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        dukeUnlocked = new Text();
        dukeUnlocked.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        dukeUnlocked.setFill(Color.YELLOWGREEN);
        dukeUnlocked.setTranslateY(500);
        dukeUnlocked.setTextAlignment(TextAlignment.RIGHT);
        dukeUnlocked.setWrappingWidth(gameInfo.getScreenWidth() - buttonHeight);

        root.getChildren().addAll(this.imageMap.values());
        root.getChildren().addAll(this.descriptionsMap.values());
        root.getChildren().addAll(this.buttonMap.keySet());

        root.getChildren().addAll(

                backButton,

                equipShieldButton,
                moneyText,

                displayEquipped,

                dukeUnlocked,
        shieldNum
        );
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
        if (this.controller.getUnlocked().contains(Items.DUKE)) {
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
    protected void stageCloseAction() {
        stage.setOnCloseRequest(event -> {
            this.controller.save();
            defaultCloseAction();
        });
    }
}
