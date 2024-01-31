package it.unibo.jetpackjoyride.menu;

import it.unibo.jetpackjoyride.core.GameLoop;
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


public class GameMenu {
    private static final int MAP_WIDTH = 800; 
    private static final int MAP_HEIGHT = 600;
    private static final int PORTION = 4;
    private static final int BUTTON_POS_X = 350;
    private static final int BUTTON_POS_Y  = 430;
    private static final int BUTTON_SPACE = 40;


    private Scene menuScene;
    private Stage mainStage;
    private Pane root;
    private Image menuImage;
    private ImageView menuImageView;
   

    private GameLoop gameLoop;

    public GameMenu(Stage primaryStage, GameLoop gameLoop){
        this.gameLoop = gameLoop;
        mainStage = primaryStage;
        initializeGameMenu();
        addButtons();
          
    }

    public Scene getScene(){
        return this.menuScene;
    }


    private void initializeGameMenu(){
        String menuImgUrl = getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm();
        menuImage = new Image(menuImgUrl);
        menuImageView = new ImageView(menuImage);
        menuImageView.setFitHeight(MAP_HEIGHT);
        menuImageView.setFitWidth(MAP_WIDTH);

        root = new Pane(menuImageView);
        menuScene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);

        this.mainStage.setScene(menuScene);
    }

    private void addButtons(){
         Button startButton = createButton("Start Game", 0, e -> {
            mainStage.setScene(gameLoop.getScene());
            setGameStagePosition(); 
            gameLoop.starLoop();
            mainStage.setOnCloseRequest(event -> {
              gameLoop.endLoop();
            });
        
        });
         Button shopButton = createButton("Shop", 1, e -> {
            System.out.println("Shop not exist");
        });
         Button exitButton = createButton("Exit", 2, e -> {
            System.exit(0);
        });
        root.getChildren().addAll(startButton,shopButton,exitButton);
    }

    private Button createButton(String name, int index, EventHandler<ActionEvent> action){
        Button button = new Button(name);
        button.setOnAction(action);
        button.setTranslateX(BUTTON_POS_X);
        button.setTranslateY(BUTTON_POS_Y + (BUTTON_SPACE * index));
        return button;
    }

    private void setGameStagePosition(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sw = screenSize.getWidth();
        double sh = screenSize.getHeight();
        mainStage.setX((sw - MAP_WIDTH) / PORTION);
        mainStage.setY((sh - MAP_HEIGHT) / PORTION);
    }

    private void startLoop(){
        gameLoop.starLoop();
        Thread gameThread = new Thread(gameLoop);
        gameThread.start();
    }

}