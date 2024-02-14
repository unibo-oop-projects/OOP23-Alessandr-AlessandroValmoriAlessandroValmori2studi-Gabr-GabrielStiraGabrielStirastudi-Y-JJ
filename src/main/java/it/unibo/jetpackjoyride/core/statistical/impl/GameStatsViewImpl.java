package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStatsViewImpl implements GameStatsView{

    private final String SCORE_IMAGE1_PATH = "background/ScorePane.png";
    
    private final Text coinAndDistanceText = new Text();
    private final ImageView scorePane;

    public GameStatsViewImpl(){
        scorePane = creatImageView(SCORE_IMAGE1_PATH);
        scorePane.setX(0);
        scorePane.setY(0);
        scorePane.setFitWidth(180);
        scorePane.setFitHeight(72);
        coinAndDistanceText.setX(25);
        coinAndDistanceText.setY(22);
        coinAndDistanceText.setFill(Color.WHITE);
        coinAndDistanceText.setFont(Font.font("Serif", 15)); 
       
    }

    public void updateDateView(GameStatsModel model){
        coinAndDistanceText.setText("Current meter: " + model.getcurrentDistance() + 
                                    "\n" +
                                    "Last best meter : " + model.getTotCoins() +
                                    "\n" + "Total Coins: " + model.getBestDistance()  );
    }

    public Text getText(){
        return this.coinAndDistanceText;
    }

    public ImageView getImageView(){
        return this.scorePane;
    }


    private ImageView creatImageView(String path) {

        try {
            URL scoreImageUrl = getClass().getClassLoader().getResource(path);
            if (scoreImageUrl == null) {
                throw new FileNotFoundException("Backgroung Image was not found: " + path);
            }
            String url = scoreImageUrl.toExternalForm();
            Image scoreImage = new Image(url);
            ImageView scoreImageView = new ImageView(scoreImage);
            return scoreImageView;
        } catch (FileNotFoundException e) {
            System.err.println("Error message :" + e.getMessage());
            return new ImageView();
        }
    }
    
}
