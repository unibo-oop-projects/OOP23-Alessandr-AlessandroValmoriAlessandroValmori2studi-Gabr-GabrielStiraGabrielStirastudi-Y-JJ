package it.unibo.jetpackjoyride.core.handler.powerup;

import javafx.scene.image.Image;
import java.util.List;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;

public final class PowerUpView extends AbstractEntityView {

    private final static Double LILSTOMPER_X_DIMENSION = 320.0;
    private final static Double LILSTOMPER_Y_DIMENSION = 240.0;
    private final static Integer LILSTOMPER_WALKING_ANIMATION_SPEED = 7;
    private final static Integer LILSTOMPER_WALKING_NUM_SPRITES = 6;
    private final static Integer LILSTOMPER_ASCENDING_ANIMATION_SPEED = 4;
    private final static Integer LILSTOMPER_ASCENDING_NUM_SPRITES = 7;
    private final static Integer LILSTOMPER_GLIDING_ANIMATION_SPEED = 6;
    private final static Integer LILSTOMPER_GLIDING_NUM_SPRITES = 4;
    private final static Integer LILSTOMPER_DESCENDING_ANIMATION_SPEED = 6;
    private final static Integer LILSTOMPER_DESCENDING_NUM_SPRITES = 3;
    private final static Integer LILSTOMPER_LANDING_ANIMATION_SPEED = 4;
    private final static Integer LILSTOMPER_LANDING_NUM_SPRITES = 5;

    private final static Double MRCUDDLES_X_DIMENSION = 640.0;
    private final static Double MRCUDDLES_Y_DIMENSION = 360.0;
    private final static Integer MRCUDDLES_ANIMATION_LENGHT = 5;
    private final static Integer MRCUDDLE_STATIC_NUM_SPRITES = 1;
    private final static Integer MRCUDDLE_ROARING_NUM_SPRITES = 4;

    private final static Double PROFITBIRD_X_DIMENSION = 180.0;
    private final static Double PROFITBIRD_Y_DIMENSION = 120.0;
    private final static Integer PROFITBIRD_WALKING_ANIMATION_LENGHT = 7;
    private final static Integer PROFITBIRD_WALKING_NUM_SPRITES = 3;
    private final static Integer PROFITBIRD_JUMPING_ANIMATION_LENGHT = 6;
    private final static Integer PROFITBIRD_JUMPING_NUM_SPRITES = 1;
    private final static Integer PROFITBIRD_ASCENDING_ANIMATION_LENGHT = 1;
    private final static Integer PROFITBIRD_ASCENDING_NUM_SPRITES = 8;

    private final static Double DUKEFISHRON_X_DIMENSION = 320.0;
    private final static Double DUKEFISHRON_Y_DIMENSION = 240.0;
    private final static Integer DUKEFISHRON_ANIMATION_LENGHT = 6;
    private final static Integer DUKEFISHRON_ASCENDING_NUM_SPRITES = 6;

    private int animationLenght;
    private int[] animationCounter;

    public PowerUpView(final List<Image> images) {
        super(images);
        this.animationFrame = 0;
        this.animationCounter = new int[5]; // 0 counter for walking, 1 counter for ascending,
                                            // 2 counter for descending, 3 counter for descending-landing, 4 counter for
                                            // landing
        this.animationLenght = 1;
    }

