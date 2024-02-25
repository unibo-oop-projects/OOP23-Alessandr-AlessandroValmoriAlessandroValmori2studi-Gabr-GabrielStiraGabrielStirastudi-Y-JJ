package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.List;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import javafx.scene.image.Image;

public final class PickUpView extends AbstractEntityView {

    /**
     * The width of the {@link VehiclePickUp}.
     */
    private static final Double VEHICLE_PICKUP_X_DIMENSION = 80.0;

    /**
     * The height of the {@link VehiclePickUp}.
     */
    private static final Double VEHICLE_PICKUP_Y_DIMENSION = 80.0;

    /**
     * The length of the {@link VehiclePickUp} animation.
     */
    private static final Integer VEHICLE_PICKUP_ANIMATION_LENGHT = 4;

    /**
     * The number of sprites in the {@link VehiclePickUp}'s spriteset.
     */
    private static final Integer VEHICLE_PICKUP_NUM_SPRITES = 8;

    /**
     * The width of the {@link VehiclePickUp}'s banner .
     */
    private static final Double VEHICLE_PICKUP_BANNER_X_DIMENSION = 1280.0;

    /**
     * The height of the {@link VehiclePickUp} .
     */
    private static final Double VEHICLE_PICKUP_BANNER_Y_DIMENSION = 240.0;

    /**
     * The length of the {@link VehiclePickUp}'s animation .
     */
    private static final Integer VEHICLE_PICKUP_BANNER_ANIMATION_LENGHT = 4;

    /**
     * The number of sprites in the {@link LilStomper} banner spriteset.
     */
    private static final Integer LILSTOMPER_BANNER_SPRITES = 3;

    /**
     * The number of sprites in the {@link MrCuddles} banner spriteset.
     */
    private static final Integer MRCUDDLE_BANNER_SPRITES = 3;

    /**
     * The number of sprites in the {@link ProfitBird} banner spriteset.
     */
    private static final Integer PROFIT_BIRD_BANNER_SPRITES = 3;

    /**
     * The number of sprites in the {@link DukeFishron} banner spriteset.
     */
    private static final Integer DUKE_FISHRON_BANNER_SPRITES = 3;

    /**
     * The width of the shield pickup.
     */
    private static final Double SHIELD_PICKUP_X_DIMENSION = 80.0;

    /**
     * The height of the shield pickup .
     */
    private static final Double SHIELD_PICKUP_Y_DIMENSION = 80.0;

    /**
     * The number of sprites in the shield pickup spriteset.
     */
    private static final Integer SHIELD_PICKUP_ANIMATION_LENGHT = 2;

    /**
     * The dimension change of the shield pickup animation .
     */
    private static final Double SHIELD_PICKUP_DIMENSION_CHANGE = 2.0;

    /**
     * The speed of the dimension variation of the shield pickup .
     */
    private static final Integer SHIELD_PICKUP_DIMENSION_CHANGE_SPEED = 30;

    /**
     * A field that contains the current animation length .
     */
    private int animationLenght;

    /**
     * A counter used to iterate over the sprite set.
     */
    private int animationCounter;

    /**
     * the constructor of this view class.
     * 
     * @param images the whole set of images that will be used by the pickup type.
     */
    public PickUpView(final List<Image> images) {
        super(images);
        this.animationFrame = 0;
        this.animationLenght = 1;
    }

    @Override
    protected void animateFrames(final Entity entity) {
        @SuppressWarnings("unchecked")
        final PickUp pickUp = (PickUp) entity;

        switch (pickUp.getPickUpType()) {
            case VEHICLE:
                final VehiclePickUp vehiclePickUp = (VehiclePickUp) pickUp;
                switch (pickUp.getEntityStatus()) {
                    case ACTIVE:
                        width = VEHICLE_PICKUP_X_DIMENSION;
                        height = VEHICLE_PICKUP_Y_DIMENSION;
                        animationLenght = VEHICLE_PICKUP_ANIMATION_LENGHT;
                        animationFrame = animationCounter / animationLenght % VEHICLE_PICKUP_NUM_SPRITES;
                        animationCounter++;
                        break;
                    case DEACTIVATED:
                        width = VEHICLE_PICKUP_BANNER_X_DIMENSION;
                        height = VEHICLE_PICKUP_BANNER_Y_DIMENSION;
                        animationLenght = VEHICLE_PICKUP_BANNER_ANIMATION_LENGHT;

                        switch (vehiclePickUp.getVehicleSpawn()) {
                            case LILSTOMPER:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES
                                        + animationCounter / animationLenght % LILSTOMPER_BANNER_SPRITES;
                                break;
                            case MRCUDDLES:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES
                                        + animationCounter / animationLenght % MRCUDDLE_BANNER_SPRITES;
                                break;
                            case PROFITBIRD:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES
                                        + MRCUDDLE_BANNER_SPRITES
                                        + animationCounter / animationLenght % PROFIT_BIRD_BANNER_SPRITES;
                                break;
                            case DUKEFISHRON:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES
                                        + MRCUDDLE_BANNER_SPRITES + PROFIT_BIRD_BANNER_SPRITES
                                        + animationCounter / animationLenght % DUKE_FISHRON_BANNER_SPRITES;
                                break;

                            default:
                                break;
                        }
                        break;
                    default:
                        width = 0.0;
                        height = 0.0;
                        break;
                }
                break;

            case SHIELD:
                switch (pickUp.getEntityStatus()) {
                    case ACTIVE:
                        animationLenght = SHIELD_PICKUP_ANIMATION_LENGHT;
                        if (this.animationCounter == 0) {
                            this.width = SHIELD_PICKUP_X_DIMENSION;
                            this.height = SHIELD_PICKUP_Y_DIMENSION;
                        } else {
                            this.width += SHIELD_PICKUP_DIMENSION_CHANGE * (this.animationCounter
                                    % SHIELD_PICKUP_DIMENSION_CHANGE_SPEED < SHIELD_PICKUP_DIMENSION_CHANGE_SPEED / 2
                                            ? 1.0
                                            : -1.0);
                            this.height += SHIELD_PICKUP_DIMENSION_CHANGE * (this.animationCounter
                                    % SHIELD_PICKUP_DIMENSION_CHANGE_SPEED < SHIELD_PICKUP_DIMENSION_CHANGE_SPEED / 2
                                            ? 1.0
                                            : -1.0);
                        }
                        break;
                    case DEACTIVATED:
                        width = 0.0;
                        height = 0.0;
                        break;
                    default:
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
        this.animationCounter++;
    }
}
