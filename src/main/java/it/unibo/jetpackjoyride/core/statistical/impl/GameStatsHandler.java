package it.unibo.jetpackjoyride.core.statistical.impl;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsModel;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsView;
import javafx.scene.text.Text;

public class GameStatsHandler implements GameStatsController {

    private GameStatsModel model;
    private GameStatsView view;

    public GameStatsHandler( ) {      
        this.model = new GameStats(); 
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

    public GameStatsModel getGameStatsModel(){
        return this.model;
    }
    
}
