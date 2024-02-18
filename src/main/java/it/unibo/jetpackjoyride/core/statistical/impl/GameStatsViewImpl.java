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

/**
 * An implementation of the GameStatsView interface.
 * @author yukai.zhou@studio.unibo.it
 */
public class GameStatsViewImpl implements GameStatsView{

    private final String SCORE_IMAGE1_PATH = "background/ScorePane.png";
    
    private final Text coinAndDistanceText = new Text();
    private final ImageView scorePane;

     /**
     * Constructs a new GameStatsViewImpl object.
     */
    public GameStatsViewImpl(){
        scorePane = creatImageView(SCORE_IMAGE1_PATH);
        scorePane.setX(0);
        scorePane.setY(0);
        scorePane.setFitWidth(180);
        scorePane.setFitHeight(72);
        coinAndDistanceText.setX(25);
        coinAndDistanceText.setY(23);
        coinAndDistanceText.setFill(Color.SILVER);
        coinAndDistanceText.setFont(Font.font("Serif", 15)); 
       
    }
    @Override
    public void updateDataView(GameStatsModel model){
        coinAndDistanceText.setText("Current meter: " + model.getcurrentDistance() + 
                                    "\n" +
                                    "Last best meter : " + model.getBestDistance() +
                                    "\n" + "Total Coins: " + model.getTotCoins()  );
    }

    @Override
    public Text getText(){
        return this.coinAndDistanceText;
    }

    @Override
    public ImageView getImageView(){
        return this.scorePane;
    }

  /**
     * A method that used to create ImageView
     * @param path The path to the image
     */
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
