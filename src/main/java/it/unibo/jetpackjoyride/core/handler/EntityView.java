package it.unibo.jetpackjoyride.core.handler;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class EntityView {
    private ImageView imageView;
    private Image[] images;
    private int animationFrame;
    private int rotationAngle=0;

    public EntityView(Image[] images) {
        this.images = images;
        imageView = new ImageView();
        this.animationFrame = 0;
    }

    public void updateView(Entity entity) {
        imageView.setX(entity.getEntityMovement().getCurrentPosition().get1());
        imageView.setY(entity.getEntityMovement().getCurrentPosition().get2());
        imageView.setFitWidth(200);
        imageView.setFitHeight(50);
        imageView.setRotate(rotationAngle);
        rotationAngle+=5.0;

        imageView.setImage(images[animationFrame]);
        animationFrame = (animationFrame + 1) % images.length;

    }

    public ImageView getImageView() {
        return imageView;
    }
}