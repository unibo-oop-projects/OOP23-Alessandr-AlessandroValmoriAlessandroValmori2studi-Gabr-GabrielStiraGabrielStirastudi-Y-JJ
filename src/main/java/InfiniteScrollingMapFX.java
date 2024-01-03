

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.image.ImageView;


public class InfiniteScrollingMapFX extends Application {

    protected static final int MAP_WIDTH = 1200;
    protected static final int MAP_HEIGHT = 800;
    private static final int SCROLL_SPEED = 2;

    private Pane root;
    private Image backgroundImage;
    private ImageView bgImageView1, bgImageView2;
    private double bgImageX1, bgImageX2;


    private List<ObstacleController> obstacles;

    @Override
    public void start(Stage stage) {
        root = new Pane();
        Scene scene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);

        backgroundImage = new Image("file:C:/Users/zyk10/Downloads/UseForJetPackJ/background1.png");
        bgImageView1 = new ImageView(backgroundImage);
        bgImageView2 = new ImageView(backgroundImage);
        bgImageView1.setFitWidth(MAP_WIDTH);
        bgImageView1.setFitHeight(MAP_HEIGHT);
        bgImageView2.setFitWidth(MAP_WIDTH);
        bgImageView2.setFitHeight(MAP_HEIGHT);

        bgImageX1 = 0;
        bgImageX2 = MAP_WIDTH;

        root.getChildren().addAll(bgImageView1, bgImageView2);

        generateInitialElements();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBackground();
                for (ObstacleController obstacle : obstacles) {
                    obstacle.update();
                }
            }
        }.start();

        stage.setTitle("Infinite Scrolling Map");
        stage.setScene(scene);
        stage.show();
    }

    private void generateInitialElements() {
      
        Random random = new Random();
       
        int numberOfMissiles = 7; 
        int imagesLength = 49;
        int index=0;
        Image[] images = new Image[imagesLength];
        for (int i = 0; i < numberOfMissiles; i++) {
            for(int j=0; j < numberOfMissiles; j++ ) {
                String imagePath = "file:resources/sprites/entities/obstacles/missile/missile_" + (i + 1) + ".png";
                images[index] = new Image(imagePath);    
                index++;
            }
        }

        

        int numberOfWarnings = 2; 
        Image[] warnings = new Image[numberOfWarnings];
        for (int i = 0; i < numberOfWarnings; i++) { 
            String warningPath = "file:resources/sprites/entities/obstacles/missile/missile_warnings/missile_warning_" + (i + 1) + ".png";
            warnings[i] = new Image(warningPath);
         
        }

        obstacles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double width = 75;
            double height = 45;
            double x = MAP_WIDTH; 
            double y = new Random().nextDouble() * (MAP_HEIGHT - height);

        
            ObstacleModel model = new ObstacleModel(x, y,width,height);
            ObstacleView view = new ObstacleView(images, warnings);

         
            ObstacleController obstacle = new ObstacleController(model, view);
            obstacles.add(obstacle);

            root.getChildren().add(obstacle.getImageView());
        }
    }

    private void updateBackground() {
        bgImageX1 -= SCROLL_SPEED;
        bgImageX2 -= SCROLL_SPEED;
    
        if (bgImageX1 <= -MAP_WIDTH) {
            bgImageX1 = bgImageX2 + MAP_WIDTH;
        }
        if (bgImageX2 <= -MAP_WIDTH) {
            bgImageX2 = bgImageX1 + MAP_WIDTH;
        }
    
        bgImageView1.setX(bgImageX1);
        bgImageView2.setX(bgImageX2);
    }
    
}
