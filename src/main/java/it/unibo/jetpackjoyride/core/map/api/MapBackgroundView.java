package it.unibo.jetpackjoyride.core.map.api;

import javafx.scene.layout.Pane;

public interface MapBackgroundView {
    void updateBackgroundView(double x1,double x2 ,double mapWidth,double mapHeight);
    Pane getPane();
}
