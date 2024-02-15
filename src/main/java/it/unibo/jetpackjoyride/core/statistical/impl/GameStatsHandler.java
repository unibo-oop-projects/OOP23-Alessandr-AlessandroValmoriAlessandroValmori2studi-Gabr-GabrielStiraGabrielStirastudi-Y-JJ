package it.unibo.jetpackjoyride.core.statistical.impl;

import java.io.IOException;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GameStatsHandler implements GameStatsController {

    private GameStatsModel model;
    private GameStatsView view;

    public GameStatsHandler( ) {      
        loadDateFromFile();
        this.view =  new GameStatsViewImpl();
    }

    public void updateModel(){
        model.addDistance();
    }

    public void updateView(){
        view.updateDateView(model);
    }

    public Text getText(){
        return view.getText();
    } 

    public ImageView getImageView(){
        return view.getImageView();
    }

    public GameStatsModel getGameStatsModel(){
        return this.model;
    }

    private void loadDateFromFile(){
        try {
            this.model = GameStats.readFromFile("gameStats.data"); 
            System.out.println("Game stats loaded successfully.");
            System.out.println(this.model.getTotCoins());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game stats: " + e.getMessage());
          
            this.model = new GameStats();
        }
    }
    
}
