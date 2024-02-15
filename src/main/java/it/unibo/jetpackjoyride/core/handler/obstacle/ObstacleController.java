package it.unibo.jetpackjoyride.core.handler.obstacle;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import javafx.scene.image.ImageView;

public final class ObstacleController {
    private final Obstacle model;
    private final ObstacleView view;

    public ObstacleController(final Obstacle model, final ObstacleView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        this.model.update(true);
        this.view.updateView(model);
    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }

    public Obstacle getObstacleModel() {
        return this.model;
    }
}
