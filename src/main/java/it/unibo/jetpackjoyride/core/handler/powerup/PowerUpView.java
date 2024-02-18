package it.unibo.jetpackjoyride.core.handler.powerup;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.handler.generic.GenericView;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public final class PowerUpView implements GenericView {
    private ImageView imageView;
    private List<Image> images;
    private int animationFrame;
    private int animationLenght;
    private int[] animationCounter;

    public PowerUpView(final List<Image> images) {
        this.images = images;
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.animationCounter = new int[5]; // 0 counter for walking, 1 counter for ascending,
                                            // 2 counter for descending, 3 counter for descending-landing, 4 counter for
                                            // landing
        this.animationLenght = 1;
    }

    public void updateView(final Entity entity) {
        PowerUp powerUp = (PowerUp)entity;
        Double width;
        Double height;
        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        animationFrame = 0;

        switch (powerUp.getPowerUpType()) {
            case LILSTOMPER:
                width = screenSizeX / 4;
                height = screenSizeY / 3;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = 7;
                        animationFrame = ((animationCounter[0]) / animationLenght % 6);
                        animationCounter[0]++;
                        break;

                    case ASCENDING:
                        animationLenght = 4;
                        animationFrame = 5 + ((animationCounter[1]) / animationLenght % 10);
                        if (animationCounter[1] < animationLenght * 10 - 1) {
                            animationCounter[1]++;
                        } else {
                            animationCounter[1] = animationLenght * 8;
                        }
                        break;

                    case DESCENDING:
                        animationCounter[1] = 0;
                        animationLenght = 6;
                        animationFrame = 14 + ((animationCounter[3]) / animationLenght % 5);
                        if (animationCounter[3] < animationLenght * 4) {
                            animationCounter[3]++;
                        } else {
                            animationCounter[3] = animationLenght * 4;
                        }
                        break;

                    case GLIDING:
                        animationLenght = 6;
                        animationFrame = 13 + ((animationCounter[2]) / animationLenght % 4);
                        animationCounter[2]++;
                        break;

                    case LANDING:
                        animationLenght = 4;
                        animationFrame = 19 + ((animationCounter[4]) / animationLenght % 5);
                        animationCounter[4]++;
                        if (animationCounter[4] > animationLenght * 5) {
                            for (int i = 0; i < 4; i++) {
                                animationCounter[i] = 0;
                            }
                        }
                        break;

                    default:
                        animationFrame = 0;
                        break;
                }

                break;
            case MRCUDDLES:
                width = screenSizeX / 2;
                height = screenSizeY / 2;
                if (powerUp.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                    switch (powerUp.getPerformingAction()) {
                        case ASCENDING:
                            animationLenght = 5;
                            animationFrame = ((animationCounter[1]) / animationLenght % 5);
                            if (animationCounter[1] > 0) {
                                animationCounter[1]--;
                            }
                            break;
                        case DESCENDING:
                            animationLenght = 5;
                            animationFrame = 1 + ((animationCounter[1]) / animationLenght % 4);
                            if (animationCounter[1] < 16) {
                                animationCounter[1]++;
                            }
                            break;
                        default:
                            animationFrame = 0;
                            break;
                    }
                } else {
                    animationFrame = 5;
                }
                break;

            case PROFITBIRD:
                width = screenSizeX / 7;
                height = screenSizeY / 6;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = 7;
                        animationFrame = ((animationCounter[0]) / animationLenght % 3);
                        animationCounter[0]++;
                        break;

                    case JUMPING:
                        animationLenght = 6;
                        animationFrame = 3;
                        animationCounter[1] = 0;
                        break;

                    case ASCENDING:
                        animationLenght = 1;
                        if(animationCounter[1] < animationLenght * 9 ) {
                            animationCounter[1]++;
                        } 
                        animationFrame = 3 + ((animationCounter[1]) / animationLenght % 9);
                        break;

                    case DESCENDING:
                        animationLenght = 6;
                        animationFrame = 3;
                        break;

                    default:
                        animationFrame = 0;
                        break;
                }
                break;

            case DUKEFISHRON:
                width = screenSizeX / 4;
                height = screenSizeY / 3;
                switch (powerUp.getPerformingAction()) {
                    case ASCENDING:
                        animationLenght = 6;
                        animationFrame = ((animationCounter[0]) / animationLenght % 6);
                        animationCounter[0]++;
                        break;
                    case DESCENDING:
                        animationLenght = 6;
                        animationFrame = 6 + ((animationCounter[0]) / animationLenght % 6);
                        animationCounter[0]++;
                        break;
                    default:
                        animationFrame = 0;
                        break;

                }
                break;
                
            default:
                width = 0.0;
                height = 0.0;
                break;
        }

        imageView.setX(powerUp.getEntityMovement().getCurrentPosition().get1() - width / 2);
        imageView.setY(powerUp.getEntityMovement().getCurrentPosition().get2() - height / 2);
        imageView.setRotate(powerUp.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images.get(animationFrame));
    }

    public ImageView getImageView() {
        return imageView;
    }
}
