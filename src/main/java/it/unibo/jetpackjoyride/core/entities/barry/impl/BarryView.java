package it.unibo.jetpackjoyride.core.entities.barry.impl;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.List;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryStatus;
import it.unibo.jetpackjoyride.utilities.GameInfo;

/**
 * The BarryView class represents the view of the Barry entity.
 * It is responsible for updating the visual representation of the Barry entity
 * based on its state.
 */
public final class BarryView {

    private ImageView imageView;
    private ImageView shieldImageView;
    private List<Image> images;
    private int animationFrame;
    private GameInfo infoResolution;
    private BarryStatus oldStatus;

    /**
     * Constructs a new BarryView instance with the given list of images.
     *
     * @param images The list of images representing different animations of the
     *               Barry entity.
     */
    public BarryView(final List<Image> images) {
        this.images = images;
        shieldImageView = new ImageView();
        this.imageView = new ImageView();
        this.infoResolution = GameInfo.getInstance();
        this.animationFrame = 0;
        this.oldStatus = BarryStatus.WALKING;
    }

    /**
     * Updates the visual representation of the Barry entity based on its current
     * state.
     *
     * @param barry The Barry entity whose view needs to be updated.
     */

    public void update(final Barry barry) {
        double width = infoResolution.getScreenWidth() / 8;
        double height = infoResolution.getScreenHeight() / 10;

        imageView.setX(barry.getPosition().get1() - width / 2);
        imageView.setY(barry.getPosition().get2() - height / 2);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(this.images.get(animationFrame));
        animationFrame = (animationFrame + 1) % images.size();

        shieldImageView.setX(barry.getPosition().get1() - width / 2);
        shieldImageView.setY(barry.getPosition().get2() - height / 2);

        shieldImageView.setFitWidth(width);
        shieldImageView.setFitHeight(height);

        shieldImageView.setImage(new Image("sprites/entities/player/barrySHIELD.png"));

    }

    /**
     * Sets the current images for the Barry entity's view based on its status.
     * If the status has changed, it updates the images and resets the animation
     * frame.
     *
     * @param images The list of images representing the animations for the current
     *               status.
     * @param status The current status of the Barry entity.
     */
    public void setCurrentImages(final List<Image> images, final BarryStatus status) {
        if (status != this.oldStatus) {
            this.oldStatus = status;
            this.images = images;
            animationFrame = 0;
        }

    }

    /**
     * Retrieves the ImageView representing the visual representation of the Barry
     * entity.
     *
     * @return The ImageView representing the visual representation of the Barry
     *         entity.
     */
    public ImageView getImageView() {
        return this.imageView;
    }

    /**
     * Retrieves the ImageView representing the visual representation of the Barry
     * entity's shield.
     *
     * @return The ImageView representing the visual representation of the Barry
     *         entity's shield.
     */
    public ImageView getShieldImageView() {
        return this.shieldImageView;
    }
}
