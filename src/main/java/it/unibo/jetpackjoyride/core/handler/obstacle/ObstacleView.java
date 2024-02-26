package it.unibo.jetpackjoyride.core.handler.obstacle;

import javafx.scene.image.Image;
import java.util.List;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;

/**
 * The {@link ObstacleView} class, more than dealing with the view of the 
 * {@link Obstacle} entities, is responsible for its process of animation.
 * With each call of the updateView() method, the correct animation sprite
 * frame is selected based on the entity status, action, movement 
 * characteristics, etc...
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public final class ObstacleView extends AbstractEntityView {

    /**
     * The width of the {@link Missile} warning.
     */
    private static final Double MISSILE_WARNING_X_DIMENSION = 80.0;

    /**
     * The height of the {@link Missile} warning.
     */
    private static final Double MISSILE_WARNING_Y_DIMENSION = 60.0;

    /**
     * The dimension change of the {@link Missile} warning.
     */
    private static final Double MISSILE_WARNING_DIMENSION_CHANGE = 5.0;

    /**
     * The speed at which the {@link Missile} warning changes dimension.
     */
    private static final Integer MISSILE_WARNING_DIMENSION_CHANGE_SPEED = 20;

    /**
     * The length of the {@link Missile} warning animation.
     */
    private static final Integer MISSILE_WARNING_ANIMATION_LENGHT = 4;

    /**
     * The length of the first sprite set of the {@link Missile} warning.
     */
    private static final Integer MISSILE_WARNING_FIRST_NUM_SPRITES = 3;

    /**
     * The length of the second sprite set of the {@link Missile} warning.
     */
    private static final Integer MISSILE_WARNING_SECOND_NUM_SPRITES = 2;

    /**
     * The number of ticks of the first, more relaxed {@link Missile} warning.
     */

    private static final Integer MISSILE_WARNING_FIRST_TICKS = 90;

    /**
     * The number of ticks of the second, more alarming {@link Missile} warning.
     */
    private static final Integer MISSILE_WARNING_SECOND_TICKS = 100;

    /**
     * The {@link Missile} width.
     */
    private static final Double MISSILE_X_DIMENSION = 160.0;

    /**
     * The {@link Missile} height.
     */
    private static final Double MISSILE_Y_DIMENSION = 45.0;

    /**
     * The length of the {@link Missile} animation.
     */
    private static final Integer MISSILE_ANIMATION_LENGHT = 4;

    /**
     * The length of the {@link Missile} sprite set.
     */
    private static final Integer MISSILE_NUM_SPRITES = 7;

    /**
     * The width of the {@link Missile} explosion animation.
     */
    private static final Double MISSILE_EXPLOSION_X_DIMENSION = 160.0;

    /**
     * The height of the {@link Missile} explosion animation.
     */
    private static final Double MISSILE_EXPLOSION_Y_DIMENSION = 160.0;

    /**
     * The length of the {@link Missile} explosion animation.
     */
    private static final Integer MISSILE_EXPLOSION_ANIMATION_LENGHT = 7;

    /**
     * The number of sprites in the {@link Missile} explosion sprite set.
     */
    private static final Integer MISSILE_EXPLOSION_NUM_SPRITES = 8;

    /**
     * The width of the {@link Zapper}.
     */
    private static final Double ZAPPER_X_DIMENSION = 215.0;

    /**
     * The height of the {@link Zapper}.
     */
    private static final Double ZAPPER_Y_DIMENSION = 90.0;

    /**
     * The length of the {@link Zapper} animation.
     */
    private static final Integer ZAPPER_ANIMATION_LENGHT = 6;

    /**
     * The number of sprites in the {@link Zapper} sprite set.
     */
    private static final Integer ZAPPER_NUM_SPRITES = 4;

    /**
     * The length of the {@link Zapper}'s breaking animation.
     */
    private static final Integer ZAPPER_BROKEN_ANIMATION_LENGHT = 4;

    /**
     * The number of sprites in the breaking {@link Zapper}'s sprite set.
     */
    private static final Integer ZAPPER_BROKEN_NUM_SPRITES = 16;

    /**
     * The width of the {@link Laser}.
     */
    private static final Double LASER_X_DIMENSION = 1150.0;

    /**
     * The height of the {@link Laser}.
     */
    private static final Double LASER_Y_DIMENSION = 30.0;

    /**
     * The length of the {@link Laser} animation.
     */
    private static final Integer LASER_ANIMATION_LENGHT = 8;

    /**
     * The number of sprites in the charging {@link Laser}'s sprite set.
     */
    private static final Integer LASER_CHARGING_NUM_SPRITES = 12;

    /**
     * The number of sprite in the {@link Laser} beam sprite set.
     */
    private static final Integer LASER_BEAM_NUM_SPRITES = 4;

    /**
     * A field that contains the current animation length.
     */
    private int animationLenght;

    /**
     * An array used to symbolize wether an obstacle is charing, if it is active,
     * or if it is deactivated.
     */
    private int[] animationCounter;

    /**
     * The constructor for this view class.
     * 
     * @param images the whole set of images that will be used by the obstacle type.
     */
    public ObstacleView(final List<Image> images) {
        super(images);
        this.animationCounter = new int[3]; // 0 counter for charging, 1 counter for active, 2 counter for deactivated
        this.animationLenght = 1;
    }

    @Override
    protected void animateFrames(final Entity entity) {
        @SuppressWarnings("unchecked")
        final Obstacle obstacle = (Obstacle) entity;

        switch (obstacle.getObstacleType()) {
            case MISSILE:
                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        if (animationCounter[0] < MISSILE_WARNING_FIRST_TICKS) {
                            this.setWidht(MISSILE_WARNING_X_DIMENSION);
                            this.setHeight(MISSILE_WARNING_Y_DIMENSION);
                        }
                        this.animationLenght = MISSILE_WARNING_ANIMATION_LENGHT;
                        this.setAnimationFrame((this.animationCounter[0]) / this.animationLenght
                                % MISSILE_WARNING_FIRST_NUM_SPRITES);

                        if (animationCounter[0] > MISSILE_WARNING_FIRST_TICKS
                                && animationCounter[0] > MISSILE_WARNING_SECOND_TICKS) {
                            this.setAnimationFrame(MISSILE_WARNING_FIRST_NUM_SPRITES + ((this.animationCounter[0])
                                    / this.animationLenght % MISSILE_WARNING_SECOND_NUM_SPRITES));
                            this.setWidht(this.getWidht() + MISSILE_WARNING_DIMENSION_CHANGE * (this.animationCounter[0]
                            % MISSILE_WARNING_DIMENSION_CHANGE_SPEED < MISSILE_WARNING_DIMENSION_CHANGE_SPEED
                                    / 2 ? 1.0 : -1.0));
                            this.setHeight(this.getHeight() + MISSILE_WARNING_DIMENSION_CHANGE * (this.animationCounter[0]
                                    % MISSILE_WARNING_DIMENSION_CHANGE_SPEED < MISSILE_WARNING_DIMENSION_CHANGE_SPEED
                                            / 2 ? 1.0 : -1.0));
                        } else {
                            if (animationCounter[0] > MISSILE_WARNING_SECOND_TICKS) {
                                this.animationCounter[0] = MISSILE_WARNING_FIRST_TICKS;
                                this.setWidht(MISSILE_WARNING_X_DIMENSION);
                                this.setHeight(MISSILE_WARNING_Y_DIMENSION);
                            }
                        }
                        this.animationCounter[0]++;
                        break;
                    case ACTIVE:
                        this.setWidht(MISSILE_X_DIMENSION);
                        this.setHeight(MISSILE_Y_DIMENSION);
                        this.animationLenght = MISSILE_ANIMATION_LENGHT;
                        this.setAnimationFrame(MISSILE_WARNING_FIRST_NUM_SPRITES + MISSILE_WARNING_SECOND_NUM_SPRITES
                                + ((animationCounter[1]) / animationLenght % MISSILE_NUM_SPRITES));
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.setWidht(MISSILE_EXPLOSION_X_DIMENSION);
                        this.setHeight(MISSILE_EXPLOSION_Y_DIMENSION);
                        this.animationLenght = MISSILE_EXPLOSION_ANIMATION_LENGHT;
                        this.setAnimationFrame(MISSILE_WARNING_FIRST_NUM_SPRITES + MISSILE_WARNING_SECOND_NUM_SPRITES
                                + MISSILE_NUM_SPRITES
                                + ((animationCounter[2]) / animationLenght % MISSILE_EXPLOSION_NUM_SPRITES));
                        this.animationCounter[2]++;
                        break;
                    default:
                        this.setAnimationFrame(0);
                        this.setWidht(0.0);
                        this.setHeight(0.0);
                        break;
                }

                break;
            case ZAPPER:
                this.setWidht(ZAPPER_X_DIMENSION);
                this.setHeight(ZAPPER_Y_DIMENSION);

                switch (obstacle.getEntityStatus()) {
                    case ACTIVE:
                        this.animationLenght = ZAPPER_ANIMATION_LENGHT;
                        this.setAnimationFrame(this.animationCounter[1] / this.animationLenght % ZAPPER_NUM_SPRITES);
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.animationLenght = ZAPPER_BROKEN_ANIMATION_LENGHT;
                        this.setAnimationFrame(ZAPPER_NUM_SPRITES
                                + ((this.animationCounter[2]) / this.animationLenght % ZAPPER_BROKEN_NUM_SPRITES));
                        if (this.getAnimationFrame() != ZAPPER_NUM_SPRITES + ZAPPER_BROKEN_NUM_SPRITES - 1) {
                            this.animationCounter[2]++;
                        }
                        break;
                    default:
                        this.setAnimationFrame(0);
                        this.setWidht(0.0);
                        this.setHeight(0.0);
                        break;
                }
                break;
            case LASER:
                this.setWidht(LASER_X_DIMENSION);
                this.setHeight(LASER_Y_DIMENSION);
                this.animationLenght = LASER_ANIMATION_LENGHT;

                switch (obstacle.getEntityStatus()) {
                    case CHARGING:
                        this.setAnimationFrame((this.animationCounter[0]) / this.animationLenght
                                % LASER_CHARGING_NUM_SPRITES);
                        this.animationCounter[0]++;
                        break;
                    case ACTIVE:
                        this.setAnimationFrame(LASER_CHARGING_NUM_SPRITES
                                + ((this.animationCounter[1]) / this.animationLenght % LASER_BEAM_NUM_SPRITES));
                        this.animationCounter[1]++;
                        break;
                    case DEACTIVATED:
                        this.setAnimationFrame(LASER_CHARGING_NUM_SPRITES - 1
                                + ((-animationCounter[2]) / this.animationLenght % LASER_CHARGING_NUM_SPRITES));
                        this.animationCounter[2]++;
                        break;
                    default:
                        this.setAnimationFrame(0);
                        this.setWidht(0.0);
                        this.setHeight(0.0);
                        break;
                }
                break;
            default:
                this.setWidht(0.0);
                this.setHeight(0.0);
                break;
        }

    }
}
