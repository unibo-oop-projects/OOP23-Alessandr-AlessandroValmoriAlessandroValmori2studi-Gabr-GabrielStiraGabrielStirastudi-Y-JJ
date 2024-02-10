package it.unibo.jetpackjoyride.core.handler.obstacle;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import javafx.scene.image.ImageView;

public class ObstacleController {
    private Obstacle model;
    private ObstacleView view;

    public ObstacleController(Obstacle model, ObstacleView view) {
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

    public Obstacle getObstacleModel() {
        return this.model;
    }
}
