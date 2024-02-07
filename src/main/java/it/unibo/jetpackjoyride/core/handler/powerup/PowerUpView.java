package it.unibo.jetpackjoyride.core.handler.powerup;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpStatus;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public class PowerUpView {
    private ImageView imageView;
    private Image[] images;
    private int animationFrame;
    private int animationLenght;
    private int[] animationCounter;

    public PowerUpView(Image[] images) {
        this.images = images;
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.animationCounter = new int[5]; //0 counter for walking, 1 counter for ascending, 
                                            //2 counter for descending, 3 counter for descending-landing, 4 counter for landing
        this.animationLenght = 1;
    }

    public void updateView(PowerUp powerUp) {
        double width;
        double height;
        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        animationFrame=0;

        switch (powerUp.getPowerUpType()) {
            case LILSTOMPER:
                width = screenSizeX/4;
                height = screenSizeY/3;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = 6;
                        animationFrame = ((animationCounter[0])/animationLenght % 6);
                        animationCounter[0]++;
                        break;
                    case ASCENDING:
                        animationLenght = 5;
                        animationFrame = 5 + ((animationCounter[1])/animationLenght % 10);
                        if(animationCounter[1] < animationLenght*10 - 1 ) {
                            animationCounter[1]++;
                        } else {
                            animationCounter[1] = animationLenght*8;
                        }
                        break;
                    case DESCENDING:
                    if(powerUp.getEntityMovement().getSpeed().get2()<=50) {
                        animationLenght = 6;
                        animationFrame = 13 + ((animationCounter[2])/animationLenght % 4);
                        animationCounter[2]++;
                    } else {
                        animationLenght = 8;
                        animationFrame = 17 + ((animationCounter[3])/animationLenght % 2);
                        if(animationCounter[3]<15) {
                            animationCounter[3]++;
                        } else {
                            animationCounter[3] = 15;
                        }
                    }
                    
                        break;
                    case LANDING:
                    animationLenght = 4;
                    animationFrame = 19 + ((animationCounter[4])/animationLenght % 5);
                    animationCounter[4]++;
                    if(animationCounter[4] > animationLenght*5) {
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
            case MRCUDDLES:
                width = 600;
                height = 400;
                if(powerUp.getPowerUpStatus().equals(PowerUpStatus.ACTIVE)) {
                    switch (powerUp.getPerformingAction()) {
                        case ASCENDING:
                            animationLenght = 5;
                            animationFrame = ((animationCounter[1])/animationLenght % 5);
                            if(animationCounter[1] > 0) {
                                animationCounter[1]--;
                            }
                            break;
                        case DESCENDING:
                            animationLenght = 5;
                            animationFrame = 1 + ((animationCounter[1])/animationLenght % 4);
                            if(animationCounter[1]<16) {
                                animationCounter[1]++;
                            }
                            break;
                        default:
                            animationFrame=0;
                            break;
                    }
                }
                else 
                {
                    animationFrame = 5;   
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