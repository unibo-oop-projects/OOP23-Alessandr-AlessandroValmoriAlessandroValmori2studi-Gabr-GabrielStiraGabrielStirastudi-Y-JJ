package it.unibo.jetpackjoyride.core.statistical.api;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public interface GameStatsView {

    void updateDateView(GameStatsModel model);

    Text getText();
        
    ImageView getImageView();
}
