package it.unibo.jetpackjoyride.core.handler.generic;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import javafx.scene.image.ImageView;

public interface GenericView {
    <T extends Entity> void updateView(T entity);

    ImageView getImageView();
}
