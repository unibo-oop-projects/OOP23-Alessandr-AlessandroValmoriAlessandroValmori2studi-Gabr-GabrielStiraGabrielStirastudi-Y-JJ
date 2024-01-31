package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
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

        double width;
        double height;

        switch (obstacle.getObstacleType()) {
            case MISSILE:
                width=200;
                height=50;
                break;
            case ZAPPER:
                width=300;
                height=100;
            default:
                width=200;
                height=50;
                break;
        }

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images[animationFrame]);
        animationFrame = (animationFrame + 1) % images.length;

    }

    public ImageView getImageView() {
        return imageView;
    }
}