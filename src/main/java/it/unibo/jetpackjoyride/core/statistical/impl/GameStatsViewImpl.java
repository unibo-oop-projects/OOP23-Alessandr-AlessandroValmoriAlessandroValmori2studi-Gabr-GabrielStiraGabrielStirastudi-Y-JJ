package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

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
public final class GameStatsViewImpl implements GameStatsView {

    private static final int CURRENT_DISTANCE = 0;
    private static final int BEST_DISTANCE = 1;
    private static final int TOT_COIN = 2;

    private static final String SCORE_IMAGE1_PATH = "background/ScorePane.png";
    private static final double SCORE_PANE_X = 0;
    private static final double SCORE_PANE_Y = 0;
    private static final double SCORE_PANE_WIDTH = 180;
    private static final double SCORE_PANE_HEIGHT = 72;
    private static final double TEXT_X = 25;
    private static final double TEXT_Y = 23;
    private static final double FONT_SIZE = 15;

    private final Text coinAndDistanceText = new Text();
    private final ImageView scorePane;

     /**
     * Constructs a new GameStatsViewImpl object.
     */
    public GameStatsViewImpl() {
        scorePane = creatImageView(SCORE_IMAGE1_PATH);
        scorePane.setX(SCORE_PANE_X);
        scorePane.setY(SCORE_PANE_Y);
        scorePane.setFitWidth(SCORE_PANE_WIDTH);
        scorePane.setFitHeight(SCORE_PANE_HEIGHT);
        coinAndDistanceText.setX(TEXT_X);
        coinAndDistanceText.setY(TEXT_Y);
        coinAndDistanceText.setFill(Color.SILVER);
        coinAndDistanceText.setFont(Font.font("Serif",  FONT_SIZE)); 

    }

    @Override
    public void updateDataView(List<Integer> data) {
        coinAndDistanceText.setText("Current meter: " 
                                    + data.get(CURRENT_DISTANCE)
                                    + "\n" 
                                    + "Last best meter : " 
                                    + data.get(CURRENT_DISTANCE) 
                                    + "\n" 
                                    + "Total Coins: " 
                                    + data.get(TOT_COIN));
    }

    @Override
    public Text getText() {
        return this.coinAndDistanceText;
    }

    @Override
    public ImageView getImageView() {
        return this.scorePane;
    }

    /**
     * A method that used to create ImageView.
     * @param path The path to the image
     * @return return a ImageView with Image or not
     */
    private ImageView creatImageView(final String path) {

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
