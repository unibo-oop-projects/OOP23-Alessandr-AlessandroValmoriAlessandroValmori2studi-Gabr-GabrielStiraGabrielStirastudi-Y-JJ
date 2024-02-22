package it.unibo.jetpackjoyride.core.handler.generic;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import javafx.scene.image.ImageView;

public class EntityController<M extends Entity,V extends GenericView> {
    private final M model;
    private final V view;

    public EntityController(final M model, final V view) {
        this.model = model;
        this.view = view;
    }

    public void update(final boolean isSpaceBarPressed) {
        this.model.update(isSpaceBarPressed);
        this.view.updateView(model);
    }

    public ImageView getImageView() {
        return this.view.getImageView();
    }


}
