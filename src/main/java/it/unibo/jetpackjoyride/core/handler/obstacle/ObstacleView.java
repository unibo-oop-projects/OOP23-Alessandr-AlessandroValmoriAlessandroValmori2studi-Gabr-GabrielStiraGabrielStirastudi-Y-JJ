package it.unibo.jetpackjoyride.core.handler.obstacle;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import it.unibo.jetpackjoyride.core.handler.entity.EntityView;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public final class ObstacleView extends AbstractEntityView {
    private final static Double MISSILE_WARNING_X_DIMENSION = 80.0;
    private final static Double MISSILE_WARNING_Y_DIMENSION = 60.0;
    private final static Double MISSILE_WARNING_DIMENSION_CHANGE = 15.0;
    private final static Integer MISSILE_WARNING_ANIMATION_LENGHT = 4;
    private final static Integer MISSILE_WARNING_FIRST_NUM_SPRITES = 3;
    private final static Integer MISSILE_WARNING_SECOND_NUM_SPRITES = 2;
    private final static Integer MISSILE_WARNING_FIRST_TICKS = 90;
    private final static Integer MISSILE_WARNING_SECOND_TICKS = 100;

    private final static Double MISSILE_X_DIMENSION = 160.0;
    private final static Double MISSILE_Y_DIMENSION = 45.0;
    private final static Integer MISSILE_ANIMATION_LENGHT = 4;
    private final static Integer MISSILE_NUM_SPRITES = 7;

    private final static Double MISSILE_EXPLOSION_X_DIMENSION = 160.0;
    private final static Double MISSILE_EXPLOSION_Y_DIMENSION = 160.0;
    private final static Integer MISSILE_EXPLOSION_ANIMATION_LENGHT = 7;
    private final static Integer MISSILE_EXPLOSION_NUM_SPRITES = 8;

    private final static Double ZAPPER_X_DIMENSION = 215.0;
    private final static Double ZAPPER_Y_DIMENSION = 90.0;
    private final static Integer ZAPPER_ANIMATION_LENGHT = 6;
    private final static Integer ZAPPER_NUM_SPRITES = 4;

    private final static Integer ZAPPER_BROKEN_ANIMATION_LENGHT = 4;
    private final static Integer ZAPPER_BROKEN_NUM_SPRITES = 16;

    private final static Double LASER_X_DIMENSION = 1150.0;
    private final static Double LASER_Y_DIMENSION = 30.0;
    private final static Integer LASER_ANIMATION_LENGHT = 8;

    private final static Integer LASER_CHARGING_NUM_SPRITES = 12;

    private final static Integer LASER_BEAM_NUM_SPRITES = 4;

    private int animationLenght;
    private int[] animationCounter;

    public ObstacleView(final List<Image> images) {
        super(images);
        this.animationCounter = new int[3]; // 0 counter for charging, 1 counter for active, 2 counter for deactivated
        this.animationLenght = 1;
    }

    @Override
    protected void animateFrames(final Entity entity) {
        final Obstacle obstacle = (Obstacle) entity;

        switch (obstacle.getObstacleType()) {
            case MISSILE:
                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        this.width = MISSILE_WARNING_X_DIMENSION;
                        this.height = MISSILE_WARNING_Y_DIMENSION;
                        this.animationLenght = MISSILE_WARNING_ANIMATION_LENGHT;
                        this.animationFrame = (this.animationCounter[0]) / this.animationLenght
                                % MISSILE_WARNING_FIRST_NUM_SPRITES;

                        if (animationCounter[0] > MISSILE_WARNING_FIRST_TICKS) {
                            this.animationFrame = MISSILE_WARNING_FIRST_NUM_SPRITES + ((this.animationCounter[0])
                                    / this.animationLenght % MISSILE_WARNING_SECOND_NUM_SPRITES);
                            this.width += MISSILE_WARNING_DIMENSION_CHANGE;
                            this.height += MISSILE_WARNING_DIMENSION_CHANGE;
                        } else { 
                            if(animationCounter[0] > MISSILE_WARNING_FIRST_TICKS && animationCounter[0] < MISSILE_WARNING_SECOND_TICKS) {
                                this.animationCounter[0] = MISSILE_WARNING_FIRST_TICKS;
                                this.width = MISSILE_WARNING_X_DIMENSION;
                                this.height = MISSILE_WARNING_Y_DIMENSION;
                            }  
                        }
                        this.animationCounter[0]++;
                        break;
                    case ACTIVE:
                        this.width = MISSILE_X_DIMENSION;
                        this.height = MISSILE_Y_DIMENSION;
                        this.animationLenght = MISSILE_ANIMATION_LENGHT;
                        this.animationFrame = MISSILE_WARNING_FIRST_NUM_SPRITES + MISSILE_WARNING_SECOND_NUM_SPRITES + ((animationCounter[1]) / animationLenght % MISSILE_NUM_SPRITES);
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.width = MISSILE_EXPLOSION_X_DIMENSION;
                        this.height = MISSILE_EXPLOSION_Y_DIMENSION;
                        this.animationLenght = MISSILE_EXPLOSION_ANIMATION_LENGHT;
                        this.animationFrame = MISSILE_WARNING_FIRST_NUM_SPRITES + MISSILE_WARNING_SECOND_NUM_SPRITES + MISSILE_NUM_SPRITES + ((animationCounter[2]) / animationLenght % MISSILE_EXPLOSION_NUM_SPRITES);
                        this.animationCounter[2]++;
                        break;
                    default:
                        this.animationFrame = 0;
                        this.width = 0.0;
                        this.height = 0.0;
                        break;
                }

                break;
            case ZAPPER:
                this.width = ZAPPER_X_DIMENSION;
                this.height = ZAPPER_Y_DIMENSION;

                switch (obstacle.getEntityStatus()) {
                    case ACTIVE:
                        this.animationLenght = ZAPPER_ANIMATION_LENGHT;
                        this.animationFrame = (this.animationCounter[1]) / this.animationLenght % ZAPPER_NUM_SPRITES;
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.animationLenght = ZAPPER_BROKEN_ANIMATION_LENGHT;
                        this.animationFrame = ZAPPER_NUM_SPRITES + ((this.animationCounter[2]) / this.animationLenght % ZAPPER_BROKEN_NUM_SPRITES);
                        System.out.println(this.animationFrame);
                        if (this.animationFrame != ZAPPER_NUM_SPRITES + ZAPPER_BROKEN_NUM_SPRITES - 1) {
                            this.animationCounter[2]++;
                        }
                        break;
                    default:
                        this.animationFrame = 0;
                        this.width = 0.0;
                        this.height = 0.0;
                        break;
                }
                break;
            case LASER:
                this.width = LASER_X_DIMENSION;
                this.height = LASER_Y_DIMENSION;
                this.animationLenght = LASER_ANIMATION_LENGHT;

                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        this.animationFrame = (this.animationCounter[0]) / this.animationLenght % LASER_CHARGING_NUM_SPRITES;
                        this.animationCounter[0]++;
                        break;
                    case ACTIVE:
                        this.animationFrame = LASER_CHARGING_NUM_SPRITES + ((this.animationCounter[1]) / this.animationLenght % LASER_BEAM_NUM_SPRITES);
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.animationFrame = LASER_CHARGING_NUM_SPRITES - 1 + ((-animationCounter[2]) / this.animationLenght % LASER_CHARGING_NUM_SPRITES);
                        this.animationCounter[2]++;
                        break;
                    default:
                        this.animationFrame = 0;
                        this.width = 0.0;
                        this.height = 0.0;
                        break;
                }
                break;
            default:
                this.width = 0.0;
                this.height = 0.0;
                break;
        }

    }
}