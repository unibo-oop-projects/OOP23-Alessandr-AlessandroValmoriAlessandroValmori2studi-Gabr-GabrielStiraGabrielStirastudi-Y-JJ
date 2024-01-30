package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;

public class ObstacleView {
    private ImageView imageView;
    private Image[] images;
    private int animationFrame;

    public ObstacleView(Image[] images) {
        this.images = images;
        imageView = new ImageView();
        this.animationFrame = 0;
    }

    public void updateView(ObstacleImpl obstacle) {
        imageView.setX(obstacle.getEntityMovement().getCurrentPosition().get1());
        imageView.setY(obstacle.getEntityMovement().getCurrentPosition().get2());
        imageView.setRotate(obstacle.getEntityMovement().getRotation().get1());

        if(obstacle.getObstacleType().equals(ObstacleType.MISSILE)) {
            imageView.setFitWidth(200);
            imageView.setFitHeight(50);
        }

        imageView.setImage(images[animationFrame]);
        animationFrame = (animationFrame + 1) % images.length;

    }

    public ImageView getImageView() {
        return imageView;
    }
}