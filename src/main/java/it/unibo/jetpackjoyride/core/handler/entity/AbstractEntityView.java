package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@link AbstractEntityView} class implements all the methods of
 * {@link EntityView} and provides a standard code implementation for
 * the scaling of an image based on the screen sizes.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public abstract class AbstractEntityView implements EntityView {
    protected final ImageView imageView;
    protected final List<Image> images;
    protected int animationFrame;
    protected Double width;
    protected Double height;

    /**
     * Constructs an AbstractEntityView with the provided images.
     * By default, width and height of the imageView are set to 0.
     *
     * @param images The type of the entity.
     */
    public AbstractEntityView(final List<Image> images) {
        this.images = new ArrayList<>(images);
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.width = 0.0;
        this.height = 0.0;
    }

    /**
     * Defines a method used by all entity views to carry out an operation of scaling
     * based on the current screen size. The animateFrames method is used first to 
     * find the image corresponding to the entity and then the imageView is modified
     * using scaled position, dimensions and rotation.
     * 
     * @param entity The entity whose imageView is associated.
     */
    @Override
    public void updateView(final Entity entity) {
        this.animateFrames(entity);

        final GameInfo infoResolution = GameInfo.getInstance();

        final double scaleX = infoResolution.getScreenWidth() / infoResolution.getDefaultWidth();
        final double scaleY = infoResolution.getScreenHeight() / infoResolution.getDefaultHeight();

        imageView.setX((entity.getEntityMovement().getPosition().get1() - width / 2) * scaleX);
        imageView.setY((entity.getEntityMovement().getPosition().get2() - height / 2) * scaleY);
        imageView.setRotate(entity.getEntityMovement().getRotation().get1());

        final double width = this.width * scaleX;
        final double height = this.height * scaleY;

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        if(this.animationFrame < this.images.size()) {
            imageView.setImage(this.images.get(this.animationFrame));
        } else {
            final Canvas canvas = new Canvas(width, height);
            final GraphicsContext graphics = canvas.getGraphicsContext2D();
            graphics.setFill(entity.getEntityType().equals(EntityType.OBSTACLE) ? Color.RED : entity.getEntityType().equals(EntityType.POWERUP) ? Color.GREEN : Color.AQUA);
            graphics.fillRect(0, 0, width, height);
            this.imageView.setImage(canvas.snapshot(null, null));
        }
    }

    /**
     * 
     * @param entity The entity whose ImageView has to be computed.
     */
    protected abstract void animateFrames(Entity entity);

    @Override
    public ImageView getImageView() {
        return Collections.nCopies(1, this.imageView).get(0);
    }

}
