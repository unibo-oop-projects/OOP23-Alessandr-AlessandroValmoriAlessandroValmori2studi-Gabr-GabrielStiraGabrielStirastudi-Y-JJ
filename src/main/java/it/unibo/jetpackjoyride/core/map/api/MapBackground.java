package it.unibo.jetpackjoyride.core.map.api;

import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.layout.Pane;

public interface MapBackground {

    void updateBackground();

    Pane getPane();
    
    Pair<Double,Double> getPosX();

    Pair<Double,Double> getSize();

    void reset();

}