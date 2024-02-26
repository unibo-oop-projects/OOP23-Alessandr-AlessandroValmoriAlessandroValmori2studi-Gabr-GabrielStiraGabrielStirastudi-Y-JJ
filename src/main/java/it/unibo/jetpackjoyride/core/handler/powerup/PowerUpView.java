package it.unibo.jetpackjoyride.core.handler.powerup;

import javafx.scene.image.Image;
import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;

/**
 * The {@link PowerUpView} class, more than dealing with the view of the
 * {@link PowerUp} entities, is responsible for its process of animation.
 * With each call of the updateView() method, the correct animation sprite
 * frame is selected based on the entity status, action, movement
 * characteristics, etc...
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public final class PowerUpView extends AbstractEntityView {

    /**
     * The width of the {@link LilStomper} power-up.
     */
    private static final Double LILSTOMPER_X_DIMENSION = 320.0;

    /**
     * The height of the {@link LilStomper} power-up.
     */
    private static final Double LILSTOMPER_Y_DIMENSION = 240.0;

    /**
     * The speed of {@link LilStomper}'s walking animation.
     */
    private static final Integer LILSTOMPER_WALKING_ANIMATION_SPEED = 7;

    /**
     * The number of sprites in {@link LilStomper}'s walking animation.
     */
    private static final Integer LILSTOMPER_WALKING_NUM_SPRITES = 6;

    /**
     * The speed of {@link LilStomper}'s ascending animation.
     */
    private static final Integer LILSTOMPER_ASCENDING_ANIMATION_SPEED = 4;

    /**
     * The number of sprites in {@link LilStomper}'s ascending animation.
     */
    private static final Integer LILSTOMPER_ASCENDING_NUM_SPRITES = 7;

    /**
     * The speed of {@link LilStomper}'s gliding animation.
     */
    private static final Integer LILSTOMPER_GLIDING_ANIMATION_SPEED = 6;

    /**
     * The number of sprites in {@link LilStomper}'s gliding animation.
     */
    private static final Integer LILSTOMPER_GLIDING_NUM_SPRITES = 4;

    /**
     * The speed of {@link LilStomper}'s descending animation.
     */
    private static final Integer LILSTOMPER_DESCENDING_ANIMATION_SPEED = 6;

    /**
     * The number of sprites in {@link LilStomper}'s descending animation.
     */
    private static final Integer LILSTOMPER_DESCENDING_NUM_SPRITES = 3;

    /**
     * The speed of {@link LilStomper}'s landing animation.
     */
    private static final Integer LILSTOMPER_LANDING_ANIMATION_SPEED = 4;

    /**
     * The number of sprites in {@link LilStomper}'s landing animation.
     */
    private static final Integer LILSTOMPER_LANDING_NUM_SPRITES = 5;

    /**
     * The width of the {@link MrCuddles} power-up.
     */
    private static final Double MRCUDDLES_X_DIMENSION = 640.0;

    /**
     * The height of the {@link MrCuddles} power-up.
     */
    private static final Double MRCUDDLES_Y_DIMENSION = 360.0;

    /**
     * The length of {@link MrCuddles}' animation.
     */
    private static final Integer MRCUDDLES_ANIMATION_LENGHT = 5;

    /**
     * The number of sprites in {@link MrCuddles}' static animation.
     */
    private static final Integer MRCUDDLE_STATIC_NUM_SPRITES = 1;

    /**
     * The number of sprites in {@link MrCuddles}' roaring animation.
     */
    private static final Integer MRCUDDLE_ROARING_NUM_SPRITES = 4;

    /**
     * The width of the {@link ProfitBird} power-up.
     */
    private static final Double PROFITBIRD_X_DIMENSION = 180.0;

    /**
     * The height of the {@link ProfitBird} power-up.
     */
    private static final Double PROFITBIRD_Y_DIMENSION = 120.0;

    /**
     * The length of {@link ProfitBird}'s walking animation.
     */
    private static final Integer PROFITBIRD_WALKING_ANIMATION_LENGHT = 7;

    /**
     * The number of sprites in {@link ProfitBird}'s walking animation.
     */
    private static final Integer PROFITBIRD_WALKING_NUM_SPRITES = 3;

    /**
     * The length of {@link ProfitBird}'s jumping animation.
     */
    private static final Integer PROFITBIRD_JUMPING_ANIMATION_LENGHT = 6;

    /**
     * The number of sprites in {@link ProfitBird}'s jumping animation.
     */
    private static final Integer PROFITBIRD_JUMPING_NUM_SPRITES = 1;

    /**
     * The length of {@link ProfitBird}'s ascending animation.
     */
    private static final Integer PROFITBIRD_ASCENDING_ANIMATION_LENGHT = 1;

    /**
     * The number of sprites in {@link ProfitBird}'s ascending animation.
     */
    private static final Integer PROFITBIRD_ASCENDING_NUM_SPRITES = 8;

    /**
     * The width of the {@link DukeFishron} power-up.
     */
    private static final Double DUKEFISHRON_X_DIMENSION = 320.0;

    /**
     * The height of the {@link DukeFishron} power-up.
     */
    private static final Double DUKEFISHRON_Y_DIMENSION = 240.0;

    /**
     * The length of {@link DukeFishron}'s animation.
     */
    private static final Integer DUKEFISHRON_ANIMATION_LENGHT = 6;

    /**
     * The number of sprites in {@link DukeFishron}'s ascending animation.
     */
    private static final Integer DUKEFISHRON_ASCENDING_NUM_SPRITES = 6;

    /**
     * 
     */
    private static final Integer NUMBER_STATES_OF_POWERUP = 5;

    /**
     * A field that contains the current animation length.
     */
    private int animationLenght;

    /**
     * An array containing animation counters for different states of the power-up.
     * Index 0: counter for walking animation
     * Index 1: counter for ascending animation
     * Index 2: counter for gliding animation
     * Index 3: counter for descending animation
     * Index 4: counter for landing animation
     */
    private int[] animationCounter;

    /**
     * Constructs a new PowerUpView with the given set of images.
     *
     * @param images The list of images representing the power-up.
     */
    public PowerUpView(final List<Image> images) {
        super(images);
        this.animationCounter = new int[NUMBER_STATES_OF_POWERUP];
        this.animationLenght = 1;
    }

    @Override
    @SuppressFBWarnings("INT")
    protected void animateFrames(final Entity entity) {
        final PowerUp powerUp = (PowerUp) entity;

        switch (powerUp.getPowerUpType()) {
            case LILSTOMPER:
                this.setWidht(LILSTOMPER_X_DIMENSION);
                this.setHeight(LILSTOMPER_Y_DIMENSION);
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        this.animationLenght = LILSTOMPER_WALKING_ANIMATION_SPEED;
                        this.setAnimationFrame((this.animationCounter[0]) / this.animationLenght
                                % LILSTOMPER_WALKING_NUM_SPRITES);
                        this.animationCounter[0]++;
                        break;

                    case ASCENDING:
                        this.animationLenght = LILSTOMPER_ASCENDING_ANIMATION_SPEED;
                        this.setAnimationFrame(LILSTOMPER_WALKING_NUM_SPRITES
                                + ((this.animationCounter[1]) / this.animationLenght
                                        % LILSTOMPER_ASCENDING_NUM_SPRITES));
                        if (this.animationCounter[1] != this.animationLenght * LILSTOMPER_ASCENDING_NUM_SPRITES - 1) {
                            this.animationCounter[1]++;
                        }
                        break;
                    case GLIDING:
                        this.animationCounter[3] = 0;
                        this.animationLenght = LILSTOMPER_GLIDING_ANIMATION_SPEED;
                        this.setAnimationFrame(LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + ((this.animationCounter[2]) / this.animationLenght % LILSTOMPER_GLIDING_NUM_SPRITES));
                        this.animationCounter[2]++;
                        break;

                    case DESCENDING:
                        this.animationCounter[1] = 0;
                        this.animationLenght = LILSTOMPER_DESCENDING_ANIMATION_SPEED;
                        this.setAnimationFrame(LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + LILSTOMPER_GLIDING_NUM_SPRITES
                                + ((this.animationCounter[3]) / this.animationLenght
                                        % LILSTOMPER_DESCENDING_NUM_SPRITES));
                        if (this.animationCounter[3] != this.animationLenght * LILSTOMPER_DESCENDING_NUM_SPRITES - 1) {
                            this.animationCounter[3]++;
                        }
                        break;

                    case LANDING: // 20 - 24
                        this.animationLenght = LILSTOMPER_LANDING_ANIMATION_SPEED;
                        this.setAnimationFrame(LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + LILSTOMPER_GLIDING_NUM_SPRITES + LILSTOMPER_DESCENDING_NUM_SPRITES
                                + ((this.animationCounter[4]) / this.animationLenght % LILSTOMPER_LANDING_NUM_SPRITES));
                        if (this.animationCounter[4] == this.animationLenght * LILSTOMPER_LANDING_NUM_SPRITES - 1) {
                            for (int i = 0; i < 4; i++) {
                                this.animationCounter[i] = 0;
                            }
                            this.animationCounter[4] = 0;
                        } else {
                            this.animationCounter[4]++;
                        }
                        break;

                    default:
                        this.setAnimationFrame(0);
                        break;
                }

                break;
            case MRCUDDLES:
                this.setWidht(MRCUDDLES_X_DIMENSION);
                this.setHeight(MRCUDDLES_Y_DIMENSION);
                if (powerUp.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                    switch (powerUp.getPerformingAction()) {
                        case ASCENDING:
                            this.animationLenght = MRCUDDLES_ANIMATION_LENGHT;
                            if (this.animationCounter[1] != 0) {
                                this.animationCounter[1]--;
                                this.setAnimationFrame((this.animationCounter[1]) / this.animationLenght
                                        % MRCUDDLE_ROARING_NUM_SPRITES);
                            } else {
                                this.setAnimationFrame((this.animationCounter[1]) / this.animationLenght
                                        % MRCUDDLE_STATIC_NUM_SPRITES);
                            }

                            break;
                        case DESCENDING:
                            this.animationLenght = MRCUDDLES_ANIMATION_LENGHT;
                            this.setAnimationFrame(MRCUDDLE_STATIC_NUM_SPRITES
                                    + ((this.animationCounter[1]) / this.animationLenght
                                            % MRCUDDLE_ROARING_NUM_SPRITES));
                            if (this.animationCounter[1] != this.animationLenght * MRCUDDLE_ROARING_NUM_SPRITES - 1) {
                                this.animationCounter[1]++;
                            }
                            break;
                        default:
                            this.setAnimationFrame(0);
                            break;
                    }
                } else {
                    this.setAnimationFrame(MRCUDDLE_STATIC_NUM_SPRITES + MRCUDDLE_ROARING_NUM_SPRITES);
                }
                break;

            case PROFITBIRD:
                this.setWidht(PROFITBIRD_X_DIMENSION);
                this.setHeight(PROFITBIRD_Y_DIMENSION);
                switch (powerUp.getPerformingAction()) {
                    case WALKING:
                        this.animationLenght = PROFITBIRD_WALKING_ANIMATION_LENGHT;
                        this.setAnimationFrame((this.animationCounter[0]) / this.animationLenght
                                % PROFITBIRD_WALKING_NUM_SPRITES);
                        this.animationCounter[0]++;
                        break;

                    case JUMPING:
                        this.animationLenght = PROFITBIRD_JUMPING_ANIMATION_LENGHT;
                        this.setAnimationFrame(PROFITBIRD_WALKING_NUM_SPRITES);
                        this.animationCounter[1] = 0;
                        break;

                    case ASCENDING:
                        this.animationLenght = PROFITBIRD_ASCENDING_ANIMATION_LENGHT;
                        if (this.animationCounter[1] != this.animationLenght * PROFITBIRD_ASCENDING_NUM_SPRITES - 1) {
                            this.animationCounter[1]++;
                            this.setAnimationFrame(PROFITBIRD_WALKING_NUM_SPRITES + PROFITBIRD_JUMPING_NUM_SPRITES
                                    + ((this.animationCounter[1]) / this.animationLenght
                                            % PROFITBIRD_ASCENDING_NUM_SPRITES));
                        } else {
                            this.setAnimationFrame(PROFITBIRD_WALKING_NUM_SPRITES);
                        }
                        break;
                    default:
                        this.setAnimationFrame(PROFITBIRD_WALKING_NUM_SPRITES);
                        break;
                }
                break;

            case DUKEFISHRON:
                this.setWidht(DUKEFISHRON_X_DIMENSION);
                this.setHeight(DUKEFISHRON_Y_DIMENSION);
                switch (powerUp.getPerformingAction()) {
                    case ASCENDING:
                        this.animationLenght = DUKEFISHRON_ANIMATION_LENGHT;
                        this.setAnimationFrame((this.animationCounter[0]) / this.animationLenght
                                % DUKEFISHRON_ASCENDING_NUM_SPRITES);
                        this.animationCounter[0]++;
                        break;
                    case DESCENDING:
                        this.animationLenght = DUKEFISHRON_ANIMATION_LENGHT;
                        this.setAnimationFrame(DUKEFISHRON_ASCENDING_NUM_SPRITES
                                + ((this.animationCounter[0]) / this.animationLenght
                                        % DUKEFISHRON_ASCENDING_NUM_SPRITES));
                        this.animationCounter[0]++;
                        break;
                    default:
                        this.setAnimationFrame(0);
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
