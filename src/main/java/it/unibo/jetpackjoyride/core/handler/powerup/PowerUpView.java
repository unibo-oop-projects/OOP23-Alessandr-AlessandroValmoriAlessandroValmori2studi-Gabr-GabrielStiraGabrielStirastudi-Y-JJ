package it.unibo.jetpackjoyride.core.handler.powerup;

import javafx.scene.image.Image;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;

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
        this.animationFrame = 0;
        this.animationCounter = new int[5];
        this.animationLenght = 1;
    }

    @Override
    @SuppressFBWarnings("INT")
    protected void animateFrames(final Entity entity) {
        final PowerUp powerUp = (PowerUp) entity;

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
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES
                                + ((animationCounter[1]) / animationLenght % LILSTOMPER_ASCENDING_NUM_SPRITES);
                        if (animationCounter[1] != animationLenght * LILSTOMPER_ASCENDING_NUM_SPRITES - 1) {
                            animationCounter[1]++;
                        }
                        break;
                    case GLIDING:
                        animationCounter[3] = 0;
                        animationLenght = LILSTOMPER_GLIDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + ((animationCounter[2]) / animationLenght % LILSTOMPER_GLIDING_NUM_SPRITES);
                        animationCounter[2]++;
                        break;

                    case DESCENDING:
                        animationCounter[1] = 0;
                        animationLenght = LILSTOMPER_DESCENDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + LILSTOMPER_GLIDING_NUM_SPRITES
                                + ((animationCounter[3]) / animationLenght % LILSTOMPER_DESCENDING_NUM_SPRITES);
                        if (animationCounter[3] != animationLenght * LILSTOMPER_DESCENDING_NUM_SPRITES - 1) {
                            animationCounter[3]++;
                        }
                        break;

                    case LANDING: // 20 - 24
                        animationLenght = LILSTOMPER_LANDING_ANIMATION_SPEED;
                        animationFrame = LILSTOMPER_WALKING_NUM_SPRITES + LILSTOMPER_ASCENDING_NUM_SPRITES
                                + LILSTOMPER_GLIDING_NUM_SPRITES + LILSTOMPER_DESCENDING_NUM_SPRITES
                                + ((animationCounter[4]) / animationLenght % LILSTOMPER_LANDING_NUM_SPRITES);
                        if (animationCounter[4] == animationLenght * LILSTOMPER_LANDING_NUM_SPRITES - 1) {
                            for (int i = 0; i < 4; i++) {
                                animationCounter[i] = 0;
                            }
                            animationCounter[4] = 0;
                        } else {
                            animationCounter[4]++;
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
                            if (animationCounter[1] != 0) {
                                animationCounter[1]--;
                                animationFrame = (animationCounter[1]) / animationLenght % MRCUDDLE_ROARING_NUM_SPRITES;
                            } else {
                                animationFrame = (animationCounter[1]) / animationLenght % MRCUDDLE_STATIC_NUM_SPRITES;
                            }

                            break;
                        case DESCENDING:
                            animationLenght = MRCUDDLES_ANIMATION_LENGHT;
                            animationFrame = MRCUDDLE_STATIC_NUM_SPRITES
                                    + ((animationCounter[1]) / animationLenght % MRCUDDLE_ROARING_NUM_SPRITES);
                            if (animationCounter[1] != animationLenght * MRCUDDLE_ROARING_NUM_SPRITES - 1) {
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
                        if (animationCounter[1] != animationLenght * PROFITBIRD_ASCENDING_NUM_SPRITES - 1) {
                            animationCounter[1]++;
                            animationFrame = PROFITBIRD_WALKING_NUM_SPRITES + PROFITBIRD_JUMPING_NUM_SPRITES
                                    + ((animationCounter[1]) / animationLenght % PROFITBIRD_ASCENDING_NUM_SPRITES);
                        } else {
                            animationFrame = PROFITBIRD_WALKING_NUM_SPRITES;
                        }

                        break;

                    default:
                        animationFrame = PROFITBIRD_WALKING_NUM_SPRITES;
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
                        animationFrame = DUKEFISHRON_ASCENDING_NUM_SPRITES
                                + ((animationCounter[0]) / animationLenght % DUKEFISHRON_ASCENDING_NUM_SPRITES);
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
