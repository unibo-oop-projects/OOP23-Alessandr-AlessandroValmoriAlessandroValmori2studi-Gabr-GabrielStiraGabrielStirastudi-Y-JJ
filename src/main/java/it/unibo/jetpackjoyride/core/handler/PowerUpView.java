package it.unibo.jetpackjoyride.core.handler;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.utilities.GameInfo;

import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper.PerformingAction;

public class PowerUpView {
    private ImageView imageView;
    private Image[] images;
    private int animationFrame;
    private int animationLenght;
    private int[] animationCounter;
    private GameInfo infoResolution;

    public PowerUpView(Image[] images) {
        this.images = images;
        this.imageView = new ImageView();
        this.infoResolution = new GameInfo();
        this.animationFrame = 0;
        this.animationCounter = new int[4]; //0 counter for walking, 1 counter for ascending, 
                                            //2 counter for descending, 3 counter for landing
        this.animationLenght = 1;
    }

    public void updateView(PowerUp powerUp) {
        double width;
        double height;
        animationFrame=0;

        switch (powerUp.getPowerUpType()) {
            case LILSTOMPER:
                width = 300;
                height = 230;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = 6;
                        animationFrame = ((animationCounter[0])/animationLenght % 6);
                        animationCounter[0]++;
                        break;
                    case ASCENDING:
                    if(animationCounter[1] < 20) {
                        animationLenght = 3;
                        animationFrame = 5 + ((animationCounter[1])/animationLenght % 8);
                        animationCounter[1]++;
                    } else {
                        animationLenght = 6;
                        animationFrame = 13 + ((animationCounter[1])/animationLenght % 2);
                        animationCounter[1]++;
                    }
                        break;
                    case DESCENDING:
                    animationLenght = 6;
                    animationFrame = 15 + ((animationCounter[2])/animationLenght % 2);
                    animationCounter[2]++;
                        break;
                    case LANDING:
                    animationLenght = 4;
                    animationFrame = 19 + ((animationCounter[3])/animationLenght % 5);
                    animationCounter[3]++;
                    if(animationCounter[3] > 20) {
                        for(int i=0; i<4; i++) {
                            animationCounter[i] = 0;
                        }
                    }
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

        imageView.setX(powerUp.getEntityMovement().getCurrentPosition().get1() - width/2);
        imageView.setY(powerUp.getEntityMovement().getCurrentPosition().get2() - height/2);
        imageView.setRotate(powerUp.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images[animationFrame]);
    }

    public ImageView getImageView() {
        return imageView;
    }
}