    @Override
    protected void animateFrames(Entity entity) {
        final PowerUp powerUp = (PowerUp)entity;

        switch (powerUp.getPowerUpType()) {
            case LILSTOMPER:
                width = LILSTOMPER_X_DIMENSION;
                height = LILSTOMPER_Y_DIMENSION;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = LILSTOMPER_WALKING_ANIMATION_SPEED;
                        animationFrame = (animationCounter[0]) / animationLenght % LILSTOMPER_WALKING_NUM_SPRITES;
                        animationCounter[0]++;
                        break;

                    case ASCENDING:
                        animationLenght = LILSTOMPER_ASCENDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + ((animationCounter[1]) / animationLenght % LILSTOMPER_ASCENDING_NUM_SPRITES);
                        if (animationCounter[1] != animationLenght * LILSTOMPER_ASCENDING_NUM_SPRITES - 1) {
                            animationCounter[1]++;
                        }
                        break;
                    case GLIDING:
                        animationCounter[3] = 0;
                        animationLenght = LILSTOMPER_GLIDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES + ((animationCounter[2]) / animationLenght % LILSTOMPER_GLIDING_NUM_SPRITES);
                        animationCounter[2]++;
                        break;

                    case DESCENDING:
                        animationCounter[1] = 0;
                        animationLenght = LILSTOMPER_DESCENDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES + LILSTOMPER_GLIDING_NUM_SPRITES + ((animationCounter[3]) / animationLenght % LILSTOMPER_DESCENDING_NUM_SPRITES);
                        if (animationCounter[3] != animationLenght * LILSTOMPER_DESCENDING_NUM_SPRITES - 1) {
                            animationCounter[3]++;
                        }
                        break;

                    case LANDING:
                        animationLenght = LILSTOMPER_LANDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES + LILSTOMPER_GLIDING_NUM_SPRITES + LILSTOMPER_DESCENDING_NUM_SPRITES + ((animationCounter[4]) / animationLenght % LILSTOMPER_LANDING_NUM_SPRITES);
                        if (animationCounter[4] != animationLenght * LILSTOMPER_LANDING_NUM_SPRITES - 1) {
                            animationCounter[4]++;
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
                width = MRCUDDLES_X_DIMENSION;
                height = MRCUDDLES_Y_DIMENSION;
                if (powerUp.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                    switch (powerUp.getPerformingAction()) {
                        case ASCENDING:
                            animationLenght = MRCUDDLES_ANIMATION_LENGHT;
                            animationFrame = (animationCounter[1]) / animationLenght % MRCUDDLE_STATIC_NUM_SPRITES;
                            if (animationCounter[1] != 0) {
                                animationCounter[1]--;
                            }
                            break;
                        case DESCENDING:
                            animationLenght = MRCUDDLES_ANIMATION_LENGHT;
                            animationFrame = MRCUDDLE_STATIC_NUM_SPRITES + ((animationCounter[1]) / animationLenght % MRCUDDLE_ROARING_NUM_SPRITES);
                            if (animationCounter[1] != animationLenght*MRCUDDLE_ROARING_NUM_SPRITES - 1) {
                                animationCounter[1]++;
                            }
                            break;
                        default:
                            animationFrame = 0;
                            break;
                    }
                } else {
                    animationFrame = MRCUDDLE_STATIC_NUM_SPRITES + MRCUDDLE_ROARING_NUM_SPRITES;
                }
                break;

            case PROFITBIRD:
                width = PROFITBIRD_X_DIMENSION;
                height = PROFITBIRD_Y_DIMENSION;
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        animationLenght = PROFITBIRD_WALKING_ANIMATION_LENGHT;
                        animationFrame = (animationCounter[0]) / animationLenght % PROFITBIRD_WALKING_NUM_SPRITES;
                        animationCounter[0]++;
                        break;

                    case JUMPING:
                        animationLenght = PROFITBIRD_JUMPING_ANIMATION_LENGHT;
                        animationFrame = PROFITBIRD_WALKING_NUM_SPRITES;
                        animationCounter[1] = 0;
                        break;

                    case ASCENDING:
                        animationLenght = PROFITBIRD_ASCENDING_ANIMATION_LENGHT;
                        if(animationCounter[1] < animationLenght * PROFITBIRD_ASCENDING_NUM_SPRITES - 1 ) {
                            animationCounter[1]++;
                        } 
                        animationFrame = PROFITBIRD_WALKING_NUM_SPRITES + PROFITBIRD_JUMPING_NUM_SPRITES + ((animationCounter[1]) / animationLenght % PROFITBIRD_ASCENDING_NUM_SPRITES);
                        break;

                    default:
                        animationFrame = 0;
                        break;
                }
                break;

            case DUKEFISHRON:
                width = DUKEFISHRON_X_DIMENSION;
                height = DUKEFISHRON_Y_DIMENSION;
                switch (powerUp.getPerformingAction()) {
                    case ASCENDING:
                        animationLenght = DUKEFISHRON_ANIMATION_LENGHT;
                        animationFrame = (animationCounter[0]) / animationLenght % DUKEFISHRON_ASCENDING_NUM_SPRITES;
                        animationCounter[0]++;
                        break;
                    case DESCENDING:
                        animationLenght = DUKEFISHRON_ANIMATION_LENGHT;
                        animationFrame = DUKEFISHRON_ASCENDING_NUM_SPRITES + ((animationCounter[0]) / animationLenght % DUKEFISHRON_ASCENDING_NUM_SPRITES);
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
    }
}
