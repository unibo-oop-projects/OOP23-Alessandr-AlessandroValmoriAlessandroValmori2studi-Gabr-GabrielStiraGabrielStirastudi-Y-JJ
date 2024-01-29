package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.utilities.Pair;

import java.util.*;

public class ObstacleController {
    private ObstacleImpl model;
    private ObstacleView view;

    public ObstacleController(ObstacleImpl model, ObstacleView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        model.update();
        view.updateView(model);

        if(model.isOutOfBounds()) {
            Random random = new Random();
            model.getEntityMovement().setCurrentPosition(new Pair<>(1200.0, 700.0*random.nextDouble()));
        }
    }

    public ImageView getImageView() {
        return view.getImageView();
    }
}
