package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;

public class ObstacleController {
    private ObstacleImpl model;
    private ObstacleView view;

    public ObstacleController(ObstacleImpl model, ObstacleView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        this.model.update();
        this.view.updateView(model);
    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }

    public ObstacleImpl getObstacleModel() {
        return this.model;
    }
}
