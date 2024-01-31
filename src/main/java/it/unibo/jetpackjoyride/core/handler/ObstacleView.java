package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public class ObstacleView {
    private ImageView imageView;
    private Image[] images;
    private int animationFrame;
    private GameInfo infoResolution;

    public ObstacleView(Image[] images) {
        this.images = images;
        this.imageView = new ImageView();
        this.infoResolution = new GameInfo();
        this.animationFrame = 0;
    }

    public void updateView(ObstacleImpl obstacle) {
        double width;
        double height;

        switch (obstacle.getObstacleType()) {
            case MISSILE:
                width=infoResolution.getScreenWidth()/8;
                height=infoResolution.getScreenHeight()/16;
                break;
            case ZAPPER:
                width=infoResolution.getScreenWidth()/6;
                height=infoResolution.getScreenHeight()/16;
                break;
            case LASER:
                width=infoResolution.getScreenWidth() - (0.04)*infoResolution.getScreenWidth();
                height=infoResolution.getScreenHeight()/24;
                break;
            default:
                width=0;
                height=0;
                break;
        }

        imageView.setX(obstacle.getEntityMovement().getCurrentPosition().get1() - width/2);
        imageView.setY(obstacle.getEntityMovement().getCurrentPosition().get2() - height/2);
        imageView.setRotate(obstacle.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images[animationFrame]);
        animationFrame = (animationFrame + 1) % images.length;

    }

    public ImageView getImageView() {
        return imageView;
    }
}