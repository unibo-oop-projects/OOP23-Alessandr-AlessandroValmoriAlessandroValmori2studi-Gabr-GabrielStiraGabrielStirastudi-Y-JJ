package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.text.Text;

public interface GameStatsController {
     void updateModel();

     void updateView();

     Text getText();

     GameStatsModel getGameStatsModel();
}
