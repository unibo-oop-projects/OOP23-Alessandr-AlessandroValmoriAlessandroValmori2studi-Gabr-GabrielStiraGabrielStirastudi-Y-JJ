package it.unibo.jetpackjoyride.core.handler.pickup;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.handler.generic.GenericView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PickUpView implements GenericView{

    private ImageView imageView;
    private Image[] images;
    private int animationFrame;
    private int animationLenght;
    private int animationCounter;

    public PickUpView(final Image[] images) {
        this.images = images;
        this.imageView = new ImageView();
        this.animationFrame = 0;
        this.animationLenght = 1;
    }

	@Override
	public void updateView(final Entity entity) {
        PickUp pickUp = (PickUp)entity;
        Double width;
        Double height;
        Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        switch (pickUp.getPickUpType()) {
            case VEHICLE:
                VehiclePickUp vehiclePickUp = (VehiclePickUp)pickUp;
                switch (pickUp.getEntityStatus()) {
                    case ACTIVE:
                        width = screenSizeX / 15;
                        height = screenSizeY / 9;
                        animationLenght = 4;
                        animationFrame = animationCounter / animationLenght % 8;
                        animationCounter++;
                        break;
                    case DEACTIVATED:
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
            default:
                width = 0.0;
                height = 0.0;
                break;
        }


		imageView.setX(pickUp.getEntityMovement().getCurrentPosition().get1() - width / 2);
        imageView.setY(pickUp.getEntityMovement().getCurrentPosition().get2() - height / 2);
        imageView.setRotate(pickUp.getEntityMovement().getRotation().get1());

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(images[animationFrame]);
	}

	@Override
	public ImageView getImageView() {
		return imageView;
	}
    
}
