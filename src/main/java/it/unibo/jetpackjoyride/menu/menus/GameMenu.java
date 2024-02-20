package it.unibo.jetpackjoyride.menu.menus;


import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class representing a basic game menu extensible.
 * @author yukai.zhou@studio.unibo.it
 */
public abstract class GameMenu {
    /**
    * Default width for buttons within the game menu.
    */
    protected static final int DEFAULT_BUTTON_WIDTH = 150;

    /**
    * Default height for buttons within the game menu.
    */
    protected static final int DEFAULT_BUTTON_HEIGHT = 50;

    /**
    * The main scene for the game menu where all UI elements are placed.
    */
    protected final Scene scene;

    /**
    * The primary stage for the game menu, serving as the top-level JavaFX container.
    */
    private final Stage stage;

    /**
    * The root pane for the game menu layout, which holds all other UI elements.
    */
    private final StackPane root;

    /**
    * ImageView for displaying the menu's background image.
    */
    private ImageView menuImageView;

    /**
    * Utility for accessing game information such as screen dimensions.
    */
    protected  GameInfo gameInfo = GameInfo.getInstance();

    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> heightListener;
    private final GameStatsController gameStatsController;

   /**
     * Constructs a new game menu.
     *
     * @param primaryStage      the primary stage
     * @param gameStatsController the game statistics controller
     */
    public GameMenu(final Stage primaryStage, final GameStatsController gameStatsController) {
         this.stage = primaryStage;
         this.root = new StackPane();
         this.scene = new Scene(root, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
         this.gameStatsController = gameStatsController;
    }

    /**
     * A method to show the game menu on Screen.
     */
    public void showMenu() {
         this.stage.setScene(scene);
         this.stage.centerOnScreen();
    }

    /**
     * Removes the listeners attached to the scene.
     */
    public void removeListener() {
        if (scene != null && widthListener != null) {
            scene.widthProperty().removeListener(widthListener);
        }
        if (scene != null && heightListener != null) {
            scene.heightProperty().removeListener(heightListener);
        }
    }

    /**
     * A method to get the game statistics controller.
     *
     * @return the game statistics controller
     */
    public GameStatsController getGameStatsHandler() {
        return this.gameStatsController;
    }

    /**
     * Gets the primary stage of the game menu.
     * 
     * @return The primary stage of the game menu.
     */
    public Stage getStage() {
        return stage;
    }

     /**
     * here can initializes specify funtion of the  menu.
     */
    protected abstract void initializeGameMenu();

    /**
     * Adds buttons to the game menu.
     *
     * @param buttons the buttons to add
     */
    protected void addButtons(final Node buttons) {
        this.root.getChildren().add(buttons);
    }

    /**
     * Sets the image for menu.
     *
     * @param menuImage the menu image
     */
    protected void setMenuImage(final Image menuImage) {
        if (menuImageView != null) {
            this.root.getChildren().remove(menuImageView);
        }
        menuImageView = new ImageView(menuImage);
        menuImageView.setFitWidth(gameInfo.getScreenWidth());
        menuImageView.setFitHeight(gameInfo.getScreenHeight());
        addSizeListener();
        root.getChildren().add(0, menuImageView);
    }

    /**
     * The method can define the action to perform when the stage is closed.
     */
    protected void stageCloseAction() {
        stage.setOnCloseRequest(event -> {
            defaultCloseAction();
        });
    }

    /**
     *  The default action when the stage is closed.
     */
    protected void defaultCloseAction() {
        Platform.exit(); 
        System.exit(0);
    }

    /**
     * adds the listeners to the scene.
     */
    protected void addSizeListener() {
        widthListener = (obs, oldvalue, newVal) -> {
            double ratioX = newVal.doubleValue() / oldvalue.doubleValue();
            menuImageView.setFitWidth(menuImageView.getFitWidth() * ratioX);
        };
        scene.widthProperty().addListener(widthListener);
        heightListener = (obs, oldvalue, newVal) -> {
            double ratioY = newVal.doubleValue() / oldvalue.doubleValue();
            menuImageView.setFitHeight(menuImageView.getFitHeight() * ratioY);
        };
        scene.heightProperty().addListener(heightListener);
    }
}
