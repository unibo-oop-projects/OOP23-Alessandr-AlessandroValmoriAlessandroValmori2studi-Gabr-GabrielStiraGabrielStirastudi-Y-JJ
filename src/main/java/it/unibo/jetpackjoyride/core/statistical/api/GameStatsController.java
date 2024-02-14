package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public interface GameStatsController {
     void updateModel();

     void updateView();

     Text getText();

     ImageView getImageView();

     GameStatsModel getGameStatsModel();
}
