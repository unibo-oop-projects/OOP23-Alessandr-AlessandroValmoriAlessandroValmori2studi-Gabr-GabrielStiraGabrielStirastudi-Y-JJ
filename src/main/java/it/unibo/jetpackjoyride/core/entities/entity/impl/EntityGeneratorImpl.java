package it.unibo.jetpackjoyride.core.entities.entity.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.*;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddlesGenerator;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;

public class EntityGeneratorImpl implements EntityGenerator {
    private final List<Image> obstacleImages;
    private final List<Image> powerupImages;
    private final List<Image> pickupImages;

    public EntityGeneratorImpl() {
        this.obstacleImages = new ArrayList<>(); // 0-19 MISSILE | 20-39 ZAPPER | 40-55 LASER
        this.powerupImages = new ArrayList<>(); // 0-23 LILSTOMPER | 24-29 MRCUDDLE | 30-41 PROFITBIRD | 42-53 DUKEFISHRON
        this.pickupImages = new ArrayList<>(); // 0-20 VEHICLEPICKUP

        // MISSILE 20 total
        obstacleImages.addAll(imageLoader(20, "sprites/entities/obstacles/missile/missile_"));
        // ZAPPER 20 total
        obstacleImages.addAll(imageLoader(20, "sprites/entities/obstacles/zapper/zapper_"));
        // LASER 16 total
        obstacleImages.addAll(imageLoader(16, "sprites/entities/obstacles/laser/laser_"));
        
        // LILSTOMPER 24 total
        powerupImages.addAll(imageLoader(24, "sprites/entities/powerups/lilstomper/lilstomper_"));
        // MRCUDDLE 6 total
        powerupImages.addAll(imageLoader(6, "sprites/entities/powerups/mrcuddles/mrcuddles_"));
        // PROFITBIRD 12 total
        powerupImages.addAll(imageLoader(12, "sprites/entities/powerups/profitbird/profitbird_"));
        // DUKEFISHRON 12 total
        powerupImages.addAll(imageLoader(12, "sprites/entities/powerups/dukefishron/dukefishron_"));

        // VEHICLEPICKUP
        pickupImages.addAll(imageLoader(21, "sprites/entities/pickups/vehiclepickup/vehiclepickup_"));

        
    }

    @Override
    public GenericController<Obstacle, ObstacleView> generateObstacle(final ObstacleType obstacleType, final Movement obstacleMovement) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Hitbox obstacleHitbox;
        final Obstacle obstacleModel;
        final GenericController<Obstacle, ObstacleView> obstacle;
        
        switch (obstacleType) {
            case MISSILE: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX / 32, screenSizeY / 48));
                obstacleModel = new Missile(obstacleMovement, obstacleHitbox);
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages,0,19)));
                break;
            case ZAPPER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX / 8, screenSizeY / 24));
                obstacleModel = new Zapper(obstacleMovement, obstacleHitbox);
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages,20, 39)));
                break;
            case LASER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX, screenSizeY / 30));
                obstacleModel = new Laser(obstacleMovement, obstacleHitbox);
                obstacle = new GenericController<>(obstacleModel, new ObstacleView(this.takeImages(this.obstacleImages, 40, 55)));
                break;
            default:
                throw new IllegalArgumentException("EntityGenerator could not generate the obstacle");
        }

        return obstacle;
    }

    @Override
    public List<GenericController<PowerUp, PowerUpView>> generatePowerUp(final PowerUpType powerUpType) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Movement powerUpMovement;
        Hitbox powerUpHitbox;
        final List<PowerUp> powerUpModel = new ArrayList<>();
        final List<GenericController<PowerUp, PowerUpView>> powerUp = new ArrayList<>();
        
        switch (powerUpType) {
            case LILSTOMPER: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 8, screenSizeY / 5));
                powerUpModel.add(new LilStomper(powerUpMovement, powerUpHitbox));
                powerUp.add(new GenericController<>(powerUpModel.get(0), new PowerUpView(this.takeImages(this.powerupImages, 0,23))));
                break;

            case MRCUDDLES: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX/5, screenSizeY/8), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), List.of(MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModel.addAll(new MrCuddlesGenerator(powerUpMovement, powerUpHitbox).generateMrCuddle());

                for (final var powerUpInstance : powerUpModel) {
                    powerUp.add(new GenericController<>(powerUpInstance, new PowerUpView(this.takeImages(this.powerupImages, 24,29))));
                }
                break;

            case PROFITBIRD: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModel.add(new ProfitBird(powerUpMovement, powerUpHitbox));
                powerUp.add(new GenericController<>(powerUpModel.get(0), new PowerUpView(this.takeImages(this.powerupImages, 30,41))));
                break;
            case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 10.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.BOUNCING));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModel.add(new DukeFishron(powerUpMovement, powerUpHitbox));
                powerUp.add(new GenericController<>(powerUpModel.get(0),  new PowerUpView(this.takeImages(this.powerupImages, 42,53))));
                break;

            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the powerup");
        }
        return powerUp;
    }

    @Override
    public GenericController<PickUp, PickUpView> generatePickUp(final PickUpType pickUpType) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Movement pickUpMovement;
        Hitbox pickUpHitbox;
        final PickUp pickUpModel;
        final GenericController<PickUp, PickUpView> pickUp;

        switch (pickUpType) {
            case VEHICLE: // Canon pickup existing in the original game
                pickUpMovement = new MovementImpl(new Pair<>(screenSizeX, screenSizeY/2), new Pair<>(-3.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY));
                pickUpHitbox = new HitboxImpl(pickUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 15, screenSizeY / 9));
                pickUpModel = new VehiclePickUp(pickUpMovement, pickUpHitbox);
                pickUp = new GenericController<>(pickUpModel, new PickUpView(this.takeImages(this.pickupImages, 0,20)));
                break;
        
            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the pickup");
            
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
