package it.unibo.jetpackjoyride.menu.shop.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStats;
import it.unibo.jetpackjoyride.menu.buttoncommand.ButtonFactory;
import it.unibo.jetpackjoyride.menu.menus.impl.GameMenuImpl;
import it.unibo.jetpackjoyride.menu.shop.api.BackToMenuObs;
import it.unibo.jetpackjoyride.menu.shop.api.CharacterObs;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.menu.shop.api.ShopItemPurchaseObs;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static it.unibo.jetpackjoyride.menu.shop.api.ShopController.Items;

/**
 * The view class for the shop menu, it extends {@link GameMenuImpl},
 * because of some common functionalities, such as background rescaling.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 */
public final class ShopView extends GameMenuImpl {
    /** Constants related to image positioning . */
    private static final int IMAGE_X_POS = 50;
    private static final int IMAGE_SIZE = 110;
    private static final int IMAGE_DISTANCE = 30;

    /** Constants related to button positioning . */
    private static final int BUY_BUTTON_X_POS = 210;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;
    private static final int BUY_BUTTON_Y_DISPLACEMENT = (IMAGE_SIZE - BUTTON_HEIGHT) / 2;
    private static final int QUIT_BUTTON_WIDTH = 160;
    private static final int QUIT_BUTTON_HEIGHT = 60;
    private static final int TOP_RIGHT_QUIT_DIST = 20;
    private static final int MONEY_Y_POS = 160;
    private static final int DUKE_WARNING_Y_POS = 500;

    /** Constants related to text and font . */
    private static final int FONT_SIZE = TOP_RIGHT_QUIT_DIST;
    private static final int SHIELD_COUNTER_X_POS = BUY_BUTTON_X_POS + 2 * BUTTON_WIDTH + 2 * IMAGE_DISTANCE;
    private static final int DESCR_X_POS = SHIELD_COUNTER_X_POS + IMAGE_DISTANCE;
    private static final String BUTTON_STYLE = "-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 16;";
    private static final int MONEY_FONT = 40;

    /**
     * Y position of the mrcuddles image, used as a reference for the rest
     * of the components.
     */
    private final int cuddleImageYPos = (int) GameInfo.getInstance().getScreenHeight() / 8;

    /** The shop controller associated with this view class . */
    private final ShopController controller;
    /** The text section that displays the coin balance . */
    private final Text moneyText;
    /**
     * The text section that appears when the right password is typed
     * alerting that {@link DukeFishron} has been unlocked.
     */
    private final Text dukeUnlocked;
    /** The map that associates a button to a item description . */
    private final Map<Button, Text> descriptionsMap = new HashMap<>();
    /** The map that associates a button to its corresponding item . */
    private final Map<Button, Items> buttonMap = new HashMap<>();
    /** A map that associates a button to its corresponding Image . */
    private final Map<Button, ImageView> imageMap = new HashMap<>();
    /** The lists of the observers subscribed to this class. */
    private final List<ShopItemPurchaseObs> buyObsList = new ArrayList<>();
    private final List<BackToMenuObs> backList = new ArrayList<>();
    private final List<CharacterObs> charObsList = new ArrayList<>();
    /** The root of the scene .. */
    private final StackPane root = new StackPane();

