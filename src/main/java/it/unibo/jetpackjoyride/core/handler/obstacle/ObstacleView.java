package it.unibo.jetpackjoyride.core.handler.obstacle;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.generic.GenericView;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public final class ObstacleView implements GenericView{
    private final ImageView imageView;
    private final Image[] images;
    private int animationFrame;
    private int animationLenght;
    private int[] animationCounter;

    public ObstacleView(final Image[] images) {
        this.images = images.clone();
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.animationCounter = new int[3]; // 0 counter for charging, 1 counter for active, 2 counter for deactivated
        this.animationLenght = 1;
    }

    public void updateView(final Entity entity) {
        Obstacle obstacle = (Obstacle)entity;
        Double width;
        Double height;
        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        switch (obstacle.getObstacleType()) {
            case MISSILE:
                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        width = screenSizeX / 16;
                        height = screenSizeY / 12;
                        animationLenght = 4;
                        animationFrame = ((animationCounter[0]) / animationLenght % 3);
                        if(animationCounter[0] > 90) {
                            animationFrame = 3 + ((animationCounter[0]) / animationLenght % 2);
                            width+=15;
                            height+=15;
                        }
                        if(animationCounter[0] == 100) {
                            width = screenSizeX / 16;
                            height = screenSizeY / 12;
                            animationCounter[0] = 90;
                        }
                        animationCounter[0]++;
                        break;
                    case ACTIVE:
                        width = screenSizeX / 8;
                        height = screenSizeY / 16;
                        animationLenght = 4;
                        animationFrame = 5 + ((animationCounter[1]) / animationLenght % 7);
                        animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        width = screenSizeX / 8;
                        height = screenSizeY / 16;
                        animationLenght = 7;
                        animationFrame = 12 + ((animationCounter[2]) / animationLenght % 8);
                        width = screenSizeX / 8;
                        height = screenSizeY / 5;
                        animationCounter[2]++;
                        break;
                    default:
                        animationFrame = 0;
                        width = 0.0;
                        height = 0.0;
                        break;
                }

                break;
            case ZAPPER:
                width = screenSizeX / 6;
                height = screenSizeY / 8;

                switch (obstacle.getEntityStatus()) {
                    case ACTIVE:
                        animationLenght = 6;
                        animationFrame = (animationCounter[1]) / animationLenght % 4;
                        animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        animationLenght = 4;
                        animationFrame = 3 + ((animationCounter[2]) / animationLenght % 17);
                        if (animationFrame != 19) {
                            animationCounter[2]++;
                        }
                        break;
                    default:
                        animationFrame = 0;
                        width = 0.0;
                        height = 0.0;
                        break;
                }
                break;
            case LASER:
                width = screenSizeX - screenSizeX / 8;
                height = screenSizeY / 24;
                animationLenght = 8;

                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        animationFrame = (animationCounter[0]) / animationLenght % 12;
                        animationCounter[0]++;
                        break;
                    case ACTIVE:
                        animationFrame = 12 + ((animationCounter[1]) / animationLenght % 4);
                        animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        animationFrame = 11 + ((-animationCounter[2]) / animationLenght % 12);
                        animationCounter[2]++;
                        break;
                    default:
                        animationFrame = 0;
                        width = 0.0;
                        height = 0.0;
                        break;
                }
                break;
            default:
                width = 0.0;
                height = 0.0;
                break;
        }

        imageView.setX(obstacle.getEntityMovement().getCurrentPosition().get1() - width / 2);
        imageView.setY(obstacle.getEntityMovement().getCurrentPosition().get2() - height / 2);
        imageView.setRotate(obstacle.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images[animationFrame]);
    }

    public ImageView getImageView() {
        return imageView;
    }

}