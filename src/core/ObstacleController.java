package com.fxversion;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ObstacleController {
    private ObstacleModel model;
    private ObstacleView view;

    public ObstacleController(ObstacleModel model, ObstacleView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        model.update();
        view.updateView(model);
    }

    public ImageView getImageView() {
        return view.getImageView();
    }
}
