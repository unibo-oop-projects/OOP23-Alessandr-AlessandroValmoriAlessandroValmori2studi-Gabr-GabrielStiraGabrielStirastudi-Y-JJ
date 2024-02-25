package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import javafx.scene.image.ImageView;

/**
 * The {@link EntityView} interface defines the methods the classes which deal
 * with the view of the entities have. 
 * These classes generate an imageView initially loading all the images of the entity 
 * and with each call of the updateView method the images loaded by the ImageView are
 * updated based on the status, action, movement, etc... of the entity.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public interface EntityView {
    /**
     * Updates the ImageView, loading it with a new Image computed after an internal process of animation.
     * @param entity The entity whose view has to be calculated. Since the view changes based on a lot of 
     * parameters (movement, status, action...), the entire Entity class is passed.
     */
    void updateView(Entity entity);

    /**
     * Gets the ImageView of the entity
     * @return The imageView of the entity
     */
    ImageView getImageView();
}