package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.List;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.handler.entity.AbstractEntityView;
import javafx.scene.image.Image;

public class PickUpView extends AbstractEntityView {

    private final static Double VEHICLE_PICKUP_X_DIMENSION = 80.0;
    private final static Double VEHICLE_PICKUP_Y_DIMENSION = 80.0;
    private final static Integer VEHICLE_PICKUP_ANIMATION_LENGHT = 4;
    private final static Integer VEHICLE_PICKUP_NUM_SPRITES = 8;

    private final static Double VEHICLE_PICKUP_BANNER_X_DIMENSION = 1280.0;
    private final static Double VEHICLE_PICKUP_BANNER_Y_DIMENSION = 240.0;
    private final static Integer VEHICLE_PICKUP_BANNER_ANIMATION_LENGHT = 4;

    private final static Integer LILSTOMPER_BANNER_SPRITES = 3;
    private final static Integer MRCUDDLE_BANNER_SPRITES = 3;
    private final static Integer PROFIT_BIRD_BANNER_SPRITES = 3;
    private final static Integer DUKE_FISHRON_BANNER_SPRITES = 3;

    private final static Double SHIELD_PICKUP_X_DIMENSION = 80.0;
    private final static Double SHIELD_PICKUP_Y_DIMENSION = 80.0;
    private final static Integer SHIELD_PICKUP_ANIMATION_LENGHT = 2;

    private int animationLenght;
    private int animationCounter;

    public PickUpView(final List<Image> images) {
        super(images);
        this.animationFrame = 0;
        this.animationLenght = 1;
    }

    @Override
    protected void animateFrames(Entity entity) {
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
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + animationCounter / animationLenght % LILSTOMPER_BANNER_SPRITES;
                                break;
                            case MRCUDDLES:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES + animationCounter / animationLenght % MRCUDDLE_BANNER_SPRITES;
                                break;
                            case PROFITBIRD:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES + MRCUDDLE_BANNER_SPRITES + animationCounter / animationLenght % PROFIT_BIRD_BANNER_SPRITES;
                                break;
                            case DUKEFISHRON:
                                animationFrame = VEHICLE_PICKUP_NUM_SPRITES + LILSTOMPER_BANNER_SPRITES + MRCUDDLE_BANNER_SPRITES + PROFIT_BIRD_BANNER_SPRITES + animationCounter / animationLenght % DUKE_FISHRON_BANNER_SPRITES;
                                break;

                            default:
                                break;
                        }

                        animationCounter++;
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
                        width = SHIELD_PICKUP_X_DIMENSION;
                        height = SHIELD_PICKUP_Y_DIMENSION;
                        animationLenght = SHIELD_PICKUP_ANIMATION_LENGHT;
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
            default:
                width = 0.0;
                height = 0.0;
                break;

        }
    }
}
