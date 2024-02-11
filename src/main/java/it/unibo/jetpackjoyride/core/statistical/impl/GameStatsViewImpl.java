package it.unibo.jetpackjoyride.core.statistical.impl;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStatsViewImpl implements GameStatsView{
    
    private Text coinAndDistanceText = new Text();

    public GameStatsViewImpl(){
        coinAndDistanceText.setX(10);
        coinAndDistanceText.setY(20);
        coinAndDistanceText.setFill(Color.WHITE);
        coinAndDistanceText.setFont(Font.font("Verdana", 20)); 
       
    }

    public void updateDateView(GameStatsModel model){
        coinAndDistanceText.setText("distance: " + model.getcurrentDistance() + 
                                    " Total Coins: " + model.getTotCoins() +
                                    "\n" + "best meter : " + model.getBestDistance()  );
    }

    public Text getText(){
        return this.coinAndDistanceText;
    }
    
}
