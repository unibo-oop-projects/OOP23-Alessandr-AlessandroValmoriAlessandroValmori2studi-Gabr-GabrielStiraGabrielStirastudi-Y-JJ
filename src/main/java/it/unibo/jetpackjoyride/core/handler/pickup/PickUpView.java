package it.unibo.jetpackjoyride.core.handler.pickup;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.handler.generic.GenericView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PickUpView implements GenericView {

    private final ImageView imageView;
    private final List<Image> images;
    private int animationFrame;
    private int animationLenght;
    private int animationCounter;

    public PickUpView(final List<Image> images) {
        this.images = images;
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.animationLenght = 1;
    }

    @Override
    public void updateView(final Entity entity) {
        final PickUp pickUp = (PickUp) entity;
        Double width;
        Double height;
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenSizeX = infoResolution.getScreenWidth();
        final Double screenSizeY = infoResolution.getScreenHeight();

        switch (pickUp.getPickUpType()) {
            case VEHICLE:
                final VehiclePickUp vehiclePickUp = (VehiclePickUp) pickUp;
                switch (pickUp.getEntityStatus()) {
                    case ACTIVE:
                        width = screenSizeX / 15;
                        height = screenSizeY / 9;
                        animationLenght = 4;
                        animationFrame = animationCounter / animationLenght % 8;
                        animationCounter++;
                        break;
                    case DEACTIVATED:
                        System.out.println("YAAAAAAAAAAAA");
                        width = screenSizeX;
                        height = screenSizeY / 3;
                        animationLenght = 4;
                        switch (vehiclePickUp.getVehicleSpawn()) {
                            case LILSTOMPER:
                                animationFrame = 8 + animationCounter / animationLenght % 3;
                                break;
                            case MRCUDDLES:
                                animationFrame = 11 + animationCounter / animationLenght % 3;
                                break;
                            case PROFITBIRD:
                                animationFrame = 14 + animationCounter / animationLenght % 3;
                                break;
                            case DUKEFISHRON:
                                animationFrame = 17 + animationCounter / animationLenght % 3;
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
                    width = screenSizeX / 15;
                    height = screenSizeY / 9;
                    animationLenght = 2;
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

        final double scaleX = infoResolution.getScreenWidth()/infoResolution.getDefaultWidth();
        final double scaleY = infoResolution.getScreenHeight()/infoResolution.getDefaultHeight();

        imageView.setX(pickUp.getEntityMovement().getPosition().get1()*scaleX - width / 2);
        imageView.setY(pickUp.getEntityMovement().getPosition().get2()*scaleY - height / 2);
        imageView.setRotate(pickUp.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images.get(animationFrame));
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

}
