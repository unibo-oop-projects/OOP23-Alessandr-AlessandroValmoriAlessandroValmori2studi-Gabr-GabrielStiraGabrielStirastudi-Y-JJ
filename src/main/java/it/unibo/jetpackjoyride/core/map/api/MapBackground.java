package it.unibo.jetpackjoyride.core.map.api;

import javafx.scene.layout.Pane;

public interface MapBackground {
    void updateBackgroundView();

    void updateBackgroundModel();

    Pane getPane();

}