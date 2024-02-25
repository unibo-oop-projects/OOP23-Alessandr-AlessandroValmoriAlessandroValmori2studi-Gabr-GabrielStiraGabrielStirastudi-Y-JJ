package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@link AbstractEntityView} class implements all the methods of
 * {@link EntityView}
 * and provides a standard code implementation for the scaling of an image based
 * on the
 * screen sizes.
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
     * Constructs and AbstractEntityView with the provided images.
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

    @Override
    public void updateView(Entity entity) {
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

        imageView.setImage(images.get(animationFrame));
    }

    /**
     * 
     * @param entity The entity whose ImageView has to be computed.
     */
    protected abstract void animateFrames(final Entity entity);

    @Override
    public ImageView getImageView() {
        return Collections.nCopies(1, this.imageView).get(0);
    }

}
