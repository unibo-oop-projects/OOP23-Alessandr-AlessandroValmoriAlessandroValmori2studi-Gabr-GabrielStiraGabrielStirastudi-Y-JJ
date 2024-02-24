package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import javafx.scene.image.ImageView;

public interface EntityView {
    void updateView(Entity entity);

    ImageView getImageView();
}