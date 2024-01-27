package it.unibo.jetpackjoyride.menu;

import it.unibo.jetpackjoyride.core.GameLoop;
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

    private Scene menuScene;
    private Stage mainStage;
    private Pane root;
    private Button startButton;
    private Button shopButton;
    private Button exitButton;
    private Image menuImage;
    private ImageView menuImageView;

    public GameMenu(Stage primaryStage, GameLoop gameLoop){
        String menuImgUrl = getClass().getClassLoader().getResource("menuImg/menuimg.png").toExternalForm();
        menuImage = new Image(menuImgUrl);
        menuImageView = new ImageView(menuImage);
        menuImageView.setFitHeight(MAP_HEIGHT);
        menuImageView.setFitWidth(MAP_WIDTH);
        
        mainStage = primaryStage;
         startButton = new Button("Start Game");
         startButton.setOnAction(e -> {
            mainStage.setScene(gameLoop.getScene());

            final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            final int sw = (int) screen.getWidth();
            final int sh = (int) screen.getHeight();
            mainStage.setX(sw/7);
            mainStage.setY(sh/18);
        });
         
        shopButton = new Button("Shop");
         shopButton.setOnAction(e -> {
            System.out.println("Shop not exist");
        });

         exitButton = new Button("Exit");
         exitButton.setOnAction(e -> {
            System.exit(0);
        });

        startButton.setTranslateX(350); 
        startButton.setTranslateY(430); 

        shopButton.setTranslateX(350);
        shopButton.setTranslateY(470);

        exitButton.setTranslateX(350);
        exitButton.setTranslateY(510);


         root = new Pane();
         root.getChildren().add(menuImageView);
         root.getChildren().addAll(startButton,shopButton,exitButton);
         menuScene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);
          
    }

    public Scene getScene(){
        return this.menuScene;
    }


}