package it.unibo.jetpackjoyride.core.entities.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import javafx.scene.image.Image;

public class EntityControllerGeneratorImpl {
    private final static Integer MISSILESPRITES = 20;
    private final static Integer ZAPPERSPRITES = 20;
    private final static Integer LASERSPRITES = 16;
    private final static Integer LILSTOMPERSPRITES = 24;
    private final static Integer MRCUDDLESPRITES = 6;
    private final static Integer PROFITBIRDSPRITES = 12;
    private final static Integer DUKEFISHRONSPRITES = 12;
    private final static Integer VEHICLEPICKUPSPRITES = 21;
    private final static Integer SHIELDPICKUPSPRITES = 2;

    private final List<Image> obstacleImages;
    private final List<Image> powerupImages;
    private final List<Image> pickupImages;

    public EntityControllerGeneratorImpl() {
        this.obstacleImages = new ArrayList<>(); // 0-19 MISSILE | 20-39 ZAPPER | 40-55 LASER
        this.powerupImages = new ArrayList<>(); // 0-23 LILSTOMPER | 24-29 MRCUDDLE | 30-41 PROFITBIRD | 42-53 DUKEFISHRON
        this.pickupImages = new ArrayList<>(); // 0-20 VEHICLEPICKUP

        // MISSILE 20 total
        obstacleImages.addAll(imageLoader(MISSILESPRITES, "sprites/entities/obstacles/missile/missile_"));
        // ZAPPER 20 total
        obstacleImages.addAll(imageLoader(ZAPPERSPRITES, "sprites/entities/obstacles/zapper/zapper_"));
        // LASER 16 total
        obstacleImages.addAll(imageLoader(LASERSPRITES, "sprites/entities/obstacles/laser/laser_"));
        
        // LILSTOMPER 24 total
        powerupImages.addAll(imageLoader(LILSTOMPERSPRITES, "sprites/entities/powerups/lilstomper/lilstomper_"));
        // MRCUDDLE 6 total
        powerupImages.addAll(imageLoader(MRCUDDLESPRITES, "sprites/entities/powerups/mrcuddles/mrcuddles_"));
        // PROFITBIRD 12 total
        powerupImages.addAll(imageLoader(PROFITBIRDSPRITES, "sprites/entities/powerups/profitbird/profitbird_"));
        // DUKEFISHRON 12 total
        powerupImages.addAll(imageLoader(DUKEFISHRONSPRITES, "sprites/entities/powerups/dukefishron/dukefishron_"));

        // VEHICLEPICKUP
        pickupImages.addAll(imageLoader(VEHICLEPICKUPSPRITES, "sprites/entities/pickups/vehiclepickup/vehiclepickup_"));
        // VEHICLEPICKUP
        pickupImages.addAll(imageLoader(SHIELDPICKUPSPRITES, "sprites/entities/pickups/shieldpickup/shieldpickup_"));
        
    }

    public GenericController<Obstacle, ObstacleView> generateObstacleController(final Obstacle obstacle) {
        GenericController<Obstacle, ObstacleView> obstacleController;

        try {
            switch (obstacle.getObstacleType()) {
                case MISSILE: //Canon obstacle existing in the original game
                    obstacleController = new GenericController<>(obstacle, new ObstacleView(this.takeImages(this.obstacleImages,0,MISSILESPRITES-1)));
                    break;
                case ZAPPER: //Canon obstacle existing in the original game
                    obstacleController = new GenericController<>(obstacle, new ObstacleView(this.takeImages(this.obstacleImages,MISSILESPRITES, MISSILESPRITES+ZAPPERSPRITES-1)));
                    break;
                case LASER: //Canon obstacle existing in the original game
                    obstacleController = new GenericController<>(obstacle, new ObstacleView(this.takeImages(this.obstacleImages, MISSILESPRITES+ZAPPERSPRITES, MISSILESPRITES+ZAPPERSPRITES+LASERSPRITES-1)));
                    break;
                default:
                    throw new IllegalArgumentException("EntityControllerGenerator could not generate the obstacle controller.");
            }
            
        } catch (Exception e) {
            obstacleController = new GenericController<>(obstacle, new ObstacleView(this.takeImages(this.obstacleImages,0,MISSILESPRITES-1)));
        }
        
        return obstacleController;
    }

    public List<GenericController<PowerUp, PowerUpView>> generatePowerUpControllers(final List<PowerUp> powerUp) {
       List<GenericController<PowerUp, PowerUpView>> powerUpControllers = new ArrayList<>();
        
        try {
            switch (powerUp.get(0).getPowerUpType()) {
                case LILSTOMPER: //Canon powerup existing in the original game
                    powerUpControllers.add(new GenericController<>(powerUp.get(0), new PowerUpView(this.takeImages(this.powerupImages, 0,LILSTOMPERSPRITES-1))));
                    break;
                case MRCUDDLES: //Canon powerup existing in the original game
                    for (final var powerUpInstance : powerUp) {
                        powerUpControllers.add(new GenericController<>(powerUpInstance, new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES-1))));
                    }
                    break;
                case PROFITBIRD: //Canon powerup existing in the original game
                    powerUpControllers.add(new GenericController<>(powerUp.get(0), new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES-1))));
                    break;
                case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                    powerUpControllers.add(new GenericController<>(powerUp.get(0),  new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES+DUKEFISHRONSPRITES-1))));
                    break;
                default:
                throw new IllegalArgumentException("EntityControllerGenerator could not generate the powerup controller");
            }
            
        } catch (Exception e) {
            powerUpControllers.add(new GenericController<>(powerUp.get(0), new PowerUpView(this.takeImages(this.powerupImages, 0,LILSTOMPERSPRITES-1))));
        }
        return powerUpControllers;
    }

    public GenericController<PickUp, PickUpView> generatePickUpController(final PickUp pickUp) {
        GenericController<PickUp, PickUpView> pickUpController;

        try {
            switch (pickUp.getPickUpType()) {
                case VEHICLE: // Canon pickup existing in the original game
                pickUpController = new GenericController<>(pickUp, new PickUpView(this.takeImages(this.pickupImages, 0,VEHICLEPICKUPSPRITES-1)));
                    break;
                case SHIELD: // Canon pickup existing in the original game
                pickUpController = new GenericController<>(pickUp, new PickUpView(this.takeImages(this.pickupImages, VEHICLEPICKUPSPRITES,VEHICLEPICKUPSPRITES+SHIELDPICKUPSPRITES-1)));
                    //System.out.println(pickUp.getEntityModel().getEntityStatus());
                    break;
            
                default:
                throw new IllegalArgumentException("EntityControllerGenerator could not generate the pickup controller");
                
            }
        } catch (Exception e) {
            pickUpController = new GenericController<>(pickUp, new PickUpView(this.takeImages(this.pickupImages, 0,VEHICLEPICKUPSPRITES-1)));
        }
        return pickUpController;
    }

    private List<Image> imageLoader(final Integer numberOfImages, final String pathName) {
        try {
            return IntStream.range(0, numberOfImages).mapToObj(i -> new Image(getClass().getClassLoader().getResource(pathName + (i + 1) + ".png").toExternalForm())).toList();
        } catch (Exception e) {
            return List.of();
        }
    } 

    private List<Image> takeImages(final List<Image> images, final Integer fromIndex, final Integer toIndex) {
        return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> images.get(i)).toList();
    }
}