    /**
     * Constructor for ShopView.
     * 
     * @param controller
     * @param primaryStage
     */
    public ShopView(final ShopController controller, final Stage primaryStage) {
        super(primaryStage);
        this.controller = controller;
        this.root.setFocusTraversable(true);
        this.root.setOnKeyPressed(ev -> {
            this.charObsList.forEach(obs -> obs.type(ev.getCode()));
            this.update();

        });
        initializeGameMenu(primaryStage);
        primaryStage.centerOnScreen();

        for (final var entry : Items.values()) {
            if (!entry.equals(Items.DUKE)) {
                this.buttonMap.put(new Button(), entry);
            }
        }

        for (final var entry : buttonMap.entrySet()) {
            this.descriptionsMap.put(entry.getKey(), new Text(entry.getValue().getDescription().get()));
            this.imageMap.put(entry.getKey(), new ImageView(new Image(
                    getClass().getClassLoader().getResource("shop/shop" + entry.getValue().name() + ".png")
                            .toExternalForm())));
        }

        for (final var entry : imageMap.entrySet()) {
            entry.getValue().setFitWidth(IMAGE_SIZE);
            entry.getValue().setFitHeight(IMAGE_SIZE);
            entry.getValue().setTranslateX(IMAGE_X_POS);
            entry.getValue().setTranslateY(
                    (this.buttonMap.get(entry.getKey()).getOrder().get()) * (IMAGE_SIZE + IMAGE_DISTANCE)
                            + this.cuddleImageYPos);
        }

        for (final var entry : this.descriptionsMap.entrySet()) {
            entry.getValue().setFont(Font.font("Arial", FontWeight.NORMAL, FONT_SIZE));
            entry.getValue().setFill(Color.WHITE);
            entry.getValue().setTranslateX(DESCR_X_POS);
            entry.getValue().setTranslateY(
                    (this.buttonMap.get(entry.getKey()).getOrder().get()) * (IMAGE_SIZE + IMAGE_DISTANCE)
                            + this.cuddleImageYPos);
        }

        for (final var entry : buttonMap.entrySet()) {

            entry.getKey().setText(String.valueOf(entry.getValue().getItemCost()));
            entry.getKey().setStyle(BUTTON_STYLE);
            entry.getKey().setPrefWidth(BUTTON_WIDTH);
            entry.getKey().setPrefHeight(BUTTON_HEIGHT);
            entry.getKey().setTranslateX(BUY_BUTTON_X_POS);
            entry.getKey().setTranslateY(
                    entry.getValue().getOrder().get() * (IMAGE_SIZE + IMAGE_DISTANCE)
                            + BUY_BUTTON_Y_DISPLACEMENT + this.cuddleImageYPos);
            entry.getKey().setOnAction(e -> {
                this.buyObsList.forEach(obs -> obs.onItemBought(entry.getValue()));
                this.update();
            });
        }

        final Button backButton = ButtonFactory.createButton("menu", e -> this.backList.forEach(obs -> obs.goBack()),
                QUIT_BUTTON_WIDTH,
                QUIT_BUTTON_HEIGHT);

        backButton.setTranslateX(TOP_RIGHT_QUIT_DIST);
        backButton.setTranslateY(TOP_RIGHT_QUIT_DIST);

        moneyText = new Text();
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, MONEY_FONT));
        moneyText.setFill(Color.WHITE);
        moneyText.setTranslateY(MONEY_Y_POS);
        moneyText.setTextAlignment(TextAlignment.RIGHT);
        moneyText.setWrappingWidth(GameInfo.getInstance().getScreenWidth() - BUTTON_HEIGHT);

        dukeUnlocked = new Text();
        dukeUnlocked.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        dukeUnlocked.setFill(Color.YELLOWGREEN);
        dukeUnlocked.setTranslateY(DUKE_WARNING_Y_POS);
        dukeUnlocked.setTextAlignment(TextAlignment.RIGHT);
        dukeUnlocked.setWrappingWidth(GameInfo.getInstance().getScreenWidth() - BUTTON_HEIGHT);

        root.getChildren().addAll(this.imageMap.values());
        root.getChildren().addAll(this.descriptionsMap.values());
        root.getChildren().addAll(this.buttonMap.keySet());
        root.getChildren().addAll(
                backButton,
                moneyText,
                dukeUnlocked);
        this.update();
    }

    /**
     * Sets the scene of the shop menu on Stage.
     */
    public void setSceneOnStage() {
        this.showMenu();
    }

    /**
     * Subscriber method for {@link ShopItemPurchaseObs} observers.
     * @param observer the observer
     */
    public void addBuyObs(final ShopItemPurchaseObs observer) {
        buyObsList.add(observer);
    }

    /**
     * Subscriber method for {@link BackToMenu} observers.
     * @param observer the observer
     */
    public void addBackToMenuObs(final BackToMenuObs observer) {
        backList.add(observer);
    }

    /**
     * Subscriber method for {@link CharacterObs} observers.
     * @param observer the observer
     */
    public void addCharObs(final CharacterObs observer) {
        charObsList.add(observer);
    }

    /**
     * Unsubscriber method for {@link BackToMenuObs} observers.
     * @param observer the observer
     */
    public void removeBackToMenuObs(final BackToMenuObs observer) {
        backList.remove(observer);
    }

    /**
     * Unsubscriber method for {@link ShopItemPurchaseObs} observers.
     * @param observer the observer
     */
    public void removeBuyObs(final ShopItemPurchaseObs observer) {
        buyObsList.remove(observer);
    }

    /**
     * Unsubscriber method for {@link CharacterObs} observers.
     * @param observer the observer
     */
    public void removeCharObs(final CharacterObs observer) {
        charObsList.remove(observer);
    }

    /** The graphical update method of this view class . */
    private void update() {

        this.moneyText.setText("Money: $" + GameStats.getCoins());

        if (controller.getUnlocked().contains(Items.DUKE)) {
            this.dukeUnlocked.setText("DUKE UNLOCKED ! ! !");
        }
        for (final var entry : this.buttonMap.entrySet()) {
            if (controller.getUnlocked().contains(entry.getValue())) {

                final Image image = new Image(
                        getClass().getClassLoader().getResource("buttons/tick.png").toExternalForm());
                final ImageView imageView = new ImageView(image);
                imageView.setFitWidth(BUTTON_WIDTH);
                imageView.setFitHeight(BUTTON_HEIGHT);
                imageView.setPreserveRatio(false);
                entry.getKey().setStyle(
                        "-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                entry.getKey().setGraphic(imageView);
            }
        }
    }

    @Override
    protected void initializeGameMenu(final Stage primaryStage) {
        root.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        final Image menuImage = new Image(getClass().getClassLoader().getResource("shop/shopbg.png").toExternalForm());
        setMenuImage(menuImage);
        addButtons(root);
    }
}
