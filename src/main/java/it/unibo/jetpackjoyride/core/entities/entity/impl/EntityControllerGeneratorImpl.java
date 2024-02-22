package it.unibo.jetpackjoyride.core.entities.entity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.core.movement.Movement;
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
    private final EntityModelGenerator entityModelFactory;

    public EntityControllerGeneratorImpl() {
        this.entityModelFactory = new EntityModelGeneratorImpl();
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

    public GenericController<Obstacle, ObstacleView> generateObstacleController(final ObstacleType obstacleType, final Movement obstacleMovement) {
        final Obstacle obstacleModel = this.entityModelFactory.generateObstacle(obstacleType, obstacleMovement);
        final GenericController<Obstacle, ObstacleView> obstacle;
        
        switch (obstacleType) {
            case MISSILE: //Canon obstacle existing in the original game
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages,0,MISSILESPRITES-1)));
                break;
            case ZAPPER: //Canon obstacle existing in the original game
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages,MISSILESPRITES, MISSILESPRITES+ZAPPERSPRITES-1)));
                break;
            case LASER: //Canon obstacle existing in the original game
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages, MISSILESPRITES+ZAPPERSPRITES, MISSILESPRITES+ZAPPERSPRITES+LASERSPRITES-1)));
                break;
            default:
                throw new IllegalArgumentException("EntityControllerGenerator could not generate the obstacle controller");
        }

        return obstacle;
    }

    public List<GenericController<PowerUp, PowerUpView>> generatePowerUpControllers(final PowerUpType powerUpType) {
        final List<PowerUp> powerUpModel = new ArrayList<>(this.entityModelFactory.generatePowerUp(powerUpType));
        final List<GenericController<PowerUp, PowerUpView>> powerUp = new ArrayList<>();
        
        switch (powerUpType) {
            case LILSTOMPER: //Canon powerup existing in the original game
                powerUp.add(new GenericController<>(powerUpModel.get(0), new PowerUpView(this.takeImages(this.powerupImages, 0,LILSTOMPERSPRITES-1))));
                break;
            case MRCUDDLES: //Canon powerup existing in the original game
                for (final var powerUpInstance : powerUpModel) {
                    powerUp.add(new GenericController<>(powerUpInstance, new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES-1))));
                }
                break;
            case PROFITBIRD: //Canon powerup existing in the original game
                powerUp.add(new GenericController<>(powerUpModel.get(0), new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES-1))));
                break;
            case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                powerUp.add(new GenericController<>(powerUpModel.get(0),  new PowerUpView(this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES+DUKEFISHRONSPRITES-1))));
                break;
            default:
            throw new IllegalArgumentException("EntityControllerGenerator could not generate the powerup controller");
        }
        return powerUp;
    }

    public GenericController<PickUp, PickUpView> generatePickUpController(final PickUpType pickUpType) {
        final PickUp pickUpModel = this.entityModelFactory.generatePickUp(pickUpType);
        final GenericController<PickUp, PickUpView> pickUp;

        switch (pickUpType) {
            case VEHICLE: // Canon pickup existing in the original game
                pickUp = new GenericController<>(pickUpModel, new PickUpView(this.takeImages(this.pickupImages, 0,VEHICLEPICKUPSPRITES-1)));
                break;
            case SHIELD: // Canon pickup existing in the original game
                pickUp = new GenericController<>(pickUpModel, new PickUpView(this.takeImages(this.pickupImages, VEHICLEPICKUPSPRITES,VEHICLEPICKUPSPRITES+SHIELDPICKUPSPRITES-1)));
                System.out.println(pickUp.getEntityModel().getEntityStatus());
                break;
        
            default:
            throw new IllegalArgumentException("EntityControllerGenerator could not generate the pickup controller");
            
        }
        return pickUp;
    }

    private List<Image> imageLoader(final Integer numberOfImages, final String pathName) {
        return IntStream.range(0, numberOfImages).mapToObj(i -> new Image(getClass().getClassLoader().getResource(pathName + (i + 1) + ".png").toExternalForm())).toList();
    } 

    private List<Image> takeImages(final List<Image> images, final Integer fromIndex, final Integer toIndex) {
        return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> images.get(i)).toList();
    }
}
