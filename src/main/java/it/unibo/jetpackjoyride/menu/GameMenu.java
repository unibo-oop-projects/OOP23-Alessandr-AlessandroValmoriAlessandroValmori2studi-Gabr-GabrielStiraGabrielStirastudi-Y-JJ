package it.unibo.jetpackjoyride.menu;

import it.unibo.jetpackjoyride.core.GameLoop;
import it.unibo.jetpackjoyride.menu.shop.impl.ShopControllerImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class GameMenu {
    private static final int MAP_WIDTH = 800;
    private static final int MAP_HEIGHT = 600;
    private static final int PORTION = 4;
    private static final int BUTTON_POS_X = 350;
    private static final int BUTTON_POS_Y = 430;
    private static final int BUTTON_SPACE = 40;

    private Scene menuScene;
    private Stage mainStage;
    private Pane root;
    private Image menuImage;
    private ImageView menuImageView;
    private GameLoop gameLoop;
    

    private ShopControllerImpl shopController;

    
    private GameInfo gameInfo;

    public GameMenu(final Stage primaryStage) {
        this.gameLoop = new GameLoop();
        gameInfo = GameInfo.getInstance();
        mainStage = primaryStage;
        initializeGameMenu();
        addButtons();
        shopController = new ShopControllerImpl(mainStage, this);
        mainStage.setOnCloseRequest(event -> {
            if (gameLoop != null) {
                gameLoop.endLoop(); 
            }
            Platform.exit(); 
            System.exit(0);
        });
    }

    public Scene getScene() {
        return this.menuScene;
    }

    private void initializeGameMenu() {
        try {
            String menuImgUrl = getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm();
            if (menuImgUrl != null) {
                menuImage = new Image(menuImgUrl);
                menuImageView = new ImageView(menuImage);
                menuImageView.setFitHeight(MAP_HEIGHT);
                menuImageView.setFitWidth(MAP_WIDTH);

                root = new Pane(menuImageView);
                menuScene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);

                this.mainStage.setScene(menuScene);
            } else {
                System.err.println("GameMenu Image was not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addButtons() {
        final Button startButton = createButton("Start Game", 0, e -> {
            mainStage.setScene(gameLoop.getScene());
            setGameStagePosition();
            gameLoop.starLoop();
            

        });
        final Button shopButton = createButton("Shop", 1, e -> {
            mainStage.setScene(shopController.getScene());
            setGameStagePosition();
        });
        root.getChildren().addAll(startButton, shopButton);
    }

    private Button createButton(final String name, final int index, final EventHandler<ActionEvent> action) {
        final Button button = new Button(name);
        button.setOnAction(action);
        button.setTranslateX(BUTTON_POS_X);
        button.setTranslateY(BUTTON_POS_Y + (BUTTON_SPACE * index));
        return button;
    }

    private void setGameStagePosition() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double sw = screenSize.getWidth();
        final double sh = screenSize.getHeight();
        final double mapw = gameInfo.getScreenWidth();
        final double maph = gameInfo.getScreenHeight();

        final double stageX = (sw - mapw) / (PORTION / 2);
        final double stageY = (sh - maph) / PORTION;

        mainStage.setX(stageX);
        mainStage.setY(stageY);
    }

}
