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
                animationFrame = (animationFrame + 1) % images.length;
                break;
            case ZAPPER:
                width=infoResolution.getScreenWidth()/6;
                height=infoResolution.getScreenHeight()/16;
                animationFrame = (animationFrame + 1) % images.length;
                break;
            case LASER:
                width=infoResolution.getScreenWidth() - (0.04)*infoResolution.getScreenWidth();
                height=infoResolution.getScreenHeight()/24;

                switch (obstacle.getObstacleStatus()) {
                    case CHARGING:
                        animationFrame++;
                        break;
                    case ACTIVE:
                        animationFrame = ((animationFrame + 1) % 28) + 84;
                        break;
                    case DEACTIVATED:
                        if(animationFrame>84) {
                            animationFrame = 84;
                        }
                        animationFrame--;
                        break;
                    default:
                        animationFrame=0;
                        break;
                }
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

    }

    public ImageView getImageView() {
        return imageView;
    }
